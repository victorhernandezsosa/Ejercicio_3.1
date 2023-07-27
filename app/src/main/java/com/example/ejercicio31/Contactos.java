package com.example.ejercicio31;

public class Contactos {

    private String ID;
    private String Cuenta;
    private String Nombre;
    private String Apellido;

    public Contactos(String ID, String cuenta, String nombre, String apellido) {
        this.ID = ID;
        Cuenta = cuenta;
        Nombre = nombre;
        Apellido = apellido;
    }

    public Contactos() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
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

    @Override
    public String toString() {
        return Nombre + " " + Apellido;
    }

}
