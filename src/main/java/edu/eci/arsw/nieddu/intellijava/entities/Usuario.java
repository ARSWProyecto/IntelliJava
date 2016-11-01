/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

/**
 *
 * @author 2100038
 */ 
public class Usuario {

	private String nombre;

	private Proyecto proyectoActual;
        
        public Usuario(String nombre) throws EntitiesException{
            if(nombre==null || nombre.isEmpty())throw new EntitiesException(EntitiesException.USUARIO_SIN_NOMBRE);
            this.nombre = nombre;
        }
        
        public boolean esDuenno(){
            boolean resp = proyectoActual==null?false:proyectoActual.getDuenno().nombre.equals(nombre);
            return resp;
        }
        
        public void setProyectoActual(Proyecto proj){
            proyectoActual=proj;
        }
        
        public void delegarProyecto(Usuario otherUser) throws EntitiesException{ 
            if(!esDuenno()){
                throw new EntitiesException(EntitiesException.USUARIO_SIN_DERECHOS);
            }
            proyectoActual.setDuenno(otherUser);
        }

    public String getNombre() {
        return nombre;
    }
}