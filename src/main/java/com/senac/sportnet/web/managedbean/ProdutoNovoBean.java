/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Categoria;
import com.senac.spornet.entity.ImagemProduto;
import com.senac.spornet.entity.Produto;
import com.senac.sportnet.fakeimpl.ProdutoServiceFakeImpl;
import com.senac.sportnet.service.CategoriaService;
import com.senac.sportnet.service.ProdutoService;
import com.senac.sportnet.servicejpa.CategoriaServiceJPA;
import com.senac.sportnet.servicejpa.ProdutoServiceJPA;
import com.senac.sportnet.web.util.Mensagem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.Part;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author nliggia-ibm
 */
@ManagedBean
@RequestScoped
@ViewScoped
public class ProdutoNovoBean implements Serializable {

    @ManagedProperty(value = "#{param.id}")
    private Long idProduto;

    private String nome;
    private String descricao;
    private List<Integer> idsCategorias;
    private float preco;
    private Part imagem;
    private String genero;
    private String cor;
    private String tamanho;
    private String marca;
    private int qtdAtual;
    private List<Produto> produtosLista;
    private String nomeCategoria;

    public ProdutoNovoBean() {
    }

    public List<Produto> getLista() {
        ProdutoService service = new ProdutoServiceJPA();
        return service.listar(0, 100);
    }

    public String getFragmento() {
        if (nome != null && nome.length() > 0) {
            return "<h1>" + getNome() + "</h1>";
        }
        return "ERRO";
    }

    public String salvar() {
        Produto p = new Produto();
        CategoriaService cServ = new CategoriaServiceJPA();

        p.setNome(nome);
        p.setDescricao(descricao);
        p.setTamanho(tamanho);
        p.setCor(cor);
        p.setQtdAtual(qtdAtual);
        p.setGenero(genero);
        p.setMarca(marca);
        List<Categoria> listaCategorias = new ArrayList<Categoria>();
        for (int idCat : idsCategorias) {
            Categoria cat = cServ.obter(idCat);
            listaCategorias.add(cat);
            cat.setProdutos(Arrays.asList(p));
        }
        p.setCategorias(listaCategorias);
        p.setPreco(preco);
        p.setDtCadastro(new Date());

        ImagemProduto img = new ImagemProduto();
        String nomeArquivo = obterNomeArquivo();
        if (nomeArquivo != null && nomeArquivo.trim().length() > 0) {
            salvarImagem(nomeArquivo);
            img.setNomeArquivo(nomeArquivo);
            img.setProduto(p);
            p.setImagens(Arrays.asList(img));
        }

        ProdutoService produtoService = new ProdutoServiceJPA();
        produtoService.incluir(p);

        Flash flash = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash();
        flash.put("mensagem", new Mensagem("Produto '"
                + p.getNome()
                + "' cadastrado com sucesso", "success"));
        return "/admin-produto.xhtml?faces-redirect=true";
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Integer> getIdsCategorias() {
        return idsCategorias;
    }

    public void setIdsCategorias(List<Integer> idsCategorias) {
        this.idsCategorias = idsCategorias;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Part getImagem() {
        return imagem;
    }

    public void setImagem(Part imagem) {
        this.imagem = imagem;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Produto obter(long idProduto) {
        ProdutoService service = new ProdutoServiceJPA();
        Produto produtoObtido = service.obter(idProduto);
        for (Categoria c : produtoObtido.getCategorias()) {
            nomeCategoria = c.getNome();
        }
        return produtoObtido;
    }

    public Produto getProduto() {
        return obter(getIdProduto());
    }

    public List<Produto> getProdutosLista() {
        return produtosLista;
    }

    public void setProdutosLista(List<Produto> produtosLista) {
        this.produtosLista = produtosLista;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public int getQtdAtual() {
        return qtdAtual;
    }

    public void setQtdAtual(int qtdAtual) {
        this.qtdAtual = qtdAtual;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

//    public void onRowEdit(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//
//    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//
//    public void onCellEdit(CellEditEvent event) {
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//
//        if (newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }
//    
}
