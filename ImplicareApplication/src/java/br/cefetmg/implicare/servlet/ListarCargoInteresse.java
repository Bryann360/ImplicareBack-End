/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.CargoInteresse;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.CargoInteresseManagement;
import br.cefetmg.implicare.model.serviceImpl.CargoInteresseManagementImpl;
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

public class ListarCargoInteresse extends HttpServlet {
    private ArrayList<CargoInteresse> ListaCargoInteresse;
    private CargoInteresseManagement CargoInteresseManagement;
    private String result;
    
    public ListarCargoInteresse() {
        ListaCargoInteresse = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CPF = Long.parseLong(request.getParameter("CPF"));
        
        try {
            CargoInteresseManagement = new CargoInteresseManagementImpl();
            ListaCargoInteresse = CargoInteresseManagement.listar(CPF);
            
            if (!ListaCargoInteresse.isEmpty()) {
                result = "[";
                for (CargoInteresse CargoInteresse: ListaCargoInteresse) {  
                    result += "{"
                            + "\"CPF\": " + CargoInteresse.getCPF()
                            + ", \"Cod_Cargo\": \"" + CargoInteresse.getCod_Cargo() + "\" },";
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
        return "List Cargo Interesse";
    }

}
