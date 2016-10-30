/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.NiEdDu.entities;

/**
 *
 * @author 2107803
 */
public class Tarea {

    private String descripcion;
    private boolean completa;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompleta() {
        return completa;
    }

    public void setCompleta(boolean completa) {
        this.completa = completa;
    }
       
    /**
     * Constructor
     * @param descripcion de la tarea
     * @throws TareaException si la descripcion es nula o vacia
     */
    public Tarea(String descripcion) throws TareaException{
        if(descripcion == null || descripcion.equals(""))throw new TareaException(TareaException.TAREA_SIN_DESCRIPCION);
        this.descripcion=descripcion;
        this.completa=false;
    }

    /**
     * Completa una tarea
     * @throws TareaException si la tarea ya fue completada
     */
    public void completar() throws TareaException{
        if(isCompleta())throw new TareaException(TareaException.TAREA_COMPLETA);
        setCompleta(true);
    }

    
}
