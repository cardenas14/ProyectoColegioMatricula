/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Empleado;

/**
 *
 * @author Novoa
 */
public class EmpleadoTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "TIPO DOC.", "NUM. DOC.", "NOMBRES", "APELLIDOS", "DIRECCIÓN", "TELEFONO", "CARGO", "ACTIVO"};
    private List<Empleado> empleados;

    public EmpleadoTableModel(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.empleados.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        Empleado empleado = this.empleados.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = empleado.getTipoDocumento().getNombre();
                break;
            case 2:
                value = empleado.getNumDoc();
                break;
            case 3:
                value = empleado.getPriNom() + (empleado.getSegNom() != null ? " " + empleado.getSegNom() : "");
                break;
            case 4:
                value = empleado.getApePat() + " " + empleado.getApeMat();
                break;
            case 5:
                value = empleado.getDireccion();
                break;
            case 6:
                value = empleado.getTelefono();
                break;
            case 7:
                value = empleado.getCargo();
                break;
            case 8:
                value = empleado.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public Empleado getEmpleadoAt(int row) {
        return this.empleados.get(row);
    }
}
