/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.Date;
import java.util.List;
import metodos.MensajeRespuesta;
import modelos.CategoriaCurso;
import modelos.Curso;
import modelos.Profesor;

/**
 *
 * @author Novoa
 */
public interface CursoInterfase {
    
    List<Curso> index(String texto);
    
    MensajeRespuesta save(
            String id,
            CategoriaCurso categoriaCurso,
            Profesor profesor,
            String nombre,
            String descripcion,
            Date fecInicio,
            Date fecTermino,
            String duracion,
            Double costo,
            Boolean estado
    );
    
    MensajeRespuesta delete(String id);
    
    Curso findId(String id);
}
