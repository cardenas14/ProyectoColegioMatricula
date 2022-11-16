/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.List;
import metodos.MensajeRespuesta;
import modelos.CategoriaCurso;

/**
 *
 * @author Novoa
 */
public interface CategoriaCursoInterfase {
    
    List<CategoriaCurso> index(String texto_buscar);
    
    MensajeRespuesta save(
            String id,
            String nombre,
            String descripcion,
            Boolean estado
    );
    
    MensajeRespuesta delete(String id);
    
    CategoriaCurso findId(String id);
}
