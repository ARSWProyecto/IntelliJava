/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import edu.eci.arsw.nieddu.intellijava.entities.Usuario;
import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import org.junit.Assert;
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
            Usuario u = new Usuario(null);
            Assert.fail("Dejo crear el usuario");
        }catch(EntitiesException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.USUARIO_SIN_NOMBRE, te.getMessage());
        }
    }
    
    //Clase de equivalencia 2, Un usuario que no es dueño de un proyecto no debería poder delegarlo
    @Test
    public void noDeberiaPoderDelegarProyectoSinSerDuenno() {
        try {
            
            Usuario u = new Usuario("Kolmant");
            Proyecto p = new Proyecto("SuperProyecto",u);
            u.delegarProyecto(new Usuario("poshito"));
            Assert.fail("Dejo delegar el proyecto");
        }catch(EntitiesException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.USUARIO_SIN_DERECHOS, te.getMessage());
        }
    }
    
    //Clase de equivalencia 3, Un usuario que es dueño de un proyecto debería poder delegarlo
    @Test
    public void DeberiaPoderDelegarProyectoSiendoDuenno() {
        try {
            Usuario u = new Usuario("Kolmant");
            Proyecto p = new Proyecto("SuperProyecto",u);
            p.setDuenno(u);
            u.delegarProyecto(new Usuario("poshito"));
            Assert.fail("No dejo delegar el proyecto");
        }catch(EntitiesException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.USUARIO_SIN_DERECHOS, te.getMessage());
        }
    }
}
