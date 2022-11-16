/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.jtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Cliente;

/**
 *
 * @author Novoa
 */
public class ClienteTableModel extends AbstractTableModel {

    private String[] columns = new String[]{"N°", "TIPO DOC.", "NUM. DOC.", "NOMBRES", "APELLIDOS", "DIRECCIÓN", "TELEFONO", "ACTIVO"};
    private List<Cliente> clientes;

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    
    @Override
    public int getRowCount() {
        return this.clientes.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        Cliente cliente = this.clientes.get(rowIndex);
        switch(columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = cliente.getTipoDocumento().getNombre();
                break;
            case 2:
                value = cliente.getNumDoc();
                break;
            case 3:
                value = cliente.getPriNom() + (cliente.getSegNom() != null ? " " + cliente.getSegNom() : "");
                break;
            case 4:
                value = cliente.getApePat() + " " + cliente.getApeMat();
                break;
            case 5:
                value = cliente.getDireccion();
                break;
            case 6:
                value = cliente.getTelefono();
                break;
            case 7:
                value = cliente.getEstado() ? "SI" : "NO";
                break;
        }
        return value;
    }
    
    public Cliente getClienteAt(int row) {
        return this.clientes.get(row);
    }
}
