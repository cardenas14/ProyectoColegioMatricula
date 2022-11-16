/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Novoa
 */
public class DetalleMatricula {
    
    private String id;
    private Matricula matricula;
    private Curso curso;
    private Double costo;
    private Double subtotal;
    

    public DetalleMatricula() {
    }

    public DetalleMatricula(String id, Matricula matricula, Curso curso, Double costo, Double subtotal) {
        this.id = id;
        this.matricula = matricula;
        this.curso = curso;
        this.costo = costo;
        this.subtotal = subtotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    @Override
    public String toString() {
        return this.getCurso().toString();
    }
}
