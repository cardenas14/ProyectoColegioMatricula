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
public class Matricula {
    
    private String id;
    private Empleado empleado;
    private Cliente cliente;
    private TipoMatricula tipoMatricula;
    private Double porcAplicado;
    private Double montoAplicado;
    private TipoComprobante tipoComprobante;
    private String numComprobante;
    private Date fecha;
    private Double total;
    private String motivoAnulacion;
    private Date fechaAnulacion;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Matricula() {
    }

    public Matricula(String id, Empleado empleado, Cliente cliente, TipoMatricula tipoMatricula, Double porcAplicado, Double montoAplicado, TipoComprobante tipoComprobante, String numComprobante, Date fecha, Double total, String motivoAnulacion, Date fechaAnulacion, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.empleado = empleado;
        this.cliente = cliente;
        this.tipoMatricula = tipoMatricula;
        this.porcAplicado = porcAplicado;
        this.montoAplicado = montoAplicado;
        this.tipoComprobante = tipoComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.total = total;
        this.motivoAnulacion = motivoAnulacion;
        this.fechaAnulacion = fechaAnulacion;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoMatricula getTipoMatricula() {
        return tipoMatricula;
    }

    public void setTipoMatricula(TipoMatricula tipoMatricula) {
        this.tipoMatricula = tipoMatricula;
    }

    public Double getPorcAplicado() {
        return porcAplicado;
    }

    public void setPorcAplicado(Double porcAplicado) {
        this.porcAplicado = porcAplicado;
    }

    public Double getMontoAplicado() {
        return montoAplicado;
    }

    public void setMontoAplicado(Double montoAplicado) {
        this.montoAplicado = montoAplicado;
    }

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
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
        return getCliente().toString();
    }    
}
