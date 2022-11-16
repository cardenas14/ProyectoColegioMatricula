/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Novoa
 */
public class Conexion {
    
    public static Connection cnx = null;
    
    public Conexion() {
        
    }
    
    public static Connection abrir_conexion() {
        if ( cnx == null ) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection("jdbc:mysql://localhost:3307/matricula", "root", "");
                System.out.println("Conexion exitosa");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return cnx;
    }
}
