package org.devyntubac.bean;

import java.sql.Date;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 08/05/2024
 *
 */
public class Compras {

    private int numeroDocumento;
    private Date fechaDocumento;
    private String descripcion;
    private double totalDocumento;

    /**
     * Constructor Vacio.
     */
    public Compras() {
    }

    /**
     * Costructor Lleno.
     *
     * @param numeroDocumento
     * @param fechaDocumento
     * @param descripcion
     * @param totalDocumento
     */
    public Compras(int numeroDocumento, Date fechaDocumento, String descripcion, double totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    /**
     * Getters y Setters Retorna el atributo. Recibe como parametros los
     * atributos.
     */
    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

    @Override
    public String toString() {
        return "| " + getNumeroDocumento() + " | " + getFechaDocumento() + " | " + getTotalDocumento() + " |";
    }

}
