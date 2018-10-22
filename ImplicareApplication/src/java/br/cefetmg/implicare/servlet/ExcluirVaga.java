/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.Vaga;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.VagaManagement;
import br.cefetmg.implicare.model.serviceImpl.VagaManagementImpl;
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

public class ExcluirVaga extends HttpServlet {
    private VagaManagement VagaManagement;
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
            Vaga Vaga = this.VagaFromJson(payload);
            VagaManagement = new VagaManagementImpl();
            
            VagaManagement.delete(Vaga);
            
            response.setStatus(HttpServletResponse.SC_OK);
            
        } catch (PersistenceException e) {
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
        }
        
        finally{
            if(result != null){
                PrintWriter writer = response.getWriter();

            }
        }
        
    }

    private Vaga VagaFromJson(String str) {
        Gson = new Gson();
        Vaga Vaga = Gson.fromJson(str, Vaga.class);
        return Vaga;
    }
    
    @Override
    public String getServletInfo() {
        return "Delete Vaga";
    }

}
