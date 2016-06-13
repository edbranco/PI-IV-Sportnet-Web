/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Usuario;
import com.senac.sportnet.service.UsuarioClienteService;
import com.senac.sportnet.servicejpa.UsuarioClienteServiceJPA;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Eder Rodrigues
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    private String nome;
    private String senha;

    private Usuario usuario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String autenticar() {
        FacesContext context = FacesContext.getCurrentInstance();
        UsuarioClienteService ucs = new UsuarioClienteServiceJPA();

        this.usuario = ucs.Validar(nome, senha);

        if (this.usuario != null) {
            
            return "index.xhtml?faces-redirect=true";
        }
        return "erroLogin.xhtml?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }
}
