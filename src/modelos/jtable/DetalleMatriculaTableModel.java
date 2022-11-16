/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.DetalleMatricula;

/**
 *
 * @author Novoa
 */
public class DetalleMatriculaTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"CURSO", "PRECIO", "SUBTOTAL"};
    private List<DetalleMatricula> detalleMatriculas;

    public DetalleMatriculaTableModel(List<DetalleMatricula> detalleMatriculas) {
        this.detalleMatriculas = detalleMatriculas;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.detalleMatriculas.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        DetalleMatricula detalleMatricula = this.detalleMatriculas.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = detalleMatricula.getCurso().getNombre();
                break;
            case 1:
                value = detalleMatricula.getCosto();
                break;
            case 2:
                value = detalleMatricula.getSubtotal();
                break;
        }
        return value;
    }
    
    public DetalleMatricula getDetalleMatriculaAt(int row) {
        return this.detalleMatriculas.get(row);
    }
}
