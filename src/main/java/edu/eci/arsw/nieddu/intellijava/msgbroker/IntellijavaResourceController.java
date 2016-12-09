/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import java.util.List;
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
    Services ins;
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    @RequestMapping(path = "/colaborador",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPost(@RequestBody String nombre){
        //registrar usuario
        String u;
        u = nombre;
        boolean created = ins.addUser(u);
        if(created){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);  
        }else{
            return new ResponseEntity<>("No se ha podido crear el usuario",HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/colaborador/{nombreU}/compilado", method = RequestMethod.GET)
    public ResponseEntity<?> compiladoDelProyecto(@PathVariable String nombreP, @PathVariable String nombreU) throws EntitiesException{
        Proyecto p = ins.existeProyecto(nombreP);
        String u = ins.existeUsuario(nombreU);
        if(p==null || u==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            String resp = ins.compilarProyecto(p, u);
            return new ResponseEntity<>(resp,HttpStatus.ACCEPTED);
        }
    }
    /**
     * agregar un colaborador
     * @param nombreP
     * @param usuario
     * @return 
     */
    @RequestMapping(path = "/proyecto/{nombreP}/colaborador",method = RequestMethod.POST)
    public ResponseEntity<?> addColaborador(@PathVariable String nombreP, @RequestBody String usuario) {
        //registrar usuario
        try{
            Proyecto p=ins.existeProyecto(nombreP);
            String u = ins.existeUsuario(usuario);
            if(p!=null && u!=null){
                p.addColaborador(u);
                ins.updateProject(p);
                //u.setProyectoActual(p);
                return new ResponseEntity<>(HttpStatus.CREATED);  
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (EntitiesException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/{colaborador}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delColaborador(@PathVariable String nombreP, @PathVariable String colaborador) {
        //registrar usuario
        try{
            Proyecto p=ins.existeProyecto(nombreP);
            String u=ins.existeUsuario(colaborador);
            boolean realizado=false;
            if(u.equals(p.getDuenno()) && p.getColaboradores().size()<1){
                ins.delUsuario(u);
                ins.delProyecto(p);
                realizado=true;
            }else{
                p.delColaborador(u);
                ins.delUsuario(u);
                ins.updateProject(p);
                realizado=true;
            }if(realizado){
                return new ResponseEntity<>(HttpStatus.CREATED);  
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (EntitiesException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    } 
    
    @RequestMapping(path = "/proyecto/{nombreP}",method = RequestMethod.POST)
    public ResponseEntity<?> addProyecto(@PathVariable String nombreP,@RequestBody String usuario) {
        //registrar proyecto con su Duenno
        try{
            String u = ins.existeUsuario(usuario);
            Proyecto p = new Proyecto(nombreP, u);
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
    
    /*
    @RequestMapping(path="/colaborador", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorUsuario(){
        return new ResponseEntity<>(ins.usuarios(), HttpStatus.ACCEPTED);
    }*/
    
    @RequestMapping(path = "/proyecto/{nombreP}/colaborador", method = RequestMethod.GET)
    public ResponseEntity<?> colaboradoresProyecto(@PathVariable String nombreP){
        List<String> p= ins.colaboradores(nombreP);
        if(p==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity<>(p,HttpStatus.ACCEPTED);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/duenno", method = RequestMethod.GET)
    public ResponseEntity<?> duennoProyecto(@PathVariable String nombreP){
        String duenno = ins.getDuennoProyecto(nombreP);
        if(duenno==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity<>(duenno,HttpStatus.ACCEPTED);
        }        
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/paquete/{idPaquete}/archivo/{idArchivo}", method = RequestMethod.GET)
    public ResponseEntity<?> textoProyecto(@PathVariable String nombreP, @PathVariable Integer idPaquete, @PathVariable Integer idArchivo){
        Proyecto p = ins.existeProyecto(nombreP);
        String resp = p.getPaquete(idPaquete).obtenerArchivo(0).getTexto();
        if(resp==null)return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity<>(resp,HttpStatus.ACCEPTED);
        }
    }
    
    @RequestMapping(path = "/proyecto/{nombreP}/duenno", method = RequestMethod.PUT)
    public ResponseEntity<?> cambiarDuennoProyecto(@PathVariable String nombreP, @RequestBody String usuario){
        if(ins.cambiarDuennoProyecto(nombreP, usuario)){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
