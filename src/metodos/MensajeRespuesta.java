/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.util.List;

/**
 *
 * @author Novoa
 */
public class MensajeRespuesta {
    
    private Boolean error;
    private String mensaje;
    private ObjetoDevuelto objetoDevuelto;
    private List datos;

    public MensajeRespuesta() {
    }

    public MensajeRespuesta(Boolean error, String mensaje, ObjetoDevuelto objetoDevuelto, List datos) {
        this.error = error;
        this.mensaje = mensaje;
        this.objetoDevuelto = objetoDevuelto;
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

    public ObjetoDevuelto getObjetoDevuelto() {
        return objetoDevuelto;
    }

    public void setObjetoDevuelto(ObjetoDevuelto objetoDevuelto) {
        this.objetoDevuelto = objetoDevuelto;
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
