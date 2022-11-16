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
public class TipoComprobante {
    
    private String id;
    private String nombre;
    private String abreviatura;
    private Integer correlativo;
    private Boolean estado;

    public TipoComprobante() {
    }

    public TipoComprobante(String id, String nombre, String abreviatura, Integer correlativo, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.correlativo = correlativo;
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
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
        final TipoComprobante other = (TipoComprobante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.abreviatura, other.abreviatura)) {
            return false;
        }
        return true;
    }
}
