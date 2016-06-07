/*
 * The MIT License
 *
 * Copyright 2016 eder.lrbranco1.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.senac.sportnet.web.listener;

import com.senac.spornet.entity.Cliente;
import com.senac.sportnet.web.managedbean.UsuarioBean;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author eder.lrbranco1
 */
public class AutenticacaoListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();

        UsuarioBean usuarioBean = facesContext.getApplication()
                .evaluateExpressionGet(facesContext,
                        "#{usuarioBean}", UsuarioBean.class);

        String paginaAtual = facesContext.getViewRoot().getViewId();

        NavigationHandler nh = facesContext.getApplication()
                .getNavigationHandler();
        if (paginaAtual.contains("/admin/")) {
            if (usuarioBean == null || usuarioBean.getUsuario() == null) {
                nh.handleNavigation(facesContext, null,
                        "/autenticar.xhtml?faces-redirect=true");
                return;
            }
            if (!verificarAcesso(usuarioBean.getUsuario(), paginaAtual)) {
                nh.handleNavigation(facesContext, null,
                        "/erroNaoAutorizado.xhtml?faces-redirect=true");
                return;
            }
            // Se chegou nesse ponto da execução, JSF prossegue com
            // processamento da requisição.
        }
        if(paginaAtual.contains("/venda/")){
            if(usuarioBean == null || usuarioBean.getUsuario() == null){
                nh.handleNavigation(facesContext, null,
                        "/autenticar.xhtml?faces-redirect=true");
                return;
            }
        }

    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;

    }

    private static boolean verificarAcesso(Cliente usuario,
            String pagina) {
//        if (pagina.lastIndexOf("produto-form.xhtml") > -1
//                && usuario.autorizado("ADMIN")) {
//            return true;
//        } 
        if (pagina.indexOf("admin") > -1){
            return true;
        }
        return false;
    }

}
