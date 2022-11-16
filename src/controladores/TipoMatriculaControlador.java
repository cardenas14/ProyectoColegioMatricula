/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.TipoMatriculaInterfase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import metodos.Conexion;
import metodos.MensajeRespuesta;
import metodos.ObjetoDevuelto;
import modelos.TipoMatricula;

/**
 *
 * @author Novoa
 */
public class TipoMatriculaControlador implements TipoMatriculaInterfase {
    
    Connection cnx;

    public TipoMatriculaControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<TipoMatricula> index(String texto_buscar) {
        ArrayList<TipoMatricula> tipoMatriculas = new ArrayList<>();
        String query = "";
        try {
            PreparedStatement pst;
            if (texto_buscar.isEmpty()) {
                query = "select * from tipo_matriculas";
                pst = this.cnx.prepareStatement(query);
            } else {
                query = "select * from tipo_matriculas where nombre like ?";
                pst = this.cnx.prepareStatement(query);
                pst.setString(1, "%" + texto_buscar + "%");
            }
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                tipoMatriculas.add(new TipoMatricula(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getBoolean(4)
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return tipoMatriculas;
    }
    
    @Override
    public MensajeRespuesta save(String id, String nombre, Double porcAplicado, Boolean estado) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            if (id.isEmpty()) {
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery("select * from tipo_matriculas where nombre= '" + nombre + "'");
                if (!rs.next()) {
                    pst = this.cnx.prepareStatement("insert into tipo_matriculas(nombre, "
                            + "porc_aplicado, estado) values (?, ?, ?)", 1);
                    pst.setString(1, nombre);
                    pst.setDouble(2, porcAplicado);
                    pst.setBoolean(3, estado);
                    pst.executeUpdate();
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        respuesta = new MensajeRespuesta(false, "Tipo Matricula registrada correctamente.", 
                                new ObjetoDevuelto(rs.getString(1)), null);
                    } else {
                        respuesta = new MensajeRespuesta(true, "Error al registrar el Tipo Matricula.", null, null);
                    }
                } else {
                    respuesta = new MensajeRespuesta(true, "El Tipo Matricula ya se encuentra registrado.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update tipo_matriculas set nombre= ?, "
                        + "porc_aplicado= ?, estado= ? where id= ?");
                pst.setString(1, nombre);
                pst.setDouble(2, porcAplicado);
                pst.setBoolean(3, estado);
                pst.setString(4, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Tipo Matricula actualizada correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar el Tipo Matricula.", null, null);
                }
            }
        } catch (SQLException e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public MensajeRespuesta delete(String id) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst = this.cnx.prepareStatement("delete from tipo_matriculas where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Tipo Matricula eliminada Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar el Tipo Matricula.", null, null);
            }
        } catch (SQLException e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public TipoMatricula findId(String id) {
        TipoMatricula tipoMatricula = new TipoMatricula();
        try {
            String query = "select * from tipo_matriculas where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                tipoMatricula.setId(rs.getString(1));
                tipoMatricula.setNombre(rs.getString(2));
                tipoMatricula.setPorcAplicado(rs.getDouble(3));
                tipoMatricula.setEstado(rs.getBoolean(4));
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
        return tipoMatricula;
    }
}
