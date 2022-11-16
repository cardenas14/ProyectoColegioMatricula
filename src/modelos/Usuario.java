/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Timestamp;

/**
 *
 * @author Novoa
 */
public class Usuario {
    
    private String id;
    private Empleado empleado;
    private String email;
    private String password;
    private String perfil;
    private Boolean estado;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Usuario() {
    }

    public Usuario(String id, Empleado empleado, String email, String password, String perfil, Boolean estado, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.empleado = empleado;
        this.email = email;
        this.password = password;
        this.perfil = perfil;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
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
        return this.getEmpleado().toString();
    }
}
