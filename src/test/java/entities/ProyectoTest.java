/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import edu.eci.arsw.nieddu.intellijava.entities.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
/**
 *
 * @author Nieddu
 */
public class ProyectoTest {
    
    //clase de equivalencia 1, no deberia poderse crear un proyecto sin un dueño
    @Test
    public void deberiaTenerUnDuenno(){
        try{
            Proyecto p = new Proyecto("El proyecto", null);
            Assert.fail("Permitio crear un proyecto sin dueño");
        }catch(EntitiesException ex){
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_DUENNO, ex.getMessage());
        }
    }
    
    //clase de equivalencia 1, no deberia poderse crear un proyecto sin nombre.
    @Test
    public void deberiaTenerUnNombre(){
        try{
            Usuario u = new Usuario("Poshito");
            Proyecto p = new Proyecto(null, u);
            Assert.fail("Permitio crear un proyecto sin nombre");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_NOMBRE, ex.getMessage());
        }
    }
    
    //clase de equivalencia 1, no deberia poderse crear un proyecto si el nombre es vacio.
    @Test
    public void deberiaTenerUnNombreDiferenteDeVacio(){
        try{
            Usuario u = new Usuario("Poshito");
            Proyecto p = new Proyecto("", u);
            Assert.fail("Permitio crear un proyecto con nombre vacio");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_NOMBRE, ex.getMessage());
        }
    }
    
    //clase de equivalencia 2, no deberia poderse agregar colaboradores nulos.
    public void colaboradoresRegistrados(){
        try{
            Usuario u = new Usuario("Poshito");
            Proyecto p = new Proyecto("El proyecto", u);
            p.addColaborador(null);
            Assert.fail("Permitio adicionar un colaborador vacio");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_ADICION_USUARIOVACIO, ex.getMessage());
        }
    }
    
    //clase de equivalencia 2, no deberia poderse cambiar de duenno si es vacio.
    public void cambioDeDuenno(){
        try{
            Usuario u = new Usuario("Poshito");
            Proyecto p = new Proyecto("El proyecto", u);
            p.setDuenno(null);
            Assert.fail("Permitio adicionar un colaborador vacio");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_DUENNO, ex.getMessage());
        }
    }
    
    //clase de equivalencia 2, no deberia poderse agregar una tarea si es vacia.
    public void adicionarTarea(){
        try{
            Usuario u = new Usuario("Poshito");
            Proyecto p = new Proyecto("El proyecto", u);
            p.addTarea(null);
            Assert.fail("Permitio adicionar una tarea vacia");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_ADICION_TAREAVACIA, ex.getMessage());
        }
    }
}

