/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.TipoComprobante;

/**
 *
 * @author Novoa
 */
public class TipoComprobanteTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "NOMBRE", "ABREVIATURA", "CORREATIVO", "ESTADO"};
    private List<TipoComprobante> tipoComprobantes;

    public TipoComprobanteTableModel(List<TipoComprobante> tipoComprobantes) {
        this.tipoComprobantes = tipoComprobantes;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.tipoComprobantes.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        TipoComprobante tipoComprobante = this.tipoComprobantes.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = tipoComprobante.getNombre();
                break;
            case 2:
                value = tipoComprobante.getAbreviatura();
                break;
            case 3:
                value = tipoComprobante.getCorrelativo();
                break;
            case 4:
                value = tipoComprobante.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public TipoComprobante getTipoComprobanteAt(int row) {
        return this.tipoComprobantes.get(row);
    }
}
