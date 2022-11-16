/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.List;
import metodos.MensajeRespuesta;
import modelos.TipoComprobante;

/**
 *
 * @author Novoa
 */
public interface TipoComprobanteInterfase {
    
    List<TipoComprobante> index(String texto_buscar);
    
    MensajeRespuesta save(
            String id,
            String nombre,
            String abreviatura,
            Integer correlativo,
            Boolean estado
    );
    
    MensajeRespuesta delete(String id);
    
    TipoComprobante findId(String id);
}
