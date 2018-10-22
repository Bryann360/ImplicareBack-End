/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.AreaEstudo;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.AreaEstudoManagement;
import br.cefetmg.implicare.model.serviceImpl.AreaEstudoManagementImpl;
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

public class ListarAreaEstudo extends HttpServlet {
    private ArrayList<AreaEstudo> ListaAreaEstudo;
    private AreaEstudoManagement AreaEstudoManagement;
    private String result;
    
    public ListarAreaEstudo() {
        ListaAreaEstudo = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try {
            AreaEstudoManagement = new AreaEstudoManagementImpl();
            ListaAreaEstudo = AreaEstudoManagement.listar();
            
            if (!ListaAreaEstudo.isEmpty()) {
                result = "[";
                for (AreaEstudo AreaEstudo: ListaAreaEstudo) {  
                    result += "{"
                            + "\"Cod_Area_Estudo\": " + AreaEstudo.getCod_Area_Estudo()
                            + ", \"Nom_Area_Estudo\": \"" + AreaEstudo.getNom_Area_Estudo() + "\" },";
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
        return "List Area Estudo";
    }

}
