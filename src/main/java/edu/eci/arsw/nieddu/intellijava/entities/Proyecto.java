/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

import java.util.ArrayList;

/**
 *
 * @author 2107803
 */
public class Proyecto {
    
    private String nombre;

    private Usuario duenno;

    private ArrayList<Usuario> colaboradores;

    private ArrayList<Tarea> tareas;
    
    
    public Proyecto(String nombre, Usuario duenno) throws EntitiesException{
        if(nombre!=null) throw new EntitiesException(EntitiesException.PROYECTO_SIN_NOMBRE);
        this.duenno=duenno;
        this.nombre=nombre;
            
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Usuario> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(ArrayList<Usuario> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    
    
    public Usuario getDuenno() {
	return duenno;
    }

    public void setDuenno(Usuario otherUser) {
        this.duenno=otherUser;
    }
}
