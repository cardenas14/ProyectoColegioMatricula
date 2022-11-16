/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Usuario;

/**
 *
 * @author Novoa
 */
public class UsuarioTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "TIPO DOC.", "NUM. DOC.", "NOMBRES", "APELLIDOS", "USUARIO", "PERFIL", "ACTIVO"};
    private List<Usuario> usuarios;

    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        Usuario usuario = this.usuarios.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = usuario.getEmpleado().getTipoDocumento().getNombre();
                break;
            case 2:
                value = usuario.getEmpleado().getNumDoc();
                break;
            case 3:
                value = usuario.getEmpleado().getPriNom() + (usuario.getEmpleado().getSegNom() != null ? " " + usuario.getEmpleado().getSegNom() : "");
                break;
            case 4:
                value = usuario.getEmpleado().getApePat() + " " + usuario.getEmpleado().getApeMat();
                break;
            case 5:
                value = usuario.getEmail();
                break;
            case 6:
                value = usuario.getPerfil();
                break;
            case 7:
                value = usuario.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public Usuario getUsuarioAt(int row) {
        return this.usuarios.get(row);
    }
}
