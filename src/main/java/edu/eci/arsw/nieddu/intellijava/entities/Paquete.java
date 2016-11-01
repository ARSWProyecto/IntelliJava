/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

import java.util.LinkedList;

/**
 *
 * @author Zawsx
 */
public class Paquete {

    private String nombre;
    private LinkedList<Archivo> archivos;
    private LinkedList<Paquete> paquetes;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(LinkedList<Archivo> archivos) {
        this.archivos = archivos;
    }

    public LinkedList<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(LinkedList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    public Paquete(String nombre) throws EntitiesException {
        if (nombre == null || nombre.equals("")) {
            throw new EntitiesException(EntitiesException.PAQUETE_SIN_NOMBRE);
        }
        this.nombre = nombre;
        archivos = new LinkedList<>();
        paquetes = new LinkedList<>();

    }

    public void addArchivo(Archivo arc) throws EntitiesException {
        if(contiene(arc))throw new EntitiesException(EntitiesException.ARCHIVO_REPETIDO);
        archivos.add(arc);
    }

    public boolean contiene(Archivo arc) {
        return archivos.contains(arc);
    }

    public void eliminarArchivo(String nombreArc) throws EntitiesException{
        if(!contiene(new Archivo(nombreArc, null)))throw new EntitiesException(EntitiesException.ARCHIVO_INEXISTENTE);
        archivos.remove(new Archivo(nombreArc,null));
    }

}
