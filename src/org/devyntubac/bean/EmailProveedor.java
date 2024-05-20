package org.devyntubac.bean;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 19/05/2024
 */
public class EmailProveedor {
    private int codigoEmailProveedor;
    private String emailProveedor;
    private String descripcion;
    private int codigoProveedor;

    /**
     * Costructor vacio.
     */
    public EmailProveedor() {
    }

    /**
     * Costructor Lleno.
     * @param codigoEmailProveedor
     * @param emailProveedor
     * @param descripcion
     * @param codigoProveedor 
     */
    public EmailProveedor(int codigoEmailProveedor, String emailProveedor, String descripcion, int codigoProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        this.codigoProveedor = codigoProveedor;
    }

    /**
     * Getters y Setters
     * Retorna el atributo.
     * Recibe como parametros los atributos.
     */
    public int getCodigoEmailProveedor() {
        return codigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int codigoEmailProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    
    
}
