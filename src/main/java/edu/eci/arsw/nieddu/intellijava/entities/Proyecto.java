/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.eci.arsw.nieddu.intellijava.compiler.*;
import java.net.URISyntaxException;

/**
 *
 * @author Nicolás Gomez
 */
public class Proyecto {

    private String nombre;

    private Usuario duenno;

    private ArrayList<Usuario> colaboradores;

    private ArrayList<Tarea> tareas;

    private ArrayList<Paquete> paquetes;

    /**
     * Creador de la clase
     *
     * @param nombre del proyecto
     * @param duenno del proytecto
     * @throws EntitiesException si el nombre o dueño estan vacios
     */
    public Proyecto(String nombre, Usuario duenno) throws EntitiesException {
        if (nombre == null || nombre.isEmpty()) {
            throw new EntitiesException(EntitiesException.PROYECTO_SIN_NOMBRE);
        }
        if (duenno == null) {
            throw new EntitiesException(EntitiesException.PROYECTO_SIN_DUENNO);
        }
        this.duenno = duenno;
        this.nombre = nombre;
        colaboradores = new ArrayList<>();
        tareas = new ArrayList<>();
        paquetes = new ArrayList<>();
        Paquete defaultPackage = new Paquete("default");
        Archivo defaultFile = new Archivo("Default", "");
        defaultPackage.addArchivo(defaultFile);
        paquetes.add(defaultPackage);
    }

    /**
     * Obtiene el nombre del proyecto
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene un paquete del proyecto
     *
     * @param index del paquete
     * @return paquete
     */
    public Paquete getPaquete(int index) {
        return paquetes.get(index);
    }

    /**
     * Cambia el nombre del proyecto
     *
     * @param nombre del proyecto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los colaboradores del proyecto
     *
     * @return colaboradores
     */
    public ArrayList<Usuario> getColaboradores() {
        return colaboradores;
    }

    /**
     * Cambia la lista de colaboradores
     *
     * @param colaboradores
     */
    public void setColaboradores(ArrayList<Usuario> colaboradores) {
        this.colaboradores = colaboradores;
    }

    /**
     * Añade un colaboradoral proyecto
     *
     * @param u nuevo colaborador
     * @throws EntitiesException si el colaborador a añadir es nulo
     */
    public void addColaborador(Usuario u) throws EntitiesException {
        if (u == null) {
            throw new EntitiesException(EntitiesException.PROYECTO_ADICION_USUARIOVACIO);
        }
        colaboradores.add(u);
    }

    /**
     * Elimina un colaborador
     *
     * @param u colaborador a eliminar
     * @throws EntitiesException si el usuario no existe
     */
    public void delColaborador(Usuario u) throws EntitiesException {
        if (u == null) {
            throw new EntitiesException(EntitiesException.PROYECTO_ADICION_USUARIOVACIO);
        }
        boolean find = false;
        if (u.esDuenno() && colaboradores.size() > 0) {
            u.delegarProyecto(colaboradores.get(0));
        } else {
            for (int i = 0; i < colaboradores.size() && !find; i++) {
                if (colaboradores.get(i).getNombre().equals(u.getNombre())) {
                    find = true;
                    colaboradores.remove(i);
                }
            }
        }
    }

    /**
     * Obtiene las tareas del proyecto
     *
     * @return tareas
     */
    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    /**
     * Cambia la lista de tareas
     *
     * @param tareas
     */
    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    /**
     * Añade una tarea
     *
     * @param tarea a añadir
     * @throws EntitiesException si la tarea es nula
     */
    public void addTarea(Tarea tarea) throws EntitiesException {
        if (tarea == null) {
            throw new EntitiesException(EntitiesException.PROYECTO_ADICION_TAREAVACIA);
        }
        tareas.add(tarea);
    }

    /**
     * Obtiene el dueño del proyecto
     *
     * @return dueño del proyecto
     */
    public Usuario getDuenno() {
        return duenno;
    }

    /**
     * Cambia el dueño del proyecto
     *
     * @param otherUser nuevo dueño
     * @throws EntitiesException si el usuario es nulo
     */
    public void setDuenno(Usuario otherUser) throws EntitiesException {
        if (otherUser == null) {
            throw new EntitiesException(EntitiesException.PROYECTO_SIN_DUENNO);
        }
        this.duenno = otherUser;
    }

    /**
     * Añade un paquete al proyecto
     *
     * @param paquete a añadir
     * @throws EntitiesException si el paquete es nulo o ya existe
     */
    public void addPaquete(Paquete paquete) throws EntitiesException {
        if (paquete == null || paquetes.contains(paquete)) {
            throw new EntitiesException(EntitiesException.PAQUETE_REPETIDO);
        }
        paquetes.add(paquete);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.nombre);
        hash = 11 * hash + Objects.hashCode(this.duenno);
        hash = 11 * hash + Objects.hashCode(this.colaboradores);
        hash = 11 * hash + Objects.hashCode(this.tareas);
        hash = 11 * hash + Objects.hashCode(this.paquetes);
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
        final Proyecto other = (Proyecto) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.duenno, other.duenno)) {
            return false;
        }
        return true;
    }

    /**
     * Compila el proyecto
     *
     * @return la clase compilada
     * @throws EntitiesException si hay errores de compilacion
     */
    public String compilar() {
        InMemoryJavaCompiler jc = new InMemoryJavaCompiler(paquetes.get(0).getArchivos().get(0).getNombre(), paquetes.get(0).getArchivos().get(0).getTexto());
        String aRetornar = "";
        try {
            System.out.println(paquetes.get(0).getArchivos().get(0).getNombre() + " " + paquetes.get(0).getArchivos().get(0).getTexto());
            jc.compile();
            aRetornar = jc.getResult();
            if(aRetornar.equals("")){
                aRetornar = "Compilación exitosa.";
            }
            return aRetornar;
        } catch (ClassFormatError| ClassNotFoundException | URISyntaxException ex) {
            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
            aRetornar = EntitiesException.ERROR_DE_COMPILACION+"\n"+jc.getResult();
        }
        return aRetornar;
    }

    public void modificarArchivo(int paquete, int archivo, String texto) throws EntitiesException {
        if (paquete > paquetes.size()) {
            throw new EntitiesException(EntitiesException.PAQUETE_INEXISTENTE);
        }
        if (archivo > paquetes.get(paquete).getArchivos().size()) {
            throw new EntitiesException(EntitiesException.ARCHIVO_INEXISTENTE);
        }
        paquetes.get(paquete).escribirEnArchivo(archivo, texto);
    }
}
