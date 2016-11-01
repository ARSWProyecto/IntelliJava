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
public class ArchivosTest {

    //Clase equivalencia 1, un archivo debe tener un nombre
    @Test
    public void deberiaTenerNombre(){
        try{
            Archivo arc= new Archivo(null,"");
            Assert.fail("No lanzo excepcion");
        }catch(EntitiesException ex){
            Assert.assertEquals("No coinciden las excepciones", EntitiesException.ARCHIVO_SIN_NOMBRE, ex.getMessage());
        }
    }
}
