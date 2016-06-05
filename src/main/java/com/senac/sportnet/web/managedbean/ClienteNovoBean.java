/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.sportnet.web.managedbean;

import com.senac.spornet.entity.Cliente;
import com.senac.sportnet.service.CategoriaService;
import com.senac.sportnet.service.ClienteService;
import com.senac.sportnet.servicejpa.CategoriaServiceJPA;
import com.senac.sportnet.servicejpa.ClienteServiceJPA;
import com.senac.sportnet.web.util.Mensagem;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author nliggia-ibm
 */
@ManagedBean
@RequestScoped
@ViewScoped
public class ClienteNovoBean implements Serializable {

    @ManagedProperty(value = "#{param.id}")
    private Long idCliente;
    
    private String nome;
    private String cpf;
    private String sexo;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private Date dtNascimento;
    
    public ClienteNovoBean() {
    }
    
       public String getFragmento() {
        if (nome != null && nome.length() > 0) {
            return "<h1>" + getNome() + "</h1>";
        }
        return "ERRO";
    }
       
      public String salvar() {
          Cliente c = new Cliente();
          ClienteService cServ = new ClienteServiceJPA();
          
          c.setNome(nome);
          c.setCpf(cpf);
          c.setEmail(email);
          c.setLogin(login);
          c.setSenha(senha);
          c.setSexo(sexo);
          c.setEndereco(endereco);
          c.setDtNascimento(dtNascimento);
          
          cServ.incluir(c);
           Flash flash = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash();
        flash.put("mensagem", new Mensagem("Cliente '"
                + c.getNome()
                + "' cadastrado com sucesso", "success"));
        return "/index.xhtml?faces-redirect=true";
          
      }

       
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }
       
       

            

}
