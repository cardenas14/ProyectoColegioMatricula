/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import interfases.TipoDocumentoInterfase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import metodos.Conexion;
import modelos.TipoDocumento;

/**
 *
 * @author Novoa
 */
public class TipoDocumentoControlador implements TipoDocumentoInterfase {
    
    Connection cnx;

    public TipoDocumentoControlador() {
        this.cnx = Conexion.abrir_conexion();
    }
    
    @Override
    public List<TipoDocumento> index() {
        ArrayList<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento>();
        try {
            String query = "select * from tipo_documentos";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                tipoDocumentos.add(new TipoDocumento(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return tipoDocumentos;
    }
    
    @Override
    public TipoDocumento findId(String id) {
        TipoDocumento tipoDocumento = new TipoDocumento();
        try {
            String query = "select * from tipo_documentos where id= '" + id + "'";
            Statement st = this.cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                tipoDocumento.setId(rs.getString(1));
                tipoDocumento.setNombre(rs.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
        return tipoDocumento;
    }
}
