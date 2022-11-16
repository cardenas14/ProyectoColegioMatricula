/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.ClienteInterfase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import metodos.Conexion;
import metodos.HelperServicio;
import metodos.MensajeRespuesta;
import metodos.ObjetoDevuelto;
import modelos.Cliente;
import modelos.TipoDocumento;

/**
 *
 * @author Novoa
 */
public class ClienteControlador implements ClienteInterfase {
    
    Connection cnx;

    public ClienteControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<Cliente> index(String texto_buscar) {
        List<Cliente> clientes = new ArrayList<>();
        String query = "";
        try {
            PreparedStatement pst;
            if (texto_buscar.isEmpty()) {
                query = "select * from clientes";
                pst = this.cnx.prepareStatement(query);
            } else {
                query = "select * from clientes where num_doc like ? or "
                        + "pri_nom like ? or seg_nom like ? or ape_pat like ? or ape_mat like ?";
                pst = this.cnx.prepareStatement(query);
                pst.setString(1, "%" + texto_buscar + "%");
                pst.setString(2, "%" + texto_buscar + "%");
                pst.setString(3, "%" + texto_buscar + "%");
                pst.setString(4, "%" + texto_buscar + "%");
                pst.setString(5, "%" + texto_buscar + "%");
            }
            ResultSet rs = pst.executeQuery();
            TipoDocumentoControlador tdc = new TipoDocumentoControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                clientes.add(new Cliente(
                    rs.getString(1),
                    tdc.findId(rs.getString(2)),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDate(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getBoolean(12),
                    rs.getTimestamp(13),
                    rs.getTimestamp(14)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return clientes;
    }
    
    @Override
    public MensajeRespuesta save(String id, TipoDocumento tipoDocumento, 
            String numDoc, String priNom, String segNom, String apePat, 
            String apeMat, Date fecNac, String genero, String direccion, 
            String telefono, Boolean estado) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            if (id.isEmpty()) {
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery("select * from clientes where num_doc= '" + numDoc + "'");
                if (!rs.next()) {
                    pst = this.cnx.prepareStatement("insert into clientes(tipo_documentos_id, "
                            + "num_doc, pri_nom, seg_nom, ape_pat, ape_mat, fec_nac, "
                            + "genero, direccion, telefono, estado) "
                            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
                    pst.setString(1, tipoDocumento.getId());
                    pst.setString(2, numDoc);
                    pst.setString(3, priNom);
                    pst.setString(4, segNom);
                    pst.setString(5, apePat);
                    pst.setString(6, apeMat);
                    pst.setString(7, sdt.format(fecNac));
                    pst.setString(8, genero);
                    pst.setString(9, direccion);
                    pst.setString(10, telefono);
                    pst.setBoolean(11, estado);
                    pst.executeUpdate();
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        respuesta = new MensajeRespuesta(false, "Cliente registrado correctamente.", 
                                new ObjetoDevuelto(rs.getString(1)), null);
                    } else {
                        respuesta = new MensajeRespuesta(true, "Error al registrar el Cliente.", null, null);
                    }
                } else {
                    respuesta = new MensajeRespuesta(true, "Número de Documento ya se encuentra registrado.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update clientes set tipo_documentos_id= ?, "
                        + "num_doc= ?, pri_nom= ?, seg_nom= ?, ape_pat= ?, ape_mat= ?, "
                        + "fec_nac= ?, genero= ?, direccion= ?, telefono= ?, estado= ? "
                        + "where id= ?");
                pst.setString(1, tipoDocumento.getId());
                pst.setString(2, numDoc);
                pst.setString(3, priNom);
                pst.setString(4, segNom);
                pst.setString(5, apePat);
                pst.setString(6, apeMat);
                pst.setString(7, sdt.format(fecNac));
                pst.setString(8, genero);
                pst.setString(9, direccion);
                pst.setString(10, telefono);
                pst.setBoolean(11, estado);
                pst.setString(12, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Cliente actualizado correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar el Cliente.", null, null);
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
            PreparedStatement pst = this.cnx.prepareStatement("delete from clientes where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Cliente eliminado Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar el Cliente.", null, null);
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public Cliente findId(String id) {
        Cliente cliente = null;
        try {
            String query = "select * from clientes where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                TipoDocumentoControlador tdc = new TipoDocumentoControlador();
                cliente = new Cliente(
                        rs.getString(1),
                        tdc.findId(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getDate(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getBoolean(12),
                        rs.getTimestamp(13),
                        rs.getTimestamp(14)
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cliente;
    }
    
    public MensajeRespuesta findByNumDoc(String num_doc) {
        MensajeRespuesta respuesta = null;
        try {
            String query = "select * from clientes where num_doc= '" + num_doc + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                respuesta = new MensajeRespuesta(false, "Número de Documento ya se encuentra registrado.", 
                        new ObjetoDevuelto(rs.getString(1)) , null);
            } else {
                respuesta = new MensajeRespuesta(false, "Número de Documento no se encuentra registrado.", null, null);
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, new HelperServicio().errorExepcion(e));
        }
        return respuesta;
    }
}
