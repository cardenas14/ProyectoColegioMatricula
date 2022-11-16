/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Matricula;

/**
 *
 * @author Novoa
 */
public class MatriculaTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "COMPROBANTE", "FECHA", "EMPLEADO", "CLIENTE", "TIPO MATRICULA", "ADICIONAL EN (%)", "MONTO EN (S/.)", "TOTAL", "¿ANULADO?", "MOTIVO DE ANULACIÓN"};
    private List<Matricula> matriculas;

    public MatriculaTableModel(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.matriculas.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        Matricula matricula = this.matriculas.get(rowIndex);
        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = matricula.getNumComprobante();
                break;
            case 2:
                value = sdt.format(matricula.getFecha());
                break;
            case 3:
                value = matricula.getEmpleado().toString();
                break;
            case 4:
                value = matricula.getCliente().toString();
                break;
            case 5:
                value = matricula.getTipoMatricula().getNombre();
                break;
            case 6:
                value = matricula.getTipoMatricula().getPorcAplicado() + "%";
                break;
            case 7:
                value = "S/. " + matricula.getMontoAplicado();
                break;
            case 8:
                value = "S/. " + matricula.getTotal();
                break;
            case 9:
                value = matricula.getFechaAnulacion() != null ? "SÍ" : "NO";
                break;
            case 10:
                value = matricula.getMotivoAnulacion();
                break;
        }
        return value;
    }
    
    public Matricula getMatriculaAt(int row) {
        return this.matriculas.get(row);
    }
}
