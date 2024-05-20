package org.devyntubac.bean;

import java.sql.Blob;
import javafx.scene.image.Image;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 08/05/2024
 */
public class Productos {
    private String codigoProducto;
    private String descripcionProducto;
    private Double precioUnitario;
    private Double precioDocena;
    private Double precioMayor;
    private String imagenProducto;
    private int existencia;
    private int codigoTipoProducto;
    private int codigoProveedor;
    private Image imgProducto;
    
    /**
     * Costructor Vacio.
     */
    public Productos() {
    }

    /**
     * Costructor Lleno.
     * @param codigoProducto
     * @param descripcionProducto
     * @param precioUnitario
     * @param precioDocena
     * @param precioMayor
     * @param imagenProducto
     * @param existencia
     * @param codigoTipoProducto
     * @param codigoProveedor 
     */
    
    public Productos(String codigoProducto, String descripcionProducto, Double precioUnitario, Double precioDocena, Double precioMayor, String imagenProducto, int existencia, int codigoTipoProducto, int codigoProveedor) {
        this.codigoProducto = codigoProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioUnitario = precioUnitario;
        this.precioDocena = precioDocena;
        this.precioMayor = precioMayor;
        this.imagenProducto = imagenProducto;
        this.existencia = existencia;
        this.codigoTipoProducto = codigoTipoProducto;
        this.codigoProveedor = codigoProveedor;
    }

    /**
     * Getters y Setters
     * Retorna el atributo.
     * Recibe como parametros los atributos.
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(Double precioDocena) {
        this.precioDocena = precioDocena;
    }

    public Double getPrecioMayor() {
        return precioMayor;
    }

    public void setPrecioMayor(Double precioMayor) {
        this.precioMayor = precioMayor;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public Image getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(Image imgProducto) {
        this.imgProducto = imgProducto;
    }

    @Override
    public String toString() {
        return "| " + getCodigoProducto() + " | " + getPrecioUnitario() + " | " + getImagenProducto() + " | " + getExistencia() + "|";
    }
    
}
