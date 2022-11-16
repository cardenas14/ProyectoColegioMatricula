/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Novoa
 */
public class HelperServicio {
    
    public static String correlativo(String correlativo) {
        String numComprobante = "0";
        switch (correlativo.length()) {
            case 1:
                numComprobante = "0000" + correlativo;
                break;
            case 2:
                numComprobante = "000" + correlativo;
                break;
            case 3:
                numComprobante = "00" + correlativo;
                break;
            case 4:
                numComprobante = "0" + correlativo;
                break;
            default:
                numComprobante = correlativo;
                break;
        }
        return numComprobante;
    }
    
    public List errorExepcion(Exception e) {
        List errors = new ArrayList();
        errors.add(e.getMessage());
        errors.add(e.toString());
        errors.add(e.getLocalizedMessage());
        return errors;
    }
}
