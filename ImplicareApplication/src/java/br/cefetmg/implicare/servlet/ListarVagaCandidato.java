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

public class ListarVagaCandidato extends HttpServlet {
    private ArrayList<Vaga> ListaVaga;
    private VagaManagement VagaManagement;
    private String result;
    
    public ListarVagaCandidato() {
        ListaVaga = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CPF = Long.parseLong(request.getParameter("CPF"));
        
        try {
            VagaManagement = new VagaManagementImpl();
            ListaVaga = VagaManagement.listarVagaCandidato(CPF);
            
            if (!ListaVaga.isEmpty()) {
                result = "[";
                for (Vaga Vaga: ListaVaga) {  
                    result += "{"
                            + "\"CNPJ\": " + Vaga.getCNPJ()
                            + ", \"Seq_Vaga\": \"" + Vaga.getSeq_Vaga() + "\""
                            + ", \"Cod_Cargo\": \"" + Vaga.getCod_Cargo() + "\""
                            + ", \"Dat_Publicacao\": \"" + Vaga.getDat_Publicacao() + "\""
                            + ", \"Num_Vagas\": \"" + Vaga.getNum_Vagas() + "\""
                            + ", \"Carga_Horaria\": \"" + Vaga.getCarga_Horaria() + "\""
                            + ", \"Remuneracao\": \"" + Vaga.getRemuneracao() + "\""
                            + ", \"Desc_Vaga\": \"" + Vaga.getDesc_Vaga() + "\""
                            + ", \"Status_Vaga\": \"" + Vaga.getStatus_Vaga() + "\"},";
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
        return "List Vaga Candidato";
    }

}
