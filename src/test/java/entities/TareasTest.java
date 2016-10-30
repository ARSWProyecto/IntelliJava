/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import edu.eci.arsw.nieddu.intellijava.entities.Tarea;
import edu.eci.arsw.nieddu.intellijava.entities.TareaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author 2107803
 */
public class TareasTest {

    //Clase de equivalencia 1, No deberia completarse una tarea si ya esta completada
    @Test
    public void noDeberiaDejarCompletarDosVeces() {
        try {
            Tarea tarea = new Tarea("Tarea de prueba");
            tarea.completar();
            tarea.completar();
            Assert.fail("Dejo completar dos veces");
        }catch(TareaException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",TareaException.TAREA_COMPLETA, te.getMessage());
        }
    }
    //Clase de equivalencia 2, Deberia poderse completar una tarea
    @Test
    public void deberiaCompletarseUnaTarea(){
        try {
            Tarea tarea = new Tarea("Tarea de prueba");
            tarea.completar();
            Assert.assertTrue("No se completo la tarea", tarea.isCompleta());
        }catch(TareaException te){
            Assert.fail("Lanzo excepcion al completar la tarea");
        }
    }
    //Clase de equivalencia 3, Una tarea debe tener descripcion
    @Test
    public void deberiaTenerDescripcion(){
        try {
            Tarea tarea = new Tarea(null);
            Assert.fail("Dejo crear tarea sin descripcion");
        }catch(TareaException te){
            Assert.assertEquals("La excepcion lanzada fue distinta",TareaException.TAREA_SIN_DESCRIPCION, te.getMessage());
        }
    }
}
