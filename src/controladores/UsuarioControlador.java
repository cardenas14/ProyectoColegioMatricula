/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.UsuarioInterfase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import metodos.AuthServicio;
import metodos.Conexion;
import metodos.MensajeRespuesta;
import metodos.ObjetoDevuelto;
import modelos.Empleado;
import modelos.Usuario;

/**
 *
 * @author Novoa
 */
public class UsuarioControlador implements UsuarioInterfase {
    
    Connection cnx;

    public UsuarioControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<Usuario> index(String texto_buscar) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        String query = "";
        try {
            PreparedStatement pst;
            if (texto_buscar.isEmpty()) {
                query = "select * from usuarios";
                pst = this.cnx.prepareStatement(query);
            } else {
                query = "select u.* from usuarios u inner join empleados e on "
                        + "u.empleados_id = e.id where u.email like ? or "
                        + "u.perfil like ? or e.num_doc like ? or e.pri_nom "
                        + "like ? or e.seg_nom like ? or e.ape_pat like ? "
                        + "or e.ape_mat like ?";
                pst = this.cnx.prepareStatement(query);
                pst.setString(1, "%" + texto_buscar + "%");
                pst.setString(2, "%" + texto_buscar + "%");
                pst.setString(3, "%" + texto_buscar + "%");
                pst.setString(4, "%" + texto_buscar + "%");
                pst.setString(5, "%" + texto_buscar + "%");
                pst.setString(6, "%" + texto_buscar + "%");
                pst.setString(7, "%" + texto_buscar + "%");
            }
            ResultSet rs = pst.executeQuery();
            EmpleadoControlador ec = new EmpleadoControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                usuarios.add(new Usuario(
                    rs.getString(1),
                    ec.findId(rs.getString(2)),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getBoolean(6),
                    rs.getTimestamp(7),
                    rs.getTimestamp(8)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return usuarios;
    }
    
    @Override
    public MensajeRespuesta save(String id, Empleado empleado, 
            String email, String password, String perfil, Boolean estado) {
        MensajeRespuesta respuesta = null;
        try {
            AuthServicio authServicio = new AuthServicio();
            PreparedStatement pst;
            if (id.isEmpty()) {
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery("select * from usuarios where email= '" + email + "'");
                if (!rs.next()) {
                    pst = this.cnx.prepareStatement("insert into usuarios"
                            + "(empleados_id, email, password, perfil, estado) "
                            + "VALUES (?, ?, ?, ?, ?)", 1);
                    pst.setString(1, empleado.getId());
                    pst.setString(2, email);
                    pst.setString(3, authServicio.encriptar(password));
                    pst.setString(4, perfil);
                    pst.setBoolean(5, estado);
                    pst.executeUpdate();
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        respuesta = new MensajeRespuesta(false, "Usuario registrado correctamente.", 
                                new ObjetoDevuelto(rs.getString(1)), null);
                    } else {
                        respuesta = new MensajeRespuesta(true, "Error al registrar al Usuario.", null, null);
                    }
                } else {
                    respuesta = new MensajeRespuesta(true, "El Email ingresado ya se encuentra registrado.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update usuarios set "
                        + "empleados_id = ?, email = ?, password = ?, "
                        + "perfil = ?, estado = ? where id = ?");
                pst.setString(1, empleado.getId());
                pst.setString(2, email);
                pst.setString(3, authServicio.encriptar(password));
                pst.setString(4, perfil);
                pst.setBoolean(5, estado);
                pst.setString(6, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Usuario actualizado correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar al Usuario.", null, null);
                }
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }

    @Override
    public MensajeRespuesta delete(String id) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst = this.cnx.prepareStatement("delete from usuarios where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Usuario eliminado Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar al Usuario.", null, null);
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public Usuario findId(String id) {
        Usuario usuario = null;
        try {
            String query = "select * from usuarios where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                EmpleadoControlador ec = new EmpleadoControlador();
                usuario = new Usuario(
                        rs.getString(1),
                        ec.findId(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8)
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return usuario;
    }
    
    public MensajeRespuesta findByEmpleado(String id) {
        MensajeRespuesta respuesta = null;
        try {
            String query = "select * from usuarios where empleados_id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                respuesta = new MensajeRespuesta(false, "El Empleado ya cuenta con su Usuario.", 
                        new ObjetoDevuelto(rs.getString(1)) , null);
            } else {
                respuesta = new MensajeRespuesta(false, "El Empleado todavía no cuenta con su Usuario.", null, null);
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
}
