package org.devyntubac.bean;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 19/05/2024
 */
public class DetalleCompra {

    private int codigoDetalleCompra;
    private Double costoUnitario;
    private int cantidad;
    private String codigoProducto;
    private int numeroDocumento;

    /**
     * Costructor vacio.
     */
    public DetalleCompra() {
    }

    /**
     * Costructor Lleno
     *
     * @param codigoDetalleCompra
     * @param costoUnitario
     * @param cantidad
     * @param codigoProducto
     * @param numeroDocumento
     */
    public DetalleCompra(int codigoDetalleCompra, Double costoUnitario, int cantidad, String codigoProducto, int numeroDocumento) {
        this.codigoDetalleCompra = codigoDetalleCompra;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.codigoProducto = codigoProducto;
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Getters y Setters Retorna el atributo. Recibe como parametros los
     * atributos.
     */
    public int getCodigoDetalleCompra() {
        return codigoDetalleCompra;
    }

    public void setCodigoDetalleCompra(int codigoDetalleCompra) {
        this.codigoDetalleCompra = codigoDetalleCompra;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}
