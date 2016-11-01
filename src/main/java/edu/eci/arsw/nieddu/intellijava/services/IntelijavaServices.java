/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.services;

import edu.eci.arsw.nieddu.intellijava.entities.Usuario;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 *
 * @author CERONSANCHEZEDWINALE
 */

@Service
public class IntelijavaServices {
    
    public static CopyOnWriteArrayList usersArray;
    
    public IntelijavaServices(){
        usersArray= new CopyOnWriteArrayList();
        
    }
    
    public void addUser(Usuario u){
        usersArray.add(u);
    }
    /*
    public void delUser(Usuario u){
        
    }*/
}
