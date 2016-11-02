/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

import java.util.ArrayList;

/**
 *
 * @author Nieddu
 */
public class Proyecto {
    
    private String nombre;

    private Usuario duenno;

    private ArrayList<Usuario> colaboradores;

    private ArrayList<Tarea> tareas;
    
    
    public Proyecto(String nombre, Usuario duenno) throws EntitiesException{
        if(nombre==null || nombre.isEmpty()) throw new EntitiesException(EntitiesException.PROYECTO_SIN_NOMBRE);
        if(duenno==null) throw new EntitiesException(EntitiesException.PROYECTO_SIN_DUENNO);
        this.duenno=duenno;
        this.nombre=nombre;
        colaboradores = new ArrayList<>();
        tareas = new ArrayList<>();
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
    
    public void addColaborador(Usuario u) throws EntitiesException{
        if(u==null)throw new EntitiesException(EntitiesException.PROYECTO_ADICION_USUARIOVACIO);
        colaboradores.add(u);
    }
    
    public void delColaborador(Usuario u) throws EntitiesException{
        if(u==null)throw new EntitiesException(EntitiesException.PROYECTO_ADICION_USUARIOVACIO);
        boolean find=false;
        if(u.esDuenno() && colaboradores.size()>0){
            u.delegarProyecto(colaboradores.get(0));
        }else{
            for (int i = 0; i < colaboradores.size() && !find; i++) {
                if(colaboradores.get(i).getNombre().equals(u.getNombre())){
                    find=true;
                    colaboradores.remove(i);
                }
            }
        }
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    public void addTarea(Tarea tarea) throws EntitiesException{
        if(tarea==null)throw new EntitiesException(EntitiesException.PROYECTO_ADICION_TAREAVACIA);
        tareas.add(tarea);
    }
    
    public Usuario getDuenno() {
	return duenno;
    }

    public void setDuenno(Usuario otherUser) throws EntitiesException {
        if(otherUser==null)throw new EntitiesException(EntitiesException.PROYECTO_SIN_DUENNO);
        this.duenno=otherUser;
    }
}
