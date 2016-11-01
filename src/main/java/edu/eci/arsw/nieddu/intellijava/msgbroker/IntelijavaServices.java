/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import edu.eci.arsw.nieddu.intellijava.entities.Usuario;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nieddu
 */

@Service
public class IntelijavaServices {
    
    public static CopyOnWriteArrayList<Usuario> usersArray;
    public static CopyOnWriteArrayList<Proyecto> projectsArray;
    
    public IntelijavaServices(){
        usersArray= new CopyOnWriteArrayList();
        projectsArray= new CopyOnWriteArrayList<>();
    }
    
    public boolean addUser(Usuario u){
        boolean resp;
        if(resp = existeUsuario(u.getNombre())==null){
            usersArray.add(u);
        }
        return resp;
    }

    public Usuario existeUsuario(String u) {
        Usuario resp = null;
        for(int i = 0 ; i < usersArray.size() && resp==null; i++){
            if(usersArray.get(i).getNombre().equals(u)){
                resp = usersArray.get(i);
            }
        }
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
}
