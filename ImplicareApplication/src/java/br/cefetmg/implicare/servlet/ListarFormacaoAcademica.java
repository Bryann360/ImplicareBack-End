/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import br.cefetmg.implicare.model.domain.FormacaoAcademica;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.implicare.model.service.FormacaoAcademicaManagement;
import br.cefetmg.implicare.model.serviceImpl.FormacaoAcademicaManagementImpl;
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

public class ListarFormacaoAcademica extends HttpServlet {
    private ArrayList<FormacaoAcademica> ListaFormacaoAcademica;
    private FormacaoAcademicaManagement FormacaoAcademicaManagement;
    private String result;
    
    public ListarFormacaoAcademica() {
        ListaFormacaoAcademica = null;
        result = "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        long CPF = Long.parseLong(request.getParameter("CPF"));
        
        try {
            FormacaoAcademicaManagement = new FormacaoAcademicaManagementImpl();
            ListaFormacaoAcademica = FormacaoAcademicaManagement.listar(CPF);
            
            if (!ListaFormacaoAcademica.isEmpty()) {
                result = "[";
                for (FormacaoAcademica FormacaoAcademica: ListaFormacaoAcademica) {  
                    result += "{"
                            + "\"CPF\": " + FormacaoAcademica.getCPF()
                            + ", \"Seq_Formacao\": \"" + FormacaoAcademica.getSeq_Formacao() + "\" "
                            + ", \"Instituicao_Ensino\": \"" + FormacaoAcademica.getInstituicao_Ensino() + "\""
                            + ", \"Cod_Area_Estudo\": \"" + FormacaoAcademica.getCod_Area_Estudo() + "\""
                            + ", \"Atividades_Desenvolvidas\": \"" + FormacaoAcademica.getAtividades_Desenvolvidas() + "\""
                            + ", \"Data_Inicio\": \"" + FormacaoAcademica.getData_Inicio() + "\""
                            + ", \"Data_Termino\": \"" + FormacaoAcademica.getData_Termino() + "\""
                            + ", \"Desc_Formacao_Academica\": \"" + 
                            FormacaoAcademica.getDesc_Formacao_Academica() + "\"},";
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
        return "List Formacao Academica";
    }

}
