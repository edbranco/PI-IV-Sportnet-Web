/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Categoria;
import com.senac.spornet.entity.Produto;
import com.senac.sportnet.fakeimpl.ProdutoServiceFakeImpl;
import com.senac.sportnet.service.ProdutoService;
import com.senac.sportnet.servicejpa.ProdutoServiceJPA;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author nliggia-ibm
 */
@ManagedBean
@RequestScoped
public class ProdutoBean implements Serializable {

  // @ManagedProperty permite associar um parametro passado na requisição
  // Só funciona se bean usar @ManagedBean
  // Se não usar, tem que obter usando o FacesContext.getCurrentInstance.getRequestParameterMap()
  @ManagedProperty(value = "#{param.id}")
  private Long idProduto;

  public ProdutoBean() {
  }

  public List<Produto> getLista() {
    ProdutoService service = new ProdutoServiceJPA();
    Categoria cat = new Categoria(2, "teste");
    return service.listarPorCategoria(new Categoria(2, "teste"),
            0, 4);
  }

  public Produto getProduto() {
    //FacesContext fc = FacesContext.getCurrentInstance();
    //return obter(getIdParam(fc));
    return obter(getIdProduto());
  }

  private Produto obter(long idProduto) {
    ProdutoService service = new ProdutoServiceFakeImpl();
    return service.obter(idProduto);
  }

//    private Long getIdParam(FacesContext fc) {
//        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
//        return Long.parseLong(params.get("id"));
//    }

  public Long getIdProduto() {
    return idProduto;
  }

  public void setIdProduto(Long idProduto) {
    this.idProduto = idProduto;
  }

}
