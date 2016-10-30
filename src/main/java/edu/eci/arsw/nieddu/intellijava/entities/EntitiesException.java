/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

/**
 *
 * @author CERONSANCHEZEDWINALE
 */
public class EntitiesException extends Exception{
    
    public final static String USUARIO_SIN_NOMBRE = "El usuario tiene que tener un nombre";
    public final static String USUARIO_SIN_DERECHOS = "El usuario no tiene permisos para delegar el proyecto actual";
    public final static String TAREA_COMPLETA = "La tarea ya fue completada";
    public final static String TAREA_SIN_DESCRIPCION = "Toda tarea debe tener una descripcion";
    public final static String PROYECTO_SIN_NOMBRE = "El proyecto tiene que tener un nombre";
    
    
    
    public EntitiesException(String message) {
        super(message);
    }

    public EntitiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntitiesException(Throwable cause) {
        super(cause);
    }

    public EntitiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
