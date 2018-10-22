package br.cefetmg.inf.util.db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectaBd {

    private static final String URL = "jdbc:postgresql://localhost/implicare";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123456";
    private static Connection conn;
    private static volatile ConectaBd instanciaUnica;

    private ConectaBd() {
        try {
            Class.forName("org.postgresql.Driver");
            try {
                conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (SQLException ex) {
                Logger.getLogger(ConectaBd.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectaBd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ConectaBd obterInstancia() {
        if (instanciaUnica == null) {
            synchronized (ConectaBd.class) {
                if (instanciaUnica == null) {
                    instanciaUnica = new ConectaBd();
                }
            }
        }
        return instanciaUnica;
    }

    public Connection obterConexao() {
        if (conn != null) {
            return conn;
        } else {
            throw new NullPointerException("Variavel conexão não iniciada.");
        }
    }
}