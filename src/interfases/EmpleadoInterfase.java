/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.Date;
import java.util.List;
import metodos.MensajeRespuesta;
import modelos.Empleado;
import modelos.TipoDocumento;

/**
 *
 * @author Novoa
 */
public interface EmpleadoInterfase {
    
    List<Empleado> index(String texto);
    
    MensajeRespuesta save(String id, TipoDocumento tipoDocumento,
            String numDoc, String priNom, String segNom, String apePat, 
            String apeMat, Date fecNac, String genero, String direccion, 
            String telefono, String cargo, Boolean estado);
    
    MensajeRespuesta delete(String id);
    
    Empleado findId(String id);
}
