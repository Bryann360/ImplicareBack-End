/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.implicare.model.daoImpl;

import br.cefetmg.implicare.dao.CargoInteresseDao;
import br.cefetmg.implicare.model.domain.CargoInteresse;
import br.cefetmg.implicare.model.exception.PersistenceException;
import br.cefetmg.inf.util.db.ConectaBd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 * 
 */

public class CargoInteresseDaoImpl implements CargoInteresseDao {

    @Override
    public boolean insert(CargoInteresse CargoInteresse) throws PersistenceException {
        try {
            
           Connection connection = ConectaBd.obterInstancia().obterConexao();

            String sql = "INSERT INTO CargoInteresse (CPF, Cod_Cargo) VALUES(?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);
           
            ps.setLong(1, CargoInteresse.getCPF());
            ps.setInt(2, CargoInteresse.getCod_Cargo());
            
            ResultSet rs = ps.executeQuery();

            rs.close();
            ps.close();
            connection.close();

            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public boolean delete(CargoInteresse CargoInteresse) throws PersistenceException {
        try {
            Connection connection = ConectaBd.obterInstancia().obterConexao();
            
            String SQL = "DELETE FROM CargoInteresse"
                    + "WHERE CPF = ?, Cod_Cargo = ?";
            
            PreparedStatement ps = connection.prepareStatement(SQL);
            
            ps.setLong(1, CargoInteresse.getCPF());
            ps.setInt(2, CargoInteresse.getCod_Cargo());
            
            ps.executeQuery(SQL);
            ps.close();
            connection.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public ArrayList<CargoInteresse> listar(long CPF) throws PersistenceException {
        try {
            Connection connection = ConectaBd.obterInstancia().obterConexao();

            String sql = "SELECT * FROM CargoInteresse WHERE CPF = ? ORDER BY Cod_Cargo";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, CPF);
            ResultSet rs = ps.executeQuery();

            ArrayList<CargoInteresse> lista = new ArrayList<>();
            
            if (rs.next()) {
                do {
                    CargoInteresse Car = new CargoInteresse();
                    Car.setCPF(rs.getLong("CPF"));
                    Car.setCod_Cargo(rs.getInt("Cod_Cargo"));
                    lista.add(Car);
                } while (rs.next());
            }

            rs.close();
            ps.close();
            connection.close();

            return lista;
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
}
