/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.Date;
import java.util.List;
import metodos.MensajeRespuesta;
import modelos.Cliente;
import modelos.TipoDocumento;

/**
 *
 * @author Novoa
 */
public interface ClienteInterfase {
    
    List<Cliente> index(String texto);
    
    MensajeRespuesta save(String id, TipoDocumento tipoDocumento,
            String numDoc, String priNom, String segNom, String apePat, 
            String apeMat, Date fecNac, String genero, String direccion, 
            String telefono, Boolean estado);
    
    MensajeRespuesta delete(String id);
    
    Cliente findId(String id);
}
