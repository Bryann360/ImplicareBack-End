/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.Candidato;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.CandidatoManagement;
import br.cefetmg.implicare.model.serviceImpl.CandidatoManagementImpl;
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

public class PesquisarUsuarioCandidato extends HttpServlet {
    private Candidato Candidato;
    private CandidatoManagement CandidatoManagement;
    private String result;
    
    public PesquisarUsuarioCandidato() {
        Candidato = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CPF = Long.parseLong(request.getParameter("CPF"));
        
        try {
            CandidatoManagement = new CandidatoManagementImpl();
            Candidato = CandidatoManagement.pesquisar(CPF);
            
            if (Candidato != null) {
                result = "[ {"
                        + "\"CPF\": " + Candidato.getCPF_CNPJ()
                        + ", \"Email\": \"" + Candidato.getEmail() + "\""
                        + ", \"Foto\": \"" + Candidato.getFoto() + "\""
                        + ", \"Cod_Cep\": \"" + Candidato.getCod_CEP() + "\""
                        + ", \"Endereco\": \"" + Candidato.getEndereco() + "\""
                        + ", \"Desc_Usuario\": \"" + Candidato.getDesc_Usuario() + "\""
                        + ", \"Nome\": \"" + Candidato.getNome() + "\""
                        + ", \"Data_Nascimento\": \"" + Candidato.getData_Nascimento() + "\"},";
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
        return "Pesquisar Usuario Candidato";
    }
    
}
