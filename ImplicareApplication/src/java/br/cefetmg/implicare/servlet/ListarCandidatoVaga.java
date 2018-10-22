/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.CandidatoVaga;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.CandidatoVagaManagement;
import br.cefetmg.implicare.model.serviceImpl.CandidatoVagaManagementImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel
 * 
 */

public class ListarCandidatoVaga extends HttpServlet {
    private ArrayList<CandidatoVaga> ListaCandidatoVaga;
    private CandidatoVagaManagement CandidatoVagaManagement;
    private String result;
    
    public ListarCandidatoVaga() {
        ListaCandidatoVaga = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        int Seq_Vaga = Integer.parseInt(request.getParameter("Seq_Vaga"));
        
        try {
            CandidatoVagaManagement = new CandidatoVagaManagementImpl();
            ListaCandidatoVaga = CandidatoVagaManagement.listar(Seq_Vaga);
            
            if (!ListaCandidatoVaga.isEmpty()) {
                result = "[";
                for (CandidatoVaga CandidatoVaga: ListaCandidatoVaga) {  
                    result += "{"
                            + "\"CPF\": " + CandidatoVaga.getCPF()
                            + ", \"Seq_Vaga\": \"" + CandidatoVaga + "\""
                            + ", \"Cod_Cargo\": \"" + CandidatoVaga.getCod_Cargo() + "\""
                            + ", \"CNPJ\": \"" + CandidatoVaga.getCNPJ() + "\""
                            + ", \"Dat_Publicacao\": \"" + CandidatoVaga.getDat_Publicacao() + "\""
                            + ", \"Status_Candidato\": \"" + CandidatoVaga.getStatus_Candidato() + "\" },";
                }
                int ult = result.lastIndexOf(',');
                result = result.substring(0, ult);
                result += "]";
            
            }
            
            else {
                result = "[]";
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            
        } catch (PersistenceException ex) {
            result = ex.getMessage();
        }
        
        PrintWriter out = response.getWriter();
        response.addHeader("Access-Control-Allow-Origin", "*");
        out.println(result);
        
    }

    @Override
    public String getServletInfo() {
        return "List Candidato Vaga";
    }

}
