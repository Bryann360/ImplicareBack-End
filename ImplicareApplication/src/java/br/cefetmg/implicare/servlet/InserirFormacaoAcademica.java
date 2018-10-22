/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.FormacaoAcademica;
import br.cefetmg.implicare.model.exception.BusinessException;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.FormacaoAcademicaManagement;
import br.cefetmg.implicare.model.serviceImpl.FormacaoAcademicaManagementImpl;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel
 * 
 */
public class InserirFormacaoAcademica extends HttpServlet {
    private FormacaoAcademicaManagement FormacaoAcademicaManagement;
    private String result;
    private ServletUtil Util;
    private Gson Gson;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        try {
            Util = new ServletUtil();
            String payload = Util.getJson(request);
            FormacaoAcademica FormacaoAcademica = this.FormacaoAcademicaFromJson(payload);
            FormacaoAcademicaManagement = new FormacaoAcademicaManagementImpl();
            
            FormacaoAcademicaManagement.insert(FormacaoAcademica);
            
            response.setStatus(HttpServletResponse.SC_OK);
            
        } catch (BusinessException | PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
        }
        
        finally{
            if(result != null){
                PrintWriter writer = response.getWriter();

            }
        }
        
    }
    
    private FormacaoAcademica FormacaoAcademicaFromJson(String str) {
        Gson = new Gson();
        FormacaoAcademica FormacaoAcademica = Gson.fromJson(str, FormacaoAcademica.class);
        return FormacaoAcademica;
    }
    
    @Override
    public String getServletInfo() {
        return "Insert Formacao Academica";
    }

}
