/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Categoria;
import com.senac.sportnet.service.CategoriaService;
import com.senac.sportnet.servicejpa.CategoriaServiceJPA;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author nliggia-ibm
 */
@ManagedBean
@ApplicationScoped
public class CategoriaBean implements Serializable {

  public CategoriaBean() {
  }

  public List<Categoria> getLista() {
    CategoriaService service = new CategoriaServiceJPA();
    return service.listar();
  }
}
