package org.devyntubac.bean;

/**
 *
 * @author devyn
 */
public class Usuario {
    private int idUsuario;
    private String email;
    private String contrasenia;
    private String nombreUsuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, String email, String contrasenia, String nombreUsuario) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    
}
