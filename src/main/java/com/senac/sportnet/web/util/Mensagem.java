/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.util;

import java.io.Serializable;

/**
 *
 * @author nliggia-ibm
 */

public class Mensagem implements Serializable {

  private String msg;
  private String tipo;

  public Mensagem() {

  }

  public Mensagem(String msg, String tipo) {
    this.msg = msg;
    this.tipo = tipo;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

}

