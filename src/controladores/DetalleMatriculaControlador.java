/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.DetalleMatriculaInterfase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import metodos.Conexion;
import metodos.MensajeRespuesta;
import modelos.DetalleMatricula;

/**
 *
 * @author Novoa
 */
public class DetalleMatriculaControlador implements DetalleMatriculaInterfase {
    
    Connection cnx;

    public DetalleMatriculaControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<DetalleMatricula> index(String matriculaId) {
        List<DetalleMatricula> detalleMatriculas = new ArrayList<>();
        String query = "";
        try {
            query = "select * from detalle_matriculas where matriculas_id= '" + matriculaId + "'";
            PreparedStatement pst = this.cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            MatriculaControlador mc = new MatriculaControlador();
            CursoControlador cc = new CursoControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                detalleMatriculas.add(new DetalleMatricula(
                    rs.getString(1),
                    mc.findId(rs.getString(2)),
                    cc.findId(rs.getString(3)),
                    rs.getDouble(4),
                    rs.getDouble(5)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return detalleMatriculas;
    }

    @Override
    public MensajeRespuesta save(String matriculaId, List<DetalleMatricula> detalleMatriculas) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            ResultSet rs;
            pst = this.cnx.prepareStatement("insert into detalle_matriculas(matriculas_id, "
                    + "cursos_id, costo, subtotal) values (?, ?, ?, ?)");
            for (DetalleMatricula detalleMatricula : detalleMatriculas) {
                pst.setString(1, matriculaId);
                pst.setString(2, detalleMatricula.getCurso().getId());
                pst.setDouble(3, detalleMatricula.getCosto());
                pst.setDouble(4, detalleMatricula.getSubtotal());
                pst.executeUpdate();
            }
            respuesta = new MensajeRespuesta(false, "Detalle Matrícula registrado correctamente.", null, null);
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
}
