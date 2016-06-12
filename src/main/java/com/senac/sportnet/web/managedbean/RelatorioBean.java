/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

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
public class RelatorioBean implements Serializable{
    Venda venda;
    List<Venda> v;
    public RelatorioBean() {
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    public List<Venda> getRelatorios(){
        RelatorioService rs = new RelatorioServiceJPA();
        
        v = rs.mostrar();
        return v;
        
    }
}
