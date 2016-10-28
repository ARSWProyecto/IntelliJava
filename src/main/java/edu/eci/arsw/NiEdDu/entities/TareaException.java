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
public class TareaException extends Exception {

    public final static String TAREA_COMPLETA = "La tarea ya fue completada";
    public final static String TAREA_SIN_DESCRIPCION = "Toda tarea debe tener una descripcion";

    public TareaException(String message) {
        super(message);
    }

    public TareaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TareaException(Throwable cause) {
        super(cause);
    }

    public TareaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
