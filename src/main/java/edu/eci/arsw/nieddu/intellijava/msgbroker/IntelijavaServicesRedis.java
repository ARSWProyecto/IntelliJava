 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Nieddu
 */

@Service
public class IntelijavaServicesRedis {
    
    public static CopyOnWriteArrayList<String> usersArray;
    public static CopyOnWriteArrayList<Proyecto> projectsArray;
    
    public IntelijavaServicesRedis(){
        usersArray= new CopyOnWriteArrayList();
        projectsArray= new CopyOnWriteArrayList<>();
    }
    
    public boolean addUser(String u){
        boolean resp;
        if(resp = existeUsuario(u)==null){
            usersArray.add(u);
        }
        return resp;
    }

    public String existeUsuario(String nombre) {
        String resp = null;
        Jedis jedis = JedisUtil.getPool().getResource();
        Map<String, String> usuario = jedis.hgetAll("nombre:"+nombre);
        if(usuario!=null){            
            resp = nombre;
        }
        /*
        for(int i = 0 ; i < usersArray.size() && resp==null; i++){
            if(usersArray.get(i).getNombre().equals(u)){
                resp = usersArray.get(i);
            }
        }*/
        return resp;
    }

    public boolean addProject(Proyecto p){
        boolean resp;
        if(resp = existeProyecto(p.getNombre())==null){
            projectsArray.add(p);
        }
        return resp;
    }

    public Proyecto existeProyecto(String p) {
        Proyecto resp = null;
        for(int i = 0 ; i < projectsArray.size() && resp==null; i++){
            if(projectsArray.get(i).getNombre().equals(p)){    
                resp = projectsArray.get(i);
            }
        }
        return resp;
    }

    public boolean delUsuario(String u){
        boolean resp=false;
        for (int i = 0; i < usersArray.size() && !resp; i++) {
            if(usersArray.get(i).equals(u)){
                resp=true;
                usersArray.remove(i);
            }
        }
        return resp;
    }
    
    public boolean delProyecto(Proyecto p){
        boolean resp=false;
        for (int i = 0; i < projectsArray.size() && !resp; i++) {
            if(projectsArray.get(i).getNombre().equals(p.getNombre())){
                resp=true;
                projectsArray.remove(i);
            }
        }
        return resp;
    }

    public List<String> usuarios(){
        return usersArray;
    }
    
    public List<String> colaboradores(String p){
        Proyecto toReturn = existeProyecto(p);
        if(toReturn == null)return null;
        return toReturn.getColaboradores();
    }
    
    public String getDuennoProyecto(String p){
        Proyecto toReturn = existeProyecto(p);
        if(toReturn == null)return null;
        return toReturn.getDuenno();
    }
    
    public boolean cambiarDuennoProyecto(String p, String user){
        Proyecto toReturn = existeProyecto(p);
        if(toReturn == null)return false;
        else{
            try{
                toReturn.setDuenno(user);
                return true;
            }catch(EntitiesException ex){
                return false;
            }
        }
    }
    
    public String compilarProyecto(Proyecto p, String u)throws EntitiesException{
        if(projectsArray.contains(p) && p.getColaboradores().contains(u)){
            return p.compilar();
        }else{
            return null;
        }
    }
}
