/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.CartaoCredito;
import com.senac.spornet.entity.Produto;
import com.senac.spornet.entity.Venda;
import com.senac.sportnet.fakeimpl.ProdutoServiceFakeImpl;
import com.senac.sportnet.service.ProdutoService;
import com.senac.sportnet.servicejpa.ProdutoServiceJPA;
import com.senac.sportnet.web.entity.ProdutoQuantidade;
import com.senac.sportnet.web.util.Mensagem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author nliggia-ibm
 */
@ManagedBean
@SessionScoped
public class CompraBean implements Serializable {

    private Set<ProdutoQuantidade> itens
            = new LinkedHashSet<ProdutoQuantidade>();
    float total = 0;
    private String pagamento;

    private CartaoCredito cc;

    @ManagedProperty("#{usuarioBean}")
    private UsuarioBean usuarioBean;

    public void setUsuarioBean(UsuarioBean bean) {
        this.usuarioBean = bean;
    }

    public CartaoCredito getCc() {
        return cc;
    }

    public void setCc(CartaoCredito cc) {
        this.cc = cc;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    private ProdutoQuantidade obterItem(Produto produto) {
        for (ProdutoQuantidade pq : itens) {
            if (pq.getProduto().equals(produto)) {
                return pq;
            }
        }
        return null;
    }

    public String adicionarProduto(long idProduto, int quantidade) {
        // obter objeto produto a partir do id
        ProdutoService prodService = new ProdutoServiceJPA();
        Produto p = prodService.obter(idProduto);

        ProdutoQuantidade pq = obterItem(p);
        if (pq == null) {
            // Cria um novo item para o produto e quantidade informados
            itens.add(new ProdutoQuantidade(p, quantidade));
        } else {
            // Altera a quantidade informada do produto
            pq.setQuantidade(quantidade);
        }

        // Mensagem de sucesso para usuário
        Flash flash = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash();
        flash.put("mensagem", new Mensagem("Produto '"
                + p.getNome()
                + "' adicionado com sucesso", "success"));
        return "carrinho.xhtml?faces-redirect=true";
    }

    // Converte Set em List senao nao funciona no ui:repeat
    public List<ProdutoQuantidade> getItens() {
        List<ProdutoQuantidade> lista
                = new ArrayList<ProdutoQuantidade>();
        lista.addAll(itens);
        return lista;
    }

    //ARRUMAR METODO QUE PEGA VALOR TOTAL, DO TIPO FLOAT
    public float getValorTotal() {
        total = 0;
        for (ProdutoQuantidade pq : itens) {
            total += pq.getPreco();
        }
        return total;
    }

    public String fecharCompra() {
        ProdutoService prodService = new ProdutoServiceJPA();
        try {
            if (itens.isEmpty()) {
                return "/index.xhtml?faces-redirect=true";
            } else {
                prodService.finalizarCompra(itens, total, usuarioBean.getUsuario().getNome());
                return "venda/codigoVenda.xhtml?faces-redirect=true";
            }

        } catch (NullPointerException e) {
            return "autenticar.xhtml?faces-redirect=true";
        }
    }

    public void atualizarQuantidade() {
        this.itens = this.itens;
    }

    public Venda ultimaVenda() {
        ProdutoService cv = new ProdutoServiceJPA();
        return cv.protocoloVenda();
    }

    public void removeItem(ProdutoQuantidade item) {
        itens.remove(item);
    }
}
