/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.entities;

/**
 *
 * @author 2107803
 */
public class UsuarioException extends Exception {

    public final static String USUARIO_SIN_NOMBRE = "El usuario tiene que tener un nombre";
    public final static String USUARIO_SIN_DERECHOS = "El usuario no tiene permisos para delegar el proyecto actual";

    public UsuarioException(String message) {
        super(message);
    }

    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioException(Throwable cause) {
        super(cause);
    }

    public UsuarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
