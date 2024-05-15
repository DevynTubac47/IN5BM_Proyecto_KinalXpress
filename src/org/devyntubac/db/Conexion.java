package org.devyntubac.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    
    /**
     * Constructor de la clase, establece una conexión a la base de datos MySQL.
     */
    public Conexion(){
        try{
           Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
           conexion = DriverManager.getConnection("jdbc:mysql://localhost/dbKinalXpress?SSL=false","root","Tottus47d");
        }catch(ClassNotFoundException  e){
            e.printStackTrace();
        }catch(InstantiationException a){
            a.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException a){
            a.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Getter y Setter
     * Obtienen y establecen la conexion con la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Método para obtener una instancia única de la clase Conexion.
     * @return La instancia única de la clase Conexion.
     */
    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
}
