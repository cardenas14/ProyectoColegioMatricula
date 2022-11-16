/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.MatriculaInterfase;
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
import modelos.Empleado;
import modelos.Matricula;
import modelos.TipoComprobante;
import modelos.TipoMatricula;

/**
 *
 * @author Novoa
 */
public class MatriculaControlador implements MatriculaInterfase {
    
    Connection cnx;

    public MatriculaControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<Matricula> index(
            String texto_buscar,
            String tipo_matriculas_id,
            String tipo_comprobantes_id,
            String fecDesde,
            String fecHasta
    ) {
        List<Matricula> matriculas = new ArrayList<>();
        String query = "";
        boolean and = false;
        try {
            PreparedStatement pst;
            /*query = "select m.* from matriculas m inner join empleados e "
                    + "on m.empleados_id = e.id inner join clientes c "
                    + "on m.clientes_id = c.id inner join tipo_comprobantes tc "
                    + "on m.tipo_comprobantes_id = tc.id inner join tipo_matriculas tm "
                    + "on m.tipo_matriculas_id = tm.id where e.num_doc like ? "
                    + "or e.pri_nom like ? or e.seg_nom like ? "
                    + "or e.ape_pat like ? or e.ape_mat like ? "
                    + "or c.num_doc like ? or c.pri_nom like ? "
                    + "or c.seg_nom like ? or c.ape_pat like ? "
                    + "or c.ape_mat like ? or tc.nombre like ? "
                    + "or m.num_comprobante like ? or tm.nombre like ? "
                    + "or m.tipo_matriculas_id = ? or m.tipo_comprobantes_id = ? "
                    + "or fecha between ? and ?";*/
            query = "select m.* from matriculas m inner join empleados e "
                    + "on m.empleados_id = e.id inner join clientes c "
                    + "on m.clientes_id = c.id inner join tipo_comprobantes tc "
                    + "on m.tipo_comprobantes_id = tc.id inner join tipo_matriculas tm "
                    + "on m.tipo_matriculas_id = tm.id where ";
            if (tipo_matriculas_id != null) {
               query = query + "m.tipo_matriculas_id ='" + tipo_matriculas_id + "' ";
               and = true;
            }
            if (tipo_comprobantes_id != null) {
               query = query + (and ? "and " : "" ) + "m.tipo_comprobantes_id ='" + tipo_comprobantes_id + "' ";
               and = true;
            }
            if (fecDesde != null && fecHasta != null) {
               query = query + (and ? "and " : "" ) + "fecha between '" + fecDesde + "' and '" + fecHasta + "' ";
               and = true;
            }
            query = query + (and ? "and " : "") + "(e.num_doc like ? "
                    + "or e.pri_nom like ? or e.seg_nom like ? "
                    + "or e.ape_pat like ? or e.ape_mat like ? "
                    + "or c.num_doc like ? or c.pri_nom like ? "
                    + "or c.seg_nom like ? or c.ape_pat like ? "
                    + "or c.ape_mat like ? or m.num_comprobante like ?)";
            pst = this.cnx.prepareStatement(query);
            pst.setString(1, "%" + texto_buscar + "%");
            pst.setString(2, "%" + texto_buscar + "%");
            pst.setString(3, "%" + texto_buscar + "%");
            pst.setString(4, "%" + texto_buscar + "%");
            pst.setString(5, "%" + texto_buscar + "%");
            pst.setString(6, "%" + texto_buscar + "%");
            pst.setString(7, "%" + texto_buscar + "%");
            pst.setString(8, "%" + texto_buscar + "%");
            pst.setString(9, "%" + texto_buscar + "%");
            pst.setString(10, "%" + texto_buscar + "%");
            pst.setString(11, "%" + texto_buscar + "%");
            ResultSet rs = pst.executeQuery();
            EmpleadoControlador ec = new EmpleadoControlador();
            ClienteControlador cc = new ClienteControlador();
            TipoMatriculaControlador tmc = new TipoMatriculaControlador();
            TipoComprobanteControlador tcc = new TipoComprobanteControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                matriculas.add(new Matricula(
                    rs.getString(1),
                    ec.findId(rs.getString(2)),
                    cc.findId(rs.getString(3)),
                    tmc.findId(rs.getString(4)),
                    rs.getDouble(5),
                    rs.getDouble(6),
                    tcc.findId(rs.getString(7)),
                    rs.getString(8),
                    rs.getDate(9),
                    rs.getDouble(10),
                    rs.getString(11),
                    rs.getDate(12),
                    rs.getTimestamp(13),
                    rs.getTimestamp(14)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return matriculas;
    }

    @Override
    public MensajeRespuesta save(String id, Empleado empleado, Cliente cliente,
            TipoMatricula tipoMatricula, Double porcAplicado, Double montoAplicado,
            TipoComprobante tipoComprobante, String numComprobante, Date fecha,
            Double total, String motivoAnulacion, Date fechaAnulacion) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            if (id == null) {
                TipoComprobanteControlador tcc = new TipoComprobanteControlador();
                tipoComprobante = tcc.findId(tipoComprobante.getId());
                String correlativo = String.valueOf(tipoComprobante.getCorrelativo() + 1);
                numComprobante = tipoComprobante.getAbreviatura() + " N°" + 
                HelperServicio.correlativo(correlativo);
                pst = this.cnx.prepareStatement("insert into matriculas(empleados_id, "
                        + "clientes_id, tipo_matriculas_id, porc_aplicado, monto_aplicado, "
                        + "tipo_comprobantes_id, num_comprobante, fecha, total) "
                        + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
                pst.setString(1, empleado.getId());
                pst.setString(2, cliente.getId());
                pst.setString(3, tipoMatricula.getId());
                pst.setDouble(4, porcAplicado);
                pst.setDouble(5, montoAplicado);
                pst.setString(6, tipoComprobante.getId());
                pst.setString(7, numComprobante);
                pst.setString(8, sdt.format(fecha));
                pst.setDouble(9, total);
                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    if (tcc.actuazarCorrelativo(tipoComprobante)) {
                        respuesta = new MensajeRespuesta(false, "Matricula registrada correctamente.",
                                new ObjetoDevuelto(rs.getString(1)), null);
                    }
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al registrar la Matricula.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update matriculas set "
                        + "motivo_anulacion= ?, fecha_anulacion= ? where id= ?");
                pst.setString(1, motivoAnulacion);
                pst.setString(2, sdt.format(fechaAnulacion));
                pst.setString(3, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Matricula actualizada correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar la Matricula.", null, null);
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
            PreparedStatement pst = this.cnx.prepareStatement("delete from matriculas where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Matricula eliminada Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar la Matricula.", null, null);
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public Matricula findId(String id) {
        Matricula matricula = null;
        try {
            String query = "select * from matriculas where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                EmpleadoControlador ec = new EmpleadoControlador();
                ClienteControlador cc = new ClienteControlador();
                TipoMatriculaControlador tmc = new TipoMatriculaControlador();
                TipoComprobanteControlador tcc = new TipoComprobanteControlador();
                matricula = new Matricula(
                        rs.getString(1),
                        ec.findId(rs.getString(2)),
                        cc.findId(rs.getString(3)),
                        tmc.findId(rs.getString(4)),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        tcc.findId(rs.getString(7)),
                        rs.getString(8),
                        rs.getDate(9),
                        rs.getDouble(10),
                        rs.getString(11),
                        rs.getDate(12),
                        rs.getTimestamp(13),
                        rs.getTimestamp(14)
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return matricula;
    }
}
