/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.TipoComprobanteInterfase;
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
import modelos.TipoComprobante;

/**
 *
 * @author Novoa
 */
public class TipoComprobanteControlador implements TipoComprobanteInterfase {
    
    Connection cnx;

    public TipoComprobanteControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<TipoComprobante> index(String texto_buscar) {
        ArrayList<TipoComprobante> tipoComprobantes = new ArrayList<>();
        String query = "";
        try {
            PreparedStatement pst;
            if (texto_buscar.isEmpty()) {
                query = "select * from tipo_comprobantes";
                pst = this.cnx.prepareStatement(query);
            } else {
                query = "select * from tipo_comprobantes where nombre like ?";
                pst = this.cnx.prepareStatement(query);
                pst.setString(1, "%" + texto_buscar + "%");
            }
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                tipoComprobantes.add(new TipoComprobante(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getBoolean(5)
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return tipoComprobantes;
    }
    
    @Override
    public MensajeRespuesta save(String id, String nombre, String abreviatura,
            Integer correlativo, Boolean estado) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            if (id.isEmpty()) {
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery("select * from tipo_comprobantes where nombre= '" + nombre + "'");
                if (!rs.next()) {
                    pst = this.cnx.prepareStatement("insert into tipo_comprobantes(nombre, "
                            + "abreviatura, correlativo, estado) values (?, ?, ?, ?)", 1);
                    pst.setString(1, nombre);
                    pst.setString(2, abreviatura);
                    pst.setInt(3, correlativo);
                    pst.setBoolean(4, estado);
                    pst.executeUpdate();
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        respuesta = new MensajeRespuesta(false, "Tipo Comprobante registrado correctamente.", 
                                new ObjetoDevuelto(rs.getString(1)), null);
                    } else {
                        respuesta = new MensajeRespuesta(true, "Error al registrar el Tipo Comprobante.", null, null);
                    }
                } else {
                    respuesta = new MensajeRespuesta(true, "El Tipo Comprobante ya se encuentra registrado.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update tipo_comprobantes set nombre= ?, "
                        + "abreviatura= ?, correlativo= ?, estado= ? where id= ?");
                pst.setString(1, nombre);
                pst.setString(2, abreviatura);
                pst.setInt(3, correlativo);
                pst.setBoolean(4, estado);
                pst.setString(5, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Tipo Comprobante actualizado correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar el Tipo Comprobante.", null, null);
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
            PreparedStatement pst = this.cnx.prepareStatement("delete from tipo_comprobantes where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Tipo Comprobante eliminado Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar el Tipo Comprobante.", null, null);
            }
        } catch (SQLException e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public TipoComprobante findId(String id) {
        TipoComprobante tipoComprobante = new TipoComprobante();
        try {
            String query = "select * from tipo_comprobantes where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                tipoComprobante.setId(rs.getString(1));
                tipoComprobante.setNombre(rs.getString(2));
                tipoComprobante.setAbreviatura(rs.getString(3));
                tipoComprobante.setCorrelativo(rs.getInt(4));
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
        return tipoComprobante;
    }
    
    public boolean actuazarCorrelativo(TipoComprobante tipoComprobante) {
        boolean estado = false;
        try {
            PreparedStatement pst = this.cnx.prepareStatement("update tipo_comprobantes set correlativo= ? where id= ?");
            pst.setInt(1, tipoComprobante.getCorrelativo() + 1);
            pst.setString(2, tipoComprobante.getId());
            if (pst.executeUpdate() == 1) {
                estado = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return estado;
    }
}
