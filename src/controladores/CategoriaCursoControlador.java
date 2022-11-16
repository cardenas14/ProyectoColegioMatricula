/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.CategoriaCursoInterfase;
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
import modelos.CategoriaCurso;

/**
 *
 * @author Novoa
 */
public class CategoriaCursoControlador implements CategoriaCursoInterfase {
    
    Connection cnx;

    public CategoriaCursoControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<CategoriaCurso> index(String texto_buscar) {
        ArrayList<CategoriaCurso> categoriaCursos = new ArrayList<>();
        String query = "";
        try {
            PreparedStatement pst;
            if (texto_buscar.isEmpty()) {
                query = "select * from categoria_cursos";
                pst = this.cnx.prepareStatement(query);
            } else {
                query = "select * from categoria_cursos where nombre like ?";
                pst = this.cnx.prepareStatement(query);
                pst.setString(1, "%" + texto_buscar + "%");
            }
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                categoriaCursos.add(new CategoriaCurso(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4)
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return categoriaCursos;
    }

    @Override
    public MensajeRespuesta save(String id, String nombre, String descripcion, Boolean estado) {
        MensajeRespuesta respuesta = null;
        try {
            PreparedStatement pst;
            if (id.isEmpty()) {
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery("select * from categoria_cursos where nombre= '" + nombre + "'");
                if (!rs.next()) {
                    pst = this.cnx.prepareStatement("insert into categoria_cursos(nombre, "
                            + "descripcion, estado) values (?, ?, ?)", 1);
                    pst.setString(1, nombre);
                    pst.setString(2, descripcion);
                    pst.setBoolean(3, estado);
                    pst.executeUpdate();
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        respuesta = new MensajeRespuesta(false, "Categoria registrada correctamente.", 
                                new ObjetoDevuelto(rs.getString(1)), null);
                    } else {
                        respuesta = new MensajeRespuesta(true, "Error al registrar la Categoria.", null, null);
                    }
                } else {
                    respuesta = new MensajeRespuesta(true, "La Categoria ya se encuentra registrado.", null, null);
                }
            } else {
                pst = this.cnx.prepareStatement("update categoria_cursos set nombre= ?, "
                        + "descripcion= ?, estado= ? where id= ?");
                pst.setString(1, nombre);
                pst.setString(2, descripcion);
                pst.setBoolean(3, estado);
                pst.setString(4, id);
                int execute = pst.executeUpdate();
                if (execute == 1) {
                    respuesta = new MensajeRespuesta(false, "Categoria actualizada correctamente.", null, null);
                } else {
                    respuesta = new MensajeRespuesta(true, "Error al actualizar la Categoria.", null, null);
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
            PreparedStatement pst = this.cnx.prepareStatement("delete from categoria_cursos where id= ?");
            pst.setString(1, id);
            int execute = pst.executeUpdate();
            if (execute == 1) {
                respuesta = new MensajeRespuesta(false, "Categoria eliminada Correctamente.", null, null);
            } else {
                respuesta = new MensajeRespuesta(true, "Error al eliminar la Categoria.", null, null);
            }
        } catch (SQLException e) {
            respuesta = new MensajeRespuesta(true, e.getMessage(), null, null);
        }
        return respuesta;
    }
    
    @Override
    public CategoriaCurso findId(String id) {
        CategoriaCurso categoriaCurso = new CategoriaCurso();
        try {
            String query = "select * from categoria_cursos where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                categoriaCurso.setId(rs.getString(1));
                categoriaCurso.setNombre(rs.getString(2));
                categoriaCurso.setDescripcion(rs.getString(3));
                categoriaCurso.setEstado(rs.getBoolean(2));
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
        return categoriaCurso;
    }
}
