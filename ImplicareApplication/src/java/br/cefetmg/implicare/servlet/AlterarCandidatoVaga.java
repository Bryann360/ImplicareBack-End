/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.CandidatoVaga;
import br.cefetmg.implicare.model.exception.BusinessException;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.CandidatoVagaManagement;
import br.cefetmg.implicare.model.serviceImpl.CandidatoVagaManagementImpl;
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

public class AlterarCandidatoVaga extends HttpServlet {
    private CandidatoVagaManagement CandidatoVagaManagement;
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
            CandidatoVaga CandidatoVaga = this.CandidatoVagaFromJson(payload);
            CandidatoVagaManagement = new CandidatoVagaManagementImpl();
            
            CandidatoVagaManagement.update(CandidatoVaga);
            
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
    
    private CandidatoVaga CandidatoVagaFromJson(String str) {
        Gson = new Gson();
        CandidatoVaga CandidatoVaga = Gson.fromJson(str, CandidatoVaga.class);
        return CandidatoVaga;
    }

    @Override
    public String getServletInfo() {
        return "Update Candidato Vaga";
    }

}
