package org.devyntubac.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author devyn
 */
public class SesionIniciada {

    private static boolean sesionIniciada = false;
    private static String usuarioActual;
    private static String archivo = "sesionIniciada.txt";

    public static boolean sesionInciada() {
        return sesionIniciada;
    }

    public static void establecerSesion(boolean inicioSesion) {
        sesionIniciada = inicioSesion;
    }

    public static String usuarioActual() {
        return usuarioActual;
    }

    public static void obtenerUsuario(String nombreUsuario) {
        usuarioActual = nombreUsuario;
    }
    
    public static void guardarEstado(){
        try(PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println(sesionIniciada);
            writer.println(usuarioActual);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void cargarEstado(){
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            sesionIniciada = Boolean.parseBoolean(reader.readLine());
            usuarioActual = reader.readLine();
        } catch (IOException e) {
            sesionIniciada = false;
            usuarioActual = null;
        }
    }
    
    public static void eliminarEstadoSesion() {
        File file = new File(archivo);
        if (file.exists()) {
            file.delete();
        }
    }
    
}
