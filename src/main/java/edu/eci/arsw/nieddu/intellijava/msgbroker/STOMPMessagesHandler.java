/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.nieddu.intellijava.msgbroker;


import edu.eci.arsw.nieddu.intellijava.entities.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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

    @MessageMapping("/project.{nameProject}")
    public void updateProject(@DestinationVariable String nameProject, String text, String author) throws Exception {
        Proyecto p = ins.existeProyecto(nameProject);
        String[] datos = text.split("AUTOR");
        System.out.println(text+ " dato[0]: "+datos[0]+" dato[1]: "+datos[1]);
        p.getPaquete(0).escribirEnArchivo(0, datos[0]);
        ins.updateTextProject(p);
        msgt.convertAndSend("/topic/project."+nameProject, text);
    }
}
