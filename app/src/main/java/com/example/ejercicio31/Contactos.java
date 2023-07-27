package com.example.ejercicio31;

public class Contactos {

    private String ID;
    private String Foro;
    private String Nombre;
    private String Apellido;

    private String Genero;
    private String Fecha;

    public Contactos(String ID, String foro, String nombre, String apellido, String genero, String fecha) {
        this.ID = ID;
        Foro = foro;
        Nombre = nombre;
        Apellido = apellido;
        Genero = genero;
        Fecha = fecha;
    }

    public Contactos() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getForo() {
        return Foro;
    }

    public void setForo(String foro) {
        Foro = foro;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    @Override
    public String toString() {
        return Nombre + " " + Apellido;
    }

}
