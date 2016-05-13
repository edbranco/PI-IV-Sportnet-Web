/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.entity;

/**
 *
 * @author nliggia-ibm
 */

 

import com.senac.spornet.entity.Produto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class ProdutoQuantidade implements Serializable {

  private final Produto produto;
  private int quantidade;
  private Date dtInclusao;

  public ProdutoQuantidade(Produto produto, int quantidade) {
    this.produto = produto;
    this.quantidade = quantidade;
    this.dtInclusao = new Date();
  }
  public float getPreco() {
    // Preco * quantidade
    return produto.getPreco() * quantidade;
  }

  public Produto getProduto() {
    return produto;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public Date getDtInclusao() {
    return dtInclusao;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + Objects.hashCode(this.produto);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ProdutoQuantidade other = (ProdutoQuantidade) obj;
    if (!Objects.equals(this.produto, other.produto)) {
      return false;
    }
    return true;
  }
  
  
  

}

