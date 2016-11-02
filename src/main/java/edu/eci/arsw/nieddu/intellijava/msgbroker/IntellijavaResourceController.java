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
import org.springframework.web.bind.annotation.PathVariable;
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
            return new ResponseEntity<>(HttpStatus.CREATED);  
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/colaborador",method = RequestMethod.POST)
    public ResponseEntity<?> addColaborador(@PathVariable String nombreP, @RequestBody String usuario) throws EntitiesException {
        //registrar usuario
        Proyecto p=ins.existeProyecto(nombreP);
        Usuario u = ins.existeUsuario(usuario);
        if(p!=null && u!=null){
            p.addColaborador(u);
            return new ResponseEntity<>(HttpStatus.CREATED);  
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/delcolaborador",method = RequestMethod.POST)
    public ResponseEntity<?> delColaborador(@PathVariable String nombreP, @RequestBody String usuario) throws EntitiesException {
        //registrar usuario
        Proyecto p=ins.existeProyecto(nombreP);
        Usuario u=ins.existeUsuario(usuario);
        boolean realizado=false;
        System.out.println("el usuario es "+u.getNombre()+" los colaboradores son "+p.getColaboradores().size()+" es duenno "+u.esDuenno());
        if(p.getDuenno().getNombre().equals(u.getNombre()) && p.getColaboradores().size()<1){
            System.out.println("Borrando proyecto");
            ins.delProyecto(p);
            realizado=true;
        }else{
            System.out.println("Borrando usuario");
            p.delColaborador(u);
            ins.delUsuario(u);
            realizado=true;
        }if(realizado){
            return new ResponseEntity<>(HttpStatus.CREATED);  
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}",method = RequestMethod.POST)
    public ResponseEntity<?> addProyecto(@PathVariable String nombreP,@RequestBody String usuario) throws EntitiesException {
        //registrar proyecto con su Duenno
        Usuario u = ins.existeUsuario(usuario);
        Proyecto p = new Proyecto(nombreP, u);
        boolean created=ins.addProject(p);
        if(created){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
