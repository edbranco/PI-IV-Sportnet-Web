 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Categoria;
import com.senac.spornet.entity.ImagemProduto;
import com.senac.spornet.entity.Produto;
import com.senac.sportnet.service.CategoriaService;
import com.senac.sportnet.service.ProdutoService;
import com.senac.sportnet.servicejpa.CategoriaServiceJPA;
import com.senac.sportnet.servicejpa.ProdutoServiceJPA;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;
import org.primefaces.context.RequestContext;

/**
 *
 * @author nliggia-ibm
 */
@ManagedBean(name = "prodBean")
@ViewScoped
public class ProdBean {

    Produto produto = new Produto();
    private List<Integer> idsCategorias;
    private Part imagem;

    List produtos = new ArrayList();

    public ProdBean() {
        ProdutoService service = new ProdutoServiceJPA();
        produtos = service.listar(0, 100);
        produto = new Produto();

    }

    public void cadastrar(ActionEvent actionEvent) {
        CategoriaService cServ = new CategoriaServiceJPA();
        List<Categoria> listaCategorias = new ArrayList<Categoria>();
        for (int idCat : idsCategorias) {
            Categoria cat = cServ.obter(idCat);
            listaCategorias.add(cat);
            cat.setProdutos(Arrays.asList(produto));
        }
        produto.setCategorias(listaCategorias);
        produto.setDtCadastro(new Date());

        ImagemProduto img = new ImagemProduto();
        String nomeArquivo = obterNomeArquivo();
        if (nomeArquivo != null && nomeArquivo.trim().length() > 0) {
            salvarImagem(nomeArquivo);
            img.setNomeArquivo(nomeArquivo);
            img.setProduto(produto);
            produto.setImagens(Arrays.asList(img));
        }

        ProdutoService produtoService = new ProdutoServiceJPA();
        produtoService.incluir(produto);

        produtos = produtoService.listar(0, 100);

        produto = new Produto();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogCadastrar').hide();");
        

    }

    public void alterar(ActionEvent actionEvent) {
        ProdutoService produtoService = new ProdutoServiceJPA();
        try {
            produtoService.alterar(produto);
        } catch (Exception ex) {
            Logger.getLogger(ProdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        produtos = produtoService.listar(0, 100);
        produto = new Produto();
    }

    public void excluir(ActionEvent actionEvent) {
        ProdutoService produtoService = new ProdutoServiceJPA();
        produtoService.remover(produto.getId());
        produtos = produtoService.listar(0, 100);
        produto = new Produto();

    }

    private String obterNomeArquivo() {
        if (imagem != null) {
            String partHeader = imagem.getHeader("content-disposition");
            for (String content : partHeader.split(";")) {
                if (content.trim().startsWith("filename")) {
                    String nomeArquivo
                            = content.substring(content.indexOf('=') + 1)
                            .trim().replace("\"", "");
                    int lastFilePartIndex = nomeArquivo.lastIndexOf("\\");
                    if (lastFilePartIndex > 0) {
                        return nomeArquivo.substring(lastFilePartIndex,
                                nomeArquivo.length());
                    }
                    return nomeArquivo;
                }
            }
        }
        return null;
    }

    private void salvarImagem(String nomeImagem) {
        String diretorioDestino = "C:" + File.separator + "desenv"
                + File.separator + "imagens" + File.separator;
        File arquivo = new File(diretorioDestino + nomeImagem);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = getImagem().getInputStream();
            outputStream = new FileOutputStream(arquivo);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            //TODO: LOGAR ERRO
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //TODO: LOGAR ERRO
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //TODO: LOGAR ERRO
                }
            }
        }
    }

    public Part getImagem() {
        return imagem;
    }

    public void setImagem(Part imagem) {
        this.imagem = imagem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        ProdutoService service = new ProdutoServiceJPA();
        produtos = service.listar(0, 100);
        return produtos;
    }

    public void setProdutos(List produtos) {
        this.produtos = produtos;
    }

    public List<Integer> getIdsCategorias() {
        return idsCategorias;
    }

    public void setIdsCategorias(List<Integer> idsCategorias) {
        this.idsCategorias = idsCategorias;
    }

}
