/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.TipoMatricula;

/**
 *
 * @author Novoa
 */
public class TipoMatriculaTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "NOMBRE", "DESCRIPCIÓN", "ESTADO"};
    private List<TipoMatricula> tipoMatriculas;

    public TipoMatriculaTableModel(List<TipoMatricula> tipoMatriculas) {
        this.tipoMatriculas = tipoMatriculas;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.tipoMatriculas.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        TipoMatricula tipoMatricula = this.tipoMatriculas.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = tipoMatricula.getNombre();
                break;
            case 2:
                value = tipoMatricula.getPorcAplicado();
                break;
            case 3:
                value = tipoMatricula.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public TipoMatricula getTipoMatriculaAt(int row) {
        return this.tipoMatriculas.get(row);
    }
}
