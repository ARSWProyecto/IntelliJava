/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;


import com.google.gson.Gson;
import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 *
 * @author 2100038
 */
@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    Services ins;
    
    public static Gson gson = new Gson();

    @MessageMapping("/project.{nameProject}")
    public void updateProject(@DestinationVariable String nameProject, String text) throws Exception {
        Proyecto p = ins.existeProyecto(nameProject);
        
        Map<String,String> map = new HashMap<>();
        map = (Map<String,String>) gson.fromJson(text, map.getClass());
        p.getPaquete(0).escribirEnArchivo(0, map.get("text"));
        //System.out.println(map);
        ins.updateProject(p);
        msgt.convertAndSend("/topic/project."+nameProject, text);
    }
}
