/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Objects;

/**
 *
 * @author Novoa
 */
public class TipoMatricula {
    
    private String id;
    private String nombre;
    private Double porcAplicado;
    private Boolean estado;

    public TipoMatricula() {
    }

    public TipoMatricula(String id, String nombre, Double porcAplicado, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.porcAplicado = porcAplicado;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPorcAplicado() {
        return porcAplicado;
    }

    public void setPorcAplicado(Double porcAplicado) {
        this.porcAplicado = porcAplicado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoMatricula other = (TipoMatricula) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.porcAplicado, other.porcAplicado)) {
            return false;
        }
        return true;
    }
}
