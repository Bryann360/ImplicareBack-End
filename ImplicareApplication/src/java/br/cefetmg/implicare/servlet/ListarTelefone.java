/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.Telefone;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.TelefoneManagement;
import br.cefetmg.implicare.model.serviceImpl.TelefoneManagementImpl;
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

public class ListarTelefone extends HttpServlet {
    private ArrayList<Telefone> ListaTelefone;
    private TelefoneManagement TelefoneManagement;
    private String result;
    
    public ListarTelefone() {
        ListaTelefone = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CPF_CNPJ = Long.parseLong(request.getParameter("CPF_CNPJ"));
        
        try {
            TelefoneManagement = new TelefoneManagementImpl();
            ListaTelefone = TelefoneManagement.listar(CPF_CNPJ);
            
            if (!ListaTelefone.isEmpty()) {
                result = "[";
                for (Telefone Telefone: ListaTelefone) {  
                    result += "{"
                            + "\"CPF_CNPJ\": " + Telefone.getCPF_CNPJ()
                            + ", \"Seq_Telefone\": \"" + Telefone.getSeq_Telefone() + "\""
                            + ", \"Num_Telefone\": \"" + Telefone.getNum_Telefone() + "\""
                            + ", \"Tipo_Telefone\": \"" + Telefone.getTipo_Telefone() + "\""
                            + ", \"DDD\": \"" + Telefone.getDDD() + "\""
                            + ", \"Ramal\": \"" + Telefone.getRamal() + "\"},";
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
        return "List Telefone";
    }

}
