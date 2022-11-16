/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Profesor;

/**
 *
 * @author Novoa
 */
public class ProfesorTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "TIPO DOC.", "NUM. DOC.", "NOMBRES", "APELLIDOS", "DIRECCIÓN", "TELEFONO", "CARGO", "ACTIVO"};
    private List<Profesor> profesores;

    public ProfesorTableModel(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.profesores.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        Profesor profesor = this.profesores.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = profesor.getTipoDocumento().getNombre();
                break;
            case 2:
                value = profesor.getNumDoc();
                break;
            case 3:
                value = profesor.getPriNom() + (profesor.getSegNom() != null ? " " + profesor.getSegNom() : "");
                break;
            case 4:
                value = profesor.getApePat() + " " + profesor.getApeMat();
                break;
            case 5:
                value = profesor.getDireccion();
                break;
            case 6:
                value = profesor.getTelefono();
                break;
            case 7:
                value = profesor.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public Profesor getProfesorAt(int row) {
        return this.profesores.get(row);
    }
}
