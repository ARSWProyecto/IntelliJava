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
    public ResponseEntity<?> manejadorPost(@RequestBody String nombre){
        //registrar usuario
        Usuario u;
        try {
            u = new Usuario(nombre);
            boolean created = ins.addUser(u);
            if(created){
                return new ResponseEntity<>(HttpStatus.ACCEPTED);  
            }else{
                return new ResponseEntity<>("No se ha podido crear el usuario",HttpStatus.NOT_FOUND);
            }
        } catch (EntitiesException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/colaborador",method = RequestMethod.POST)
    public ResponseEntity<?> addColaborador(@PathVariable String nombreP, @RequestBody String usuario) {
        //registrar usuario
        try{
            Proyecto p=ins.existeProyecto(nombreP);
            Usuario u = ins.existeUsuario(usuario);
            if(p!=null && u!=null){
                p.addColaborador(u);
                u.setProyectoActual(p);
                return new ResponseEntity<>(HttpStatus.CREATED);  
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (EntitiesException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/delcolaborador",method = RequestMethod.POST)
    public ResponseEntity<?> delColaborador(@PathVariable String nombreP, @RequestBody String usuario) throws EntitiesException {
        //registrar usuario
        Proyecto p=ins.existeProyecto(nombreP);
        Usuario u=ins.existeUsuario(usuario);
        boolean realizado=false;
        if(u.esDuenno() && p.getColaboradores().size()<2){
            ins.delUsuario(u);
            ins.delProyecto(p);
            realizado=true;
        }else{
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
    public ResponseEntity<?> addProyecto(@PathVariable String nombreP,@RequestBody String usuario) {
        //registrar proyecto con su Duenno
        try{
            Usuario u = ins.existeUsuario(usuario);
            Proyecto p = new Proyecto(nombreP, u);
            u.setProyectoActual(p);
            boolean created=ins.addProject(p);
            if(created){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (EntitiesException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path="/colaborador", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorUsuario(){
        return new ResponseEntity<>(ins.usuarios(), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/colaborador", method = RequestMethod.GET)
    public ResponseEntity<?> colaboradoresProyecto(@PathVariable String nombreP){
        List<Usuario> p= ins.colaboradores(nombreP);
        if(p==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity<>(p,HttpStatus.ACCEPTED);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/duenno", method = RequestMethod.GET)
    public ResponseEntity<?> duennoProyecto(@PathVariable String nombreP){
        Usuario p = ins.getDuennoProyecto(nombreP);
        if(p==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity<>(p,HttpStatus.ACCEPTED);
        }        
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/duenno", method = RequestMethod.PUT)
    public ResponseEntity<?> cambiarDuennoProyecto(@PathVariable String nombreP, @RequestBody Usuario usuario){
        if(ins.cambiarDuennoProyecto(nombreP, usuario)){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
