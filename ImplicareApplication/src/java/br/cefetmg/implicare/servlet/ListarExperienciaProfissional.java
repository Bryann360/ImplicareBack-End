/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.ExperienciaProfissional;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.ExperienciaProfissionalManagement;
import br.cefetmg.implicare.model.serviceImpl.ExperienciaProfissionalManagementImpl;
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

public class ListarExperienciaProfissional extends HttpServlet {
    private ArrayList<ExperienciaProfissional> ListaExperienciaProfissional;
    private ExperienciaProfissionalManagement ExperienciaProfissionalManagement;
    private String result;
    
    public ListarExperienciaProfissional() {
        ListaExperienciaProfissional = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CPF = Long.parseLong(request.getParameter("CPF"));
        
        try {
            ExperienciaProfissionalManagement = new ExperienciaProfissionalManagementImpl();
            ListaExperienciaProfissional = ExperienciaProfissionalManagement.listar(CPF);
            
            if (!ListaExperienciaProfissional.isEmpty()) {
                result = "[";
                for (ExperienciaProfissional ExperienciaProfissional: ListaExperienciaProfissional) {  
                    result += "{"
                            + "\"CPF\": " + ExperienciaProfissional.getCPF()
                            + ", \"Seq_Experiencia\": \"" + ExperienciaProfissional.getSeq_Experiencia() + "\" "
                            + ", \"Nom_Empresa\": \"" + ExperienciaProfissional.getNom_Empresa() + "\" "
                            + ", \"Cod_Cargo\": \"" + ExperienciaProfissional.getCod_Cargo() + "\""
                            + ", \"Data_Inicio\": \"" + ExperienciaProfissional.getData_Inicio() + "\""
                            + ", \"Data_Termino\": \"" + ExperienciaProfissional.getData_Termino() + "\""
                            + ", \"Desc_Experiencia_Profissional\": \"" + 
                            ExperienciaProfissional.getDesc_Experiencia_Profissional() + "\"},";
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
        return "List Experiencia Profisional";
    }


}
