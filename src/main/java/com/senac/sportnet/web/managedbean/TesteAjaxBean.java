/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author nliggia-ibm
 */

@Named(value = "testeAjaxBean")
@Dependent
public class TesteAjaxBean {

  /**
   * Creates a new instance of TesteAjaxBean
   */
  public TesteAjaxBean() {
  }
  
  public List<String> getValores() {
    List<String> valores = new ArrayList<>();
    valores.add("Valor 1");
    valores.add("Valor 2");
    return valores;
  }
  
}

