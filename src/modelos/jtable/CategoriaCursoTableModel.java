/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.CategoriaCurso;

/**
 *
 * @author Novoa
 */
public class CategoriaCursoTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "NOMBRE", "DESCRIPCIÓN", "ESTADO"};
    private List<CategoriaCurso> categoriaCursos;

    public CategoriaCursoTableModel(List<CategoriaCurso> categoriaCursos) {
        this.categoriaCursos = categoriaCursos;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.categoriaCursos.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        CategoriaCurso categoriaCurso = this.categoriaCursos.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = categoriaCurso.getNombre();
                break;
            case 2:
                value = categoriaCurso.getDescripcion();
                break;
            case 3:
                value = categoriaCurso.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public CategoriaCurso getCategoriaCursoAt(int row) {
        return this.categoriaCursos.get(row);
    }
}
