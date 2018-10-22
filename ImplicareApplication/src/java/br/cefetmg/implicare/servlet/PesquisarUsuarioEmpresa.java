/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.Empresa;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.EmpresaManagement;
import br.cefetmg.implicare.model.serviceImpl.EmpresaManagementImpl;
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

public class PesquisarUsuarioEmpresa extends HttpServlet {
    private Empresa Empresa;
    private EmpresaManagement EmpresaManagement;
    private String result;
    
    public PesquisarUsuarioEmpresa() {
        Empresa = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CNPJ = Long.parseLong(request.getParameter("CNPJ"));
        
        try {
            EmpresaManagement = new EmpresaManagementImpl();
            Empresa = EmpresaManagement.pesquisar(CNPJ);
            
            if (Empresa != null) {
                result = "[ {"
                        + "\"CPF\": " + Empresa.getCPF_CNPJ()
                        + ", \"Email\": \"" + Empresa.getEmail() + "\""
                        + ", \"Foto\": \"" + Empresa.getFoto() + "\""
                        + ", \"Cod_Cep\": \"" + Empresa.getCod_CEP() + "\""
                        + ", \"Endereco\": \"" + Empresa.getEndereco() + "\""
                        + ", \"Desc_Usuario\": \"" + Empresa.getDesc_Usuario() + "\""
                        + ", \"Nom_Razao_Social\": \"" + Empresa.getNom_Razao_Social() + "\""
                        + ", \"Nome_Fantasia\": \"" + Empresa.getNome_Fantasia() + "\"},";
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
        return "Pesquisar Usuario Empresa";
    }

}
