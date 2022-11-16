/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.CursoInterfase;
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
import metodos.MensajeRespuesta;
import metodos.ObjetoDevuelto;
import modelos.CategoriaCurso;
import modelos.Curso;
import modelos.Profesor;

/**
 *
 * @author Novoa
 */
public class CursoControlador implements CursoInterfase {
    
    Connection cnx;

    public CursoControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<Curso> index(String texto_buscar) {
        List<Curso> cursos = new ArrayList<>();
        String query = "";
        try {
            PreparedStatement pst;
            if (texto_buscar.isEmpty()) {
                query = "select * from cursos";
                pst = this.cnx.prepareStatement(query);
            } else {
                query = "select * from cursos where nombre like ?";
                pst = this.cnx.prepareStatement(query);
                pst.setString(1, "%" + texto_buscar + "%");
            }
            ResultSet rs = pst.executeQuery();
            CategoriaCursoControlador ccc = new CategoriaCursoControlador();
            ProfesorControlador pc = new ProfesorControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                cursos.add(new Curso(
                    rs.getString(1),
                    ccc.findId(rs.getString(2)),
                    pc.findId(rs.getString(3)),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6),
                    rs.getDate(7),
                    rs.getString(8),
                    rs.getDouble(9),
                    rs.getInt(10),
                    rs.getBoolean(11),
                    rs.getTimestamp(12),
                    rs.getTimestamp(13)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cursos;
    }

    @Override
    public MensajeRespuesta save(String id, CategoriaCurso categoriaCurso, 
            Profesor profesor, String nombre, String descripcion, Date fecInicio, 
            Date fecTermino, String duracion, Double costo, Boolean estado) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            ResultSet rs;
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            if (id.isEmpty()) {
                pst = this.cnx.prepareStatement("insert into cursos(categoria_cursos_id, "
                        + "profesores_id, nombre, descripcion, fec_inicio, fec_termino, "
                        + "duracion, costo, estado) VALUES (?, ?, ?, ?, ?, ?, "
                        + "?, ?, ?)", 1);
                pst.setString(1, categoriaCurso.getId());
                pst.setString(2, profesor.getId());
                pst.setString(3, nombre);
                pst.setString(4, descripcion);
                pst.setString(5, sdt.format(fecInicio));
                pst.setString(6, sdt.format(fecTermino));
                pst.setString(7, duracion);
                pst.setDouble(8, costo);
                pst.setBoolean(9, estado);
                pst.executeUpdate();
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    respuesta = new MensajeRespuesta(false, "Curso registrado correctamente.", 
                            new ObjetoDevuelto(rs.getString(1)), null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al registrar el Curso.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update cursos set categoria_cursos_id= ?, "
                        + "profesores_id= ?, nombre= ?, descripcion= ?, fec_inicio= ?, "
                        + "fec_termino= ?, duracion= ?, costo= ?, estado= ? "
                        + "where id= ?");
                pst.setString(1, categoriaCurso.getId());
                pst.setString(2, profesor.getId());
                pst.setString(3, nombre);
                pst.setString(4, descripcion);
                pst.setString(5, sdt.format(fecInicio));
                pst.setString(6, sdt.format(fecTermino));
                pst.setString(7, duracion);
                pst.setDouble(8, costo);
                pst.setBoolean(9, estado);
                pst.setString(10, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Curso actualizado correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar el Curso.", null, null);
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
            PreparedStatement pst = this.cnx.prepareStatement("delete from cursos where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Curso eliminado Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar el Curso.", null, null);
            }
        } catch (Exception e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public Curso findId(String id) {
        Curso empleado = null;
        try {
            String query = "select * from cursos where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                CategoriaCursoControlador ccc = new CategoriaCursoControlador();
                ProfesorControlador pc = new ProfesorControlador();
                empleado = new Curso(
                        rs.getString(1),
                        ccc.findId(rs.getString(2)),
                        pc.findId(rs.getString(3)),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getInt(10),
                        rs.getBoolean(11),
                        rs.getTimestamp(12),
                        rs.getTimestamp(13)
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return empleado;
    }
    
    public List<Curso> findByCategoriaCurso(String id) {
        List<Curso> cursos = new ArrayList<>();
        try {
            String query = "select * from cursos where categoria_cursos_id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            CategoriaCursoControlador ccc = new CategoriaCursoControlador();
            ProfesorControlador pc = new ProfesorControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                cursos.add(new Curso(
                    rs.getString(1),
                    ccc.findId(rs.getString(2)),
                    pc.findId(rs.getString(3)),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6),
                    rs.getDate(7),
                    rs.getString(8),
                    rs.getDouble(9),
                    rs.getInt(10),
                    rs.getBoolean(11),
                    rs.getTimestamp(12),
                    rs.getTimestamp(13)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cursos;
    }
    
    public List<Curso> findByProfesor(String id) {
        List<Curso> cursos = new ArrayList<>();
        try {
            String query = "select * from cursos where profesores_id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            CategoriaCursoControlador ccc = new CategoriaCursoControlador();
            ProfesorControlador pc = new ProfesorControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                cursos.add(new Curso(
                    rs.getString(1),
                    ccc.findId(rs.getString(2)),
                    pc.findId(rs.getString(3)),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6),
                    rs.getDate(7),
                    rs.getString(8),
                    rs.getDouble(9),
                    rs.getInt(10),
                    rs.getBoolean(11),
                    rs.getTimestamp(12),
                    rs.getTimestamp(13)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cursos;
    }
    
    public List<Curso> findByEstado(Boolean estado) {
        List<Curso> cursos = new ArrayList<>();
        try {
            String query = "select * from cursos where estado= '" + (estado ? 1 : 0) + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            CategoriaCursoControlador ccc = new CategoriaCursoControlador();
            ProfesorControlador pc = new ProfesorControlador();
            int i = 0;
            while(rs.next()) {
                i++;
                cursos.add(new Curso(
                    rs.getString(1),
                    ccc.findId(rs.getString(2)),
                    pc.findId(rs.getString(3)),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6),
                    rs.getDate(7),
                    rs.getString(8),
                    rs.getDouble(9),
                    rs.getInt(10),
                    rs.getBoolean(11),
                    rs.getTimestamp(12),
                    rs.getTimestamp(13)
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cursos;
    }
}
