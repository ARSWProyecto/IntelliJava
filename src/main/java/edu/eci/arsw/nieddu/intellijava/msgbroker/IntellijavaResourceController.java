/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import edu.eci.arsw.nieddu.intellijava.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CERONSANCHEZEDWINALE
 */
@RestController
@RequestMapping(value = "/intelijava")
public class IntellijavaResourceController {
    
    @Autowired
    IntelijavaServices ins;
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    @RequestMapping(path = "/colaborador",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPost(@RequestBody String nombre) throws EntitiesException {
        //registrar usuario
        Usuario u = new Usuario(nombre);
        boolean created = ins.addUser(u);
        if(created){
            //System.out.println("Creado");
            return new ResponseEntity<>(HttpStatus.CREATED);  
        }else{
            //System.out.println("No creado");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/proyecto",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPost(@RequestBody String nombre, @RequestBody Usuario usuario) throws EntitiesException {
        //registrar usuario
        Proyecto p = new Proyecto(nombre, usuario);
        ins.addProject(p);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
}
