/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Curso;

/**
 *
 * @author Novoa
 */
public class CursoTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "CATEGORÍA", "PROFESOR", "NOMBRE DEL CURSO", "FECHA INICIO", "FECHA TERMINO", "DURACION", "COSTO", "TOTAL INSCRITOS"};
    private List<Curso> cursos;

    public CursoTableModel(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.cursos.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        Curso curso = this.cursos.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = curso.getCategoriaCurso().getNombre();
                break;
            case 2:
                value = curso.getProfesor().toString();
                break;
            case 3:
                value = curso.getNombre();
                break;
            case 4:
                value = curso.getFecInicio();
                break;
            case 5:
                value = curso.getFecTermino();
                break;
            case 6:
                value = curso.getDuracion();
                break;
            case 7:
                value = curso.getCosto();
                break;
            case 8:
                value = curso.getCantMatriculados();
                break;
        }
        return value;
    }
    
    public Curso getCursoAt(int row) {
        return this.cursos.get(row);
    }
}
