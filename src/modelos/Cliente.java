/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Novoa
 */
public class Cliente {
    
    private String id;
    private TipoDocumento tipoDocumento;
    private String numDoc;
    private String priNom;
    private String segNom;
    private String apePat;
    private String apeMat;
    private Date fecNac;
    private String genero;
    private String direccion;
    private String telefono;
    private Boolean estado;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Cliente() {
    }

    public Cliente(String id, TipoDocumento tipoDocumento, String numDoc, String priNom, String segNom, String apePat, String apeMat, Date fecNac, String genero, String direccion, String telefono, Boolean estado, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numDoc = numDoc;
        this.priNom = priNom;
        this.segNom = segNom;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.fecNac = fecNac;
        this.genero = genero;
        this.direccion = direccion;
        this.telefono = telefono;
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

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getPriNom() {
        return priNom;
    }

    public void setPriNom(String priNom) {
        this.priNom = priNom;
    }

    public String getSegNom() {
        return segNom;
    }

    public void setSegNom(String segNom) {
        this.segNom = segNom;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        return this.getPriNom() + (this.getSegNom() != null ? " "
                + this.getSegNom() + " " : " ") + this.getApeMat() + " "
                + this.getApeMat();
    }
}
