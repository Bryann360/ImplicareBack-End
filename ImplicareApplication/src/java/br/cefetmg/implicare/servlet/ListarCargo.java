/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.Cargo;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.CargoManagement;
import br.cefetmg.implicare.model.serviceImpl.CargoManagementImpl;
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

public class ListarCargo extends HttpServlet {
    private ArrayList<Cargo> ListaCargo;
    private CargoManagement CargoManagement;
    private String result;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try {
            CargoManagement = new CargoManagementImpl();
            ListaCargo = CargoManagement.listar();
            
            if (!ListaCargo.isEmpty()) {
                result = "[";
                for (Cargo Cargo: ListaCargo) {  
                    result += "{"
                            + "\"Cod_Area_Estudo\": " + Cargo.getCod_Cargo()
                            + ", \"Nom_Area_Estudo\": \"" + Cargo.getNom_Cargo() + "\" },";
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
        PrintWriter writer = response.getWriter(); 
        writer.println(result);
        
    }

    @Override
    public String getServletInfo() {
        return "List Cargo";
    }

}
