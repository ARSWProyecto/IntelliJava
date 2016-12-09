/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
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
    
    //clase de equivalencia 2, no deberia poderse crear un proyecto sin nombre.
    @Test
    public void deberiaTenerUnNombre(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto(null, u);
            Assert.fail("Permitio crear un proyecto sin nombre");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_NOMBRE, ex.getMessage());
        }
    }
    
    //clase de equivalencia 3, no deberia poderse crear un proyecto si el nombre es vacio.
    @Test
    public void deberiaTenerUnNombreDiferenteDeVacio(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("", u);
            Assert.fail("Permitio crear un proyecto con nombre vacio");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_NOMBRE, ex.getMessage());
        }
    }
    
    //clase de equivalencia 4, no deberia poderse agregar colaboradores nulos.
    @Test
    public void colaboradoresRegistrados(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("El proyecto", u);
            p.addColaborador(null);
            Assert.fail("Permitio adicionar un colaborador vacio");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_ADICION_USUARIOVACIO, ex.getMessage());
        }
    }
    
    //clase de equivalencia 5, no deberia poderse cambiar de duenno si es vacio.
    @Test
    public void cambioDeDuenno(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("El proyecto", u);
            p.setDuenno(null);
            Assert.fail("Permitio adicionar un colaborador vacio");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_SIN_DUENNO, ex.getMessage());
        }
    }
    
    //clase de equivalencia 6, no deberia poderse agregar una tarea si es vacia.
    @Test
    public void adicionarTarea(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("El proyecto", u);
            p.addTarea(null);
            Assert.fail("Permitio adicionar una tarea vacia");
        } catch (EntitiesException ex) {
            Assert.assertEquals("La excepcion lanzada fue distinta",EntitiesException.PROYECTO_ADICION_TAREAVACIA, ex.getMessage());
        }
    }
    
    //clase de equivalencia 7, deberia compilar correctamente
    @Test
    public void deberiaCompilar(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("El proyecto", u);
            String aEscribir ="public class Default{}";
            p.modificarArchivo(0, 0, aEscribir);
            String holi = p.compilar();
        }catch(EntitiesException ex){
            Assert.fail("Lanzo exception "+ex.toString());
        }
    }
    
    //Clase de equivalencia 8, no deberia compilar
    @Test
    public void noDeberiaCompilar(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("El proyecto", u);
            String aEscribir ="public clas Default{}";
            p.modificarArchivo(0, 0, aEscribir);
            String holi = p.compilar();
            System.out.println(holi);
            if(holi.isEmpty()){
                Assert.fail("Se esperaba error");
            }
        }catch(EntitiesException ex){
            //System.out.println(InMemoryJavaCompiler.RESULTADO);
            Assert.fail("No se esperaba excepcion");
        }
    }
    
    //clase de equivalencia 9, deberia compilar un archivo en blanco
    @Test
    public void deberiaCompilarArchivoEnBlanco(){
        try{
            String u = "Poshito";
            Proyecto p = new Proyecto("El proyecto", u);
            String aEscribir ="";
            p.modificarArchivo(0, 0, aEscribir);
            String holi = p.compilar();
        }catch(EntitiesException ex){
            Assert.fail("Lanzo exception "+ex.toString());
        }
    }
}

