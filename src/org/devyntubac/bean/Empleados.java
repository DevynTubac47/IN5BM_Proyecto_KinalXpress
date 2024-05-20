package org.devyntubac.bean;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 19/05/2024
 */
public class Empleados {
    private int CodigoEmpleado;
    private String nombresEmpleado;
    private String apellidosEmpleado;
    private Double sueldo;
    private String direccion;
    private String turno;
    private int codigoCargoEmpleado;

    /**
     * Costructor vacio.
     */
    public Empleados() {
    }

    /**
     * Costructor Lleno
     * @param CodigoEmpleado
     * @param nombresEmpleado
     * @param apellidosEmpleado
     * @param sueldo
     * @param direccion
     * @param turno
     * @param codigoCargoEmpleado 
     */
    public Empleados(int CodigoEmpleado, String nombresEmpleado, String apellidosEmpleado, Double sueldo, String direccion, String turno, int codigoCargoEmpleado) {
        this.CodigoEmpleado = CodigoEmpleado;
        this.nombresEmpleado = nombresEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.sueldo = sueldo;
        this.direccion = direccion;
        this.turno = turno;
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    /**
     * Getters y Setters
     * Retorna el atributo.
     * Recibe como parametros los atributos.
     */
    public int getCodigoEmpleado() {
        return CodigoEmpleado;
    }

    public void setCodigoEmpleado(int CodigoEmpleado) {
        this.CodigoEmpleado = CodigoEmpleado;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getCodigoCargoEmpleado() {
        return codigoCargoEmpleado;
    }

    public void setCodigoCargoEmpleado(int codigoCargoEmpleado) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    @Override
    public String toString() {
        return "| " + getCodigoEmpleado() + " | " + getNombresEmpleado() + " | " + getApellidosEmpleado() + " | " + getTurno() + " |";
    }


}
