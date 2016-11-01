/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;

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
public class IntellijavaServices {
    
    public CopyOnWriteArrayList usersArray;
    
    public IntellijavaServices(){
        usersArray= new CopyOnWriteArrayList();
    }
    
    public void addUser(Usuario u){
        usersArray.add(u);
    }
}
