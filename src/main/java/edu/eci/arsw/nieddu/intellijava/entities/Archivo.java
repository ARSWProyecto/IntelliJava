package edu.eci.arsw.nieddu.intellijava.entities;

import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David Useche
 */
public class Archivo {

    private String nombre;
    private String texto;

    /**
     * Obtiene el nombre del archiv
     *
     * @return nombre del archivo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del archivo
     *
     * @param nombre del archivo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el texto del archivo
     *
     * @return texto del archivo
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Cambia el texto del archivo
     *
     * @param texto del archivo
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Constructor de la clase
     *
     * @param nombre del archivo
     * @param texto del archivo
     * @throws EntitiesException si el archivo no tiene nombre
     */
    public Archivo(String nombre, String texto) throws EntitiesException {
        if (nombre == null || nombre.equals("")) {
            throw new EntitiesException(EntitiesException.ARCHIVO_SIN_NOMBRE);
        }
        this.nombre = nombre;
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.texto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Archivo other = (Archivo) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
