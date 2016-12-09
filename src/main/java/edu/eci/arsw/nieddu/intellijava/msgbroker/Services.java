/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

import edu.eci.arsw.nieddu.intellijava.entities.EntitiesException;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author CERONSANCHEZEDWINALE
 */
@Service
public interface Services {
   
    public boolean addUser(String u);
        

    public String existeUsuario(String u);

    public boolean addProject(Proyecto p);
    
    public Proyecto existeProyecto(String p);
    
    public Boolean updateTextProject(Proyecto p);
    
    public boolean delUsuario(String u);
    
    public boolean delProyecto(Proyecto p);

    //public List<String> usuarios();
    
    public List<String> colaboradores(String p);
    
    public String getDuennoProyecto(String p);
    
    public boolean cambiarDuennoProyecto(String p, String user);
    
    public String compilarProyecto(Proyecto p, String u);

}
