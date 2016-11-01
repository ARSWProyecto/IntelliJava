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
        
    }
    
    public boolean addUser(Usuario u){
        boolean resp;
        if(resp = !existeUsuario(u)){
            usersArray.add(u);
        }
        return resp;
    }

    public boolean existeUsuario(Usuario u) {
        boolean resp = false;
        for(int i = 0 ; i < usersArray.size() && !resp; i++){
            if(usersArray.get(i).getNombre().equals(u.getNombre())){
                resp = true;
            }
        }
        return resp;
    }

    public boolean addProject(Proyecto p){
        boolean resp;
        if(resp = !existeProyecto(p)){
            projectsArray.add(p);
        }
        return resp;
    }

    public boolean existeProyecto(Proyecto p) {
        boolean resp = false;
        for(int i = 0 ; i < projectsArray.size() && !resp; i++){
            if(projectsArray.get(i).getNombre().equals(p.getNombre())){
                resp = true;
            }
        }
        return resp;
    }
}
