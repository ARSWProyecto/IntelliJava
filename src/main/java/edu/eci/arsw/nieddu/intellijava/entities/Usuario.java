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
        
        public Usuario(String nombre, Proyecto proyectoActual) throws EntitiesException{
            if(nombre==null)throw new EntitiesException(EntitiesException.USUARIO_SIN_NOMBRE);
            this.nombre = nombre;
            this.proyectoActual=proyectoActual;
        }
        
        public boolean esDuenno(){
            return proyectoActual.getDuenno().nombre.equals(nombre);
        }
        
        public void delegarProyecto(Usuario otherUser) throws EntitiesException{ 
            if(!esDuenno()){
                throw new EntitiesException(EntitiesException.USUARIO_SIN_DERECHOS);
            }
            proyectoActual.setDuenno(otherUser);
        }
}