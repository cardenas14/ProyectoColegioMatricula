/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import metodos.AuthRespuesta;
import metodos.AuthServicio;
import metodos.Conexion;
import modelos.Usuario;

/**
 *
 * @author Novoa
 */
public class AuthControlador {
    
    private AuthRespuesta respuesta;
    private AuthServicio authServicio;
    
    Connection cnx = null;

    public AuthControlador() {
        this.cnx = Conexion.abrir_conexion();
        this.authServicio = new AuthServicio();
    }
    
    public AuthRespuesta login(String email, String password) {
        try {
            String query = "select * from usuarios where email= '" + email + "' and password= '" + this.authServicio.encriptar(password) + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                if (rs.getBoolean(6)) {
                    EmpleadoControlador ec = new EmpleadoControlador();
                    Usuario usuario = new Usuario(
                            rs.getString(1),
                            ec.findId(rs.getString(2)),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getBoolean(6),
                            rs.getTimestamp(7),
                            rs.getTimestamp(8)
                    );
                    this.respuesta = new AuthRespuesta(false, "¡Bienvenido al sistema " + usuario.getEmpleado().getPriNom() + "!", usuario, null);
                } else {
                    this.respuesta = new AuthRespuesta(true, "¡Usuario bloqueado! Comuniquese con el Administrador del Sistema.", null, null);
                }
            } else {
                this.respuesta = new AuthRespuesta(true, "Las credenciales son incorrectas.", null, null);
            }
        } catch (Exception e) {
            this.respuesta = new AuthRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    
}
