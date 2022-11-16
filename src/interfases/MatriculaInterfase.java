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
import modelos.Empleado;
import modelos.Matricula;
import modelos.TipoComprobante;
import modelos.TipoMatricula;

/**
 *
 * @author Novoa
 */
public interface MatriculaInterfase {
    
    List<Matricula> index(
            String texto_buscar,
            String tipo_matriculas_id,
            String tipo_comprobantes_id,
            String fecDesde,
            String fecHasta
    );
    
    MensajeRespuesta save(
            String id,
            Empleado empleado,
            Cliente cliente,
            TipoMatricula tipoMatricula,
            Double porcAplicado,
            Double montoAplicado,
            TipoComprobante tipoComprobante,
            String numComprobante,
            Date fecha,
            Double total,
            String motivoAnulacion,
            Date fechaAnulacion
    );
    
    MensajeRespuesta delete(String id);
    
    Matricula findId(String id);
}
