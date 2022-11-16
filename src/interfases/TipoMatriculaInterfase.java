/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.List;
import metodos.MensajeRespuesta;
import modelos.TipoMatricula;

/**
 *
 * @author Novoa
 */
public interface TipoMatriculaInterfase {
    
    List<TipoMatricula> index(String texto_buscar);
    
    MensajeRespuesta save(
            String id,
            String nombre,
            Double porcAplicado,
            Boolean estado
    );
    
    MensajeRespuesta delete(String id);
    
    TipoMatricula findId(String id);
}
