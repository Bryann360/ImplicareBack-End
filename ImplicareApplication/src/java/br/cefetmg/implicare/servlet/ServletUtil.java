/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gabriel
 * 
 */

public class ServletUtil extends HttpServlet {

    public String getJson(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }

        return sb.toString();
    }
    
}
