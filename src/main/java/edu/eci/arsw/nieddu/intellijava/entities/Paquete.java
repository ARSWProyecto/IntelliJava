/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author David Useche
 */
public class Paquete {

    private String nombre;
    private LinkedList<Archivo> archivos;
    private LinkedList<Paquete> paquetes;

    /**
     * Obtiene el nombre del paquete
     *
     * @return nombre del paquete
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del paquete
     *
     * @param nombre del paquete
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los archivos del paquete
     *
     * @return archivos del paquete
     */
    public LinkedList<Archivo> getArchivos() {
        return archivos;
    }

    /**
     * Cambia la lista de archivos del paquete
     *
     * @param archivos del paquete
     */
    public void setArchivos(LinkedList<Archivo> archivos) {
        this.archivos = archivos;
    }

    /**
     * Obtiene los paquetes contenidos
     *
     * @return paquetes
     */
    public LinkedList<Paquete> getPaquetes() {
        return paquetes;
    }

    /**
     * Cambia la lista de paquetes
     *
     * @param paquetes
     */
    public void setPaquetes(LinkedList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * Constructor de la clase
     *
     * @param nombre del paquete
     * @throws EntitiesException si el nombre esta vacio
     */
    public Paquete(String nombre) throws EntitiesException {
        if (nombre == null || nombre.equals("")) {
            throw new EntitiesException(EntitiesException.PAQUETE_SIN_NOMBRE);
        }
        this.nombre = nombre;
        archivos = new LinkedList<>();
        paquetes = new LinkedList<>();

    }

    /**
     * Añade un archivo al paquete
     *
     * @param arc a añadir
     * @throws EntitiesException si el archivo ya existe en ese paquete
     */
    public void addArchivo(Archivo arc) throws EntitiesException {
        if (contiene(arc)) {
            throw new EntitiesException(EntitiesException.ARCHIVO_REPETIDO);
        }
        archivos.add(arc);
    }

    /**
     * Verifica si un archivo existe en el paquete
     *
     * @param arc a verificar
     * @return true si el archivo existe, false de otro modo
     */
    public boolean contiene(Archivo arc) {
        return archivos.contains(arc);
    }

    /**
     * Verifica si existe un paquete en el paquete
     *
     * @param paquete a verificar
     * @return true si el paquete existe, false de otro modo
     */
    public boolean contiene(Paquete paquete) {
        return paquetes.contains(paquete);
    }

    /**
     * Elimina un archivo del paquete
     *
     * @param nombreArc a verificar
     * @throws EntitiesException si el archivo no existe
     */
    public void eliminarArchivo(String nombreArc) throws EntitiesException {
        if (!contiene(new Archivo(nombreArc, null))) {
            throw new EntitiesException(EntitiesException.ARCHIVO_INEXISTENTE);
        }
        archivos.remove(new Archivo(nombreArc, null));
    }

    /**
     * Obtiene un archivo
     *
     * @param index del archivo a obtener
     * @return El archivo solicitado
     */
    public synchronized Archivo obtenerArchivo(int index) {
        return archivos.get(index);
    }
    
    /**
     * Escribe en un archivo
     *
     * @param index del archivo a escribir
     * @param text el texto que se pondrá
     */
    public synchronized void escribirEnArchivo(int index, String text) {
        System.out.println("Vamos a escribir: "+text);
        archivos.get(index).setTexto(text);
    }

    /**
     * Añade un paquete al paquete
     *
     * @param paquete a añadir
     * @throws EntitiesException si el paquete ya existe
     */
    public void addPaquete(Paquete paquete) throws EntitiesException {
        if (contiene(paquete)) {
            throw new EntitiesException(EntitiesException.PAQUETE_REPETIDO);
        }
        paquetes.add(paquete);
    }

    /**
     * Elimina un paquete
     *
     * @param nombrePaquete a eliminar
     * @throws EntitiesException si el paquete no existe
     */
    public void eliminarPaquete(String nombrePaquete) throws EntitiesException {
        if (!contiene(new Paquete(nombrePaquete))) {
            throw new EntitiesException(EntitiesException.PAQUETE_INEXISTENTE);
        }
        paquetes.remove(new Paquete(nombrePaquete));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.archivos);
        hash = 23 * hash + Objects.hashCode(this.paquetes);
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
        final Paquete other = (Paquete) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
