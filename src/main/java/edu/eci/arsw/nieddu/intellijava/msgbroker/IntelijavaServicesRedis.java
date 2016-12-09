/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import com.google.gson.Gson;
import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Nieddu
 */
@Service
public class IntelijavaServicesRedis implements Services{

    //public static CopyOnWriteArrayList<String> usersArray;
    //public static CopyOnWriteArrayList<Proyecto> projectsArray;
    public IntelijavaServicesRedis() {
        //usersArray= new CopyOnWriteArrayList();
        //projectsArray= new CopyOnWriteArrayList<>();
    }

    public boolean addUser(String u) {
        boolean resp;
        System.out.println("entro a addUser");
        Jedis jedis = JedisUtil.getPool().getResource();
        Map<String, String> usuario = new HashMap<>();
        usuario.put("nombre", u);
        if (resp = (existeUsuario(u) == null)) {
            jedis.hmset("usuario:" + u, usuario);
            System.out.println("agrego usuario");
        }
        System.out.println(resp);
        jedis.close();
        return resp;
    }

    public String existeUsuario(String nombre) {
        String resp = null;
        System.out.println("Entro al existe usuario");
        Jedis jedis = JedisUtil.getPool().getResource();
        Map<String, String> usuario = jedis.hgetAll("nombre:" + nombre);
        System.out.println(usuario.size()+" "+usuario+" ");
        if (!usuario.isEmpty()) {
            resp = nombre;
        }
        jedis.close();
        return resp;
    }

    public boolean addProject(Proyecto p) {
        boolean resp;
        Jedis jedis = JedisUtil.getPool().getResource();
        if (resp = existeProyecto(p.getNombre()) == null) {
            Gson gson = new Gson();
            String project = gson.toJson(p);
            System.out.println(project); // IMPRESIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN
            Map<String, String> proyecto = new HashMap<>();
            proyecto.put("nombre", p.getNombre());
            proyecto.put("proyecto", project);
            jedis.hmset("proyecto:" + p.getNombre(), proyecto);
            jedis.close();
        }
        return resp;
    }
    
    public Proyecto existeProyecto(String p) {
        Proyecto resp = null;
        Jedis jedis = JedisUtil.getPool().getResource();
        Map<String, String> proyecto = jedis.hgetAll("nombre:"+p);
        if(proyecto.size() == 0){
            Gson gson = new Gson();
            resp = gson.fromJson(jedis.hget("proyecto:"+p, "proyecto"), Proyecto.class);   
        }
        jedis.close();
        return resp;
    }
    
    public boolean delUsuario(String u) {
        boolean resp = false;
        Jedis jedis = JedisUtil.getPool().getResource();
        Long res = jedis.hdel("usuario:"+u);
        if(res>0){
            resp = true;
        }
        jedis.close();
        return resp;
    }
    
    public boolean delProyecto(Proyecto p) {
        boolean resp = false;
        Jedis jedis = JedisUtil.getPool().getResource();
        Long res = jedis.hdel("proyecto:"+p.getNombre());
        if(res>0){
            resp = true;
        }
        jedis.close();
        return resp;
    }
    /*
    public List<String> usuarios() {
        return usersArray;
    }*/
    
    public List<String> colaboradores(String p) {
        Proyecto toReturn = existeProyecto(p);
        if (toReturn == null) {
            return null;
        }
        return toReturn.getColaboradores();
    }

    public String getDuennoProyecto(String p) {
        Proyecto toReturn = existeProyecto(p);
        if (toReturn == null) {
            return null;
        }
        return toReturn.getDuenno();
    }
    
    public boolean cambiarDuennoProyecto(String p, String user) {
        Proyecto toReturn = existeProyecto(p);
        if (toReturn == null) {
            return false;
        } else {
            try {
                toReturn.setDuenno(user);
                addProject(toReturn);
                return true;
            }catch (EntitiesException ex) {
                return false;
            }
        }
    }
    
    public String compilarProyecto(Proyecto p, String u){
        if(existeProyecto(p.getNombre()) != null && p.getColaboradores().contains(u)){
            return p.compilar();
        }else {
            return null;
        }
    }
}
