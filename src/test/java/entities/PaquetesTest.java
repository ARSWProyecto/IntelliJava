/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import edu.eci.arsw.nieddu.intellijava.entities.Archivo;
import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Paquete;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Zawsx
 */
public class PaquetesTest {
    //Clase equivalencia 1, no pueden existir archivos con el mismo nombre en el mismo paquete
    @Test
    public void noHayArchivosRepetidosPaquete(){
        try{
            Paquete prueba = new Paquete("paqueteDePrueba");
            Archivo arc1 = new Archivo("hola.java","");
            Archivo arc2 = new Archivo("hola.java","");
            prueba.addArchivo(arc1);
            prueba.addArchivo(arc2);
            Assert.fail("No lanzo excepcion");
        }catch(EntitiesException ex){
            Assert.assertEquals("No coinciden las excepciones", EntitiesException.ARCHIVO_REPETIDO, ex.getMessage());
        }
    }

    //Clase equivalencia 2, deja añadir un archivo normalmente
    @Test
    public void addArchivo(){
        try{
            Paquete prueba = new Paquete("paqueteDePrueba");
            Archivo arc1 = new Archivo("hola.java","");
            prueba.addArchivo(arc1);
            Assert.assertTrue(prueba.contiene(arc1));
        }catch(EntitiesException ex){
            Assert.fail("lanzo excepcion");
        }        
    }
    
    //Clase equivalencia 3, no deja borrar un archivo inexistente
    @Test
    public void noBorraArchivoInexistente(){
        try{
            Paquete prueba = new Paquete("paqueteDePrueba");
            prueba.eliminarArchivo("hola.java");
            Assert.fail("No lanzo excepcion");
        }catch(EntitiesException ex){
            Assert.assertEquals("No coinciden las excepciones", EntitiesException.ARCHIVO_INEXISTENTE, ex.getMessage());
        }
    }
    
    //Clase equivalencia 4, borra bien un archivo
    @Test
    public void borraBienUnArchivo(){
        try{
            Paquete prueba = new Paquete("paqueteDePrueba");
            Archivo arc1 = new Archivo("hola.java","");
            prueba.addArchivo(arc1);
            prueba.eliminarArchivo("hola.java");            
            Assert.assertFalse("No se elimino el archivo", prueba.contiene(arc1));
        }catch(EntitiesException ex){
            Assert.fail("lanzo excepcion"+ex.getMessage());            
        }        
    }
    
    //Clase equivalencia 5, no se crea un paqute sin nombre
    @Test
    public void noCreaPaqueteSinNombre(){
        try{
            Paquete prueba = new Paquete(null);
            Assert.fail("No lanzo excepcion");
        }catch(EntitiesException ex){
            Assert.assertEquals("Las excepciones no coinciden", EntitiesException.PAQUETE_SIN_NOMBRE,ex.getMessage());
        }         
    }
}
