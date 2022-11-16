/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.List;
import metodos.MensajeRespuesta;
import modelos.Curso;
import modelos.DetalleMatricula;

/**
 *
 * @author Novoa
 */
public interface DetalleMatriculaInterfase {
    
    List<DetalleMatricula> index(String matriculaId);
    
    MensajeRespuesta save(
            String matriculaId,
            List<DetalleMatricula> detalleMatriculas
    );
}
