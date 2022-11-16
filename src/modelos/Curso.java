/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Novoa
 */
public class Curso {
    
    private String id;
    private CategoriaCurso categoriaCurso;
    private Profesor profesor;
    private String nombre;
    private String descripcion;
    private Date fecInicio;
    private Date fecTermino;
    private String duracion;
    private Double costo;
    private Integer cantMatriculados;
    private Boolean estado;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Curso() {
    }

    public Curso(String id, CategoriaCurso categoriaCurso, Profesor profesor, String nombre, String descripcion, Date fecInicio, Date fecTermino, String duracion, Double costo, Integer cantMatriculados, Boolean estado, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.categoriaCurso = categoriaCurso;
        this.profesor = profesor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecInicio = fecInicio;
        this.fecTermino = fecTermino;
        this.duracion = duracion;
        this.costo = costo;
        this.cantMatriculados = cantMatriculados;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CategoriaCurso getCategoriaCurso() {
        return categoriaCurso;
    }

    public void setCategoriaCurso(CategoriaCurso categoriaCurso) {
        this.categoriaCurso = categoriaCurso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(Date fecInicio) {
        this.fecInicio = fecInicio;
    }

    public Date getFecTermino() {
        return fecTermino;
    }

    public void setFecTermino(Date fecTermino) {
        this.fecTermino = fecTermino;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Integer getCantMatriculados() {
        return cantMatriculados;
    }

    public void setCantMatriculados(Integer cantMatriculados) {
        this.cantMatriculados = cantMatriculados;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        String serie = "";
        switch (this.getId().length()) {
            case 1:
                serie = "00" + this.getId();
                break;
            case 2:
                serie = "0" + this.getId();
                break;
            default:
                serie = this.getId();
                break;
        }
        return "COD" + serie + ": " + this.getNombre() + " - S/. " + this.getCosto();
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
        final Curso other = (Curso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.duracion, other.duracion)) {
            return false;
        }
        if (!Objects.equals(this.categoriaCurso, other.categoriaCurso)) {
            return false;
        }
        if (!Objects.equals(this.profesor, other.profesor)) {
            return false;
        }
        if (!Objects.equals(this.fecInicio, other.fecInicio)) {
            return false;
        }
        if (!Objects.equals(this.fecTermino, other.fecTermino)) {
            return false;
        }
        if (!Objects.equals(this.costo, other.costo)) {
            return false;
        }
        if (!Objects.equals(this.cantMatriculados, other.cantMatriculados)) {
            return false;
        }
        return true;
    }
}
