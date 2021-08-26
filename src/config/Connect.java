/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Alexander Granados <alexander.granados.dev@gmail.com>
 */
public class Connect {
    
    
    
    Connection con;
    
    public Connect() throws FileNotFoundException, IOException, ParseException{
        JSONParser parser = new JSONParser();
        try {
            
            String credentials_path = System.getProperty("user.dir") + "/src/config/db.json";
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(credentials_path));
                    
            String ip = (String)jsonObject.get("ip");
            String port = (String)jsonObject.get("port");
            String db = (String)jsonObject.get("db");
            String user = (String)jsonObject.get("user");
            String password = (String)jsonObject.get("password");

            
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%s/%s", ip, port,db),
                    user,
                    password
            );
        
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error");
            System.out.println("error: " + e);
        }
    }
    
    public Connection getConnection(){
        return con;
    }
}
