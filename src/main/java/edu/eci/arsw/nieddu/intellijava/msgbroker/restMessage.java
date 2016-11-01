/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
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
public class restMessage {
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    IntelijavaServices ins;
    
    @RequestMapping(path = "/colaboradores",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPost(@RequestBody String nombre) throws EntitiesException {
        //registrar usuario
        Usuario u = new Usuario(nombre);
        ins.addUser(u);
        System.out.println("guardado");
        //msgt.convertAndSend("/topic/newpoint", pt);
        return new ResponseEntity<>(HttpStatus.CREATED);    
    }
}
