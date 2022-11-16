/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.List;
import metodos.MensajeRespuesta;
import modelos.Empleado;
import modelos.Usuario;

/**
 *
 * @author Novoa
 */
public interface UsuarioInterfase {
    
    List<Usuario> index(String texto);
    
    MensajeRespuesta save(String id, Empleado empleado, String email,
            String password, String perfil, Boolean estado);
    
    MensajeRespuesta delete(String id);
    
    Usuario findId(String id);
}
