/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.util.List;
import modelos.Usuario;

/**
 *
 * @author Novoa
 */
public class AuthRespuesta {
    
    private Boolean error;
    private String mensaje;
    private Usuario usuario;
    private List datos;

    public AuthRespuesta() {
    }

    public AuthRespuesta(Boolean error, String mensaje, Usuario usuario, List datos) {
        this.error = error;
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.datos = datos;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List getDatos() {
        return datos;
    }

    public void setDatos(List datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
