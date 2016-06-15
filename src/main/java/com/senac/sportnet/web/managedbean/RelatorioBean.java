/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Produto;
import com.senac.spornet.entity.Venda;
import com.senac.sportnet.service.RelatorioService;
import com.senac.sportnet.servicejpa.RelatorioServiceJPA;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Eder Rodrigues
 */
@ManagedBean
public class RelatorioBean implements Serializable {

    private Venda venda;
    private Long id;
    private Venda v;

    private String nameSearch;
    List<Produto> resultProducts;

    public RelatorioBean() {
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public List<Produto> getResultProducts() {
        return resultProducts;
    }

    public void setResultProducts(List<Produto> resultProducts) {
        this.resultProducts = resultProducts;
    }

    public void searchProduct() {
        RelatorioService rs = new RelatorioServiceJPA();
        resultProducts = rs.searchProducts(nameSearch);
//        return "/listaPesquisa.xhtml?faces-redirect=true";
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getV() {
        return v;
    }

    public void setV(Venda v) {
        this.v = v;
    }

    public void mostrarRelatorios() {
        RelatorioService rs = new RelatorioServiceJPA();

        Venda v2 = rs.mostrar(id);
        if (v2 == null) {

        } else {
            v = v2;
        }

    }

    public List<Venda> getRelatorio() {
        RelatorioService rs = new RelatorioServiceJPA();

        return rs.mostrarTodos();
    }

    public List<Produto> getProdutos() {

        return resultProducts;
    }
    public void limparPesquisa(){
        resultProducts.clear();
    }
}
