package org.devyntubac.bean;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class CargoEmpleado {
    private int codigoCargoEmpleado;
    private String nombreCargo;
    private String descripcionCargo;

    /**
     * Costructor vacío.
     */
    public CargoEmpleado() {
    }

    /**
     * Costructor lleno.
     * @param codigoCargoEmpleado
     * @param nombreCargo
     * @param descripcionCargo 
     */
    public CargoEmpleado(int codigoCargoEmpleado, String nombreCargo, String descripcionCargo) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    /**
     * Getters y Setters
     * Retorna el atributo.
     * Recibe como parametros los atributos.
     */
    public int getCodigoCargoEmpleado() {
        return codigoCargoEmpleado;
    }

    public void setCodigoCargoEmpleado(int codigoCargoEmpleado) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    @Override
    public String toString() {
        return "| " + getCodigoCargoEmpleado() + " | " + getNombreCargo() + " | ";
    }
}

