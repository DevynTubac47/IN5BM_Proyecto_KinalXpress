package org.devyntubac.bean;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class Clientes {
    private int clienteID;
    private String nombresCliente;
    private String apellidosCliente;
    private String NITClientes;
    private String direccionCliente;
    private String telefonoCliente;
    private String correoCliente;

    /**
     * Costructor vacio.
     */
    public Clientes() {
    }
    
    /**
     * Costructor lleno. 
     * @param clienteID
     * @param nombresCliente
     * @param apellidosCliente
     * @param NITClientes
     * @param direccionCliente
     * @param telefonoCliente
     * @param correoCliente 
     */
    public Clientes(int clienteID, String nombresCliente, String apellidosCliente, String NITClientes, String direccionCliente, String telefonoCliente, String correoCliente) {
        this.clienteID = clienteID;
        this.nombresCliente = nombresCliente;
        this.apellidosCliente = apellidosCliente;
        this.NITClientes = NITClientes;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
    }

    /**
     * Getters y Setters
     * Retorna el atributo.
     * Recibe como parametros los atributos.
     */
    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getNITClientes() {
        return NITClientes;
    }

    public void setNITClientes(String NITClientes) {
        this.NITClientes = NITClientes;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }
 
}
