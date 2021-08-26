/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import config.Connect;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Alexander Granados <alexander.granados.dev@gmail.com>
 */
public class PersonaDAO {
    Connect conexion;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public PersonaDAO() throws IOException, FileNotFoundException, ParseException {
        this.conexion = new Connect();
    }
    
    public List listar(){
        List<Persona> datos = new ArrayList<>();
        String sql = "SELECT * FROM persona";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                Persona p = new Persona();
                p.setId(rs.getInt(1));
                p.setDni(rs.getString(2));
                p.setNombre(rs.getString(3));
                
                datos.add(p);
            }
            
        } catch (Exception e) {
        }
        
        return datos;
    }
    
    public int agregar(Persona p){
        
        String sql = "INSERT INTO persona(dni, nombre) values(?,?)";
        
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.executeUpdate();
            
        } catch (Exception e) {
        }
        
        return 1;
    }
    
    public int actualizar (Persona p){
        int r = 0;
        String sql = "UPDATE persona SET dni=?, nombre=? WHERE id=?";
        
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getId());
            r = ps.executeUpdate();
            
           r = (r == 1) ? 1 : 0;
        
        } catch (Exception e) {
        }
        
        return r;
    }
    
    public void eliminar (int id){
    
        String sql = "DELETE FROM persona WHERE id=" +id;
        
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
