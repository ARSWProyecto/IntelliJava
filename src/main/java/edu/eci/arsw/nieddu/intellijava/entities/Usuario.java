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

	private boolean esDuenno;
        
        public Usuario(String nombre, Proyecto proyectoActual, boolean esDuenno){
            this.nombre = nombre;
            this.proyectoActual=proyectoActual;
            this.esDuenno=esDuenno;
        }

}