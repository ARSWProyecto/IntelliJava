/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import edu.eci.arsw.nieddu.intellijava.entities.Usuario;
import edu.eci.arsw.nieddu.intellijava.entities.UsuarioException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Zawsx
 */
public class UsuarioTest {
    
    //Clase de equivalencia 1, No debería poderse crear un usuario sin nombre
    @Test
    public void deberiaTenerNombre() {
        try {
            Usuario u = new Usuario(null, null);
            Assert.fail("Dejo crear el usuario");
        }catch(UsuarioException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",UsuarioException.USUARIO_SIN_NOMBRE, te.getMessage());
        }
    }
    
    //Clase de equivalencia 2, Un usuario que no es dueño de un proyecto no debería poder delegarlo
    @Test
    public void noDeberiaPoderDelegarProyectoSinSerDuenno() {
        try {
            Proyecto p = new Proyecto();
            Usuario u = new Usuario("Kolmant", p);
            u.delegarProyecto(new Usuario("poshito",p));
            Assert.fail("Dejo delegar el proyecto");
        }catch(UsuarioException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",UsuarioException.USUARIO_SIN_DERECHOS, te.getMessage());
        }
    }
    
    //Clase de equivalencia 3, Un usuario que es dueño de un proyecto debería poder delegarlo
    @Test
    public void DeberiaPoderDelegarProyectoSiendoDuenno() {
        try {
            Proyecto p = new Proyecto();
            Usuario u = new Usuario("Kolmant", p);
            p.setDuenno(u);
            u.delegarProyecto(new Usuario("poshito",p));
            Assert.fail("Dejo delegar el proyecto");
        }catch(UsuarioException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",UsuarioException.USUARIO_SIN_DERECHOS, te.getMessage());
        }
    }
}
