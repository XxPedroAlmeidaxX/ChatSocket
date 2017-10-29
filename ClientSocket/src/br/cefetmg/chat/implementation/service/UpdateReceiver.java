/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import javafx.application.Platform;
import br.cefetmg.chat.interfaces.service.IUpdateReceiver;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

/**
 *
 * @author Aluno
 */
public class UpdateReceiver implements IUpdateReceiver, Serializable{
    
    public UpdateReceiver(){
        
    }
    
    @Override
    public void receiveMessage(Message m) throws BusinessException, RemoteException {
        //Recarrega a sala, executando na thread da aplicação do JavaFX
        if(Objects.equals(m.getRoom().getIdRoom(), MainStaticUpdate.main.getCurrentRoom().getIdRoom())){
            Platform.runLater(() -> {
                try {
                    MainStaticUpdate.main.loadRoom(MainStaticUpdate.main.getCurrentRoom());
                } catch (BusinessException ex) {
                    System.out.println("Erro: " + ex);
                }
            });
        }
    }

    @Override
    public void receiveUpdate(String idt, IRoomBusiness b) throws BusinessException, RemoteException {
        System.out.println("UPDATE");
        if(idt.equals("sala")){
            Platform.runLater(() -> MainStaticUpdate.main.showHome());
        }else{
            //Usuario
            //Recarrega a home, e a sala caso o usuário esteja em uma, executando na thread da aplicação do JavaFX
            if(MainStaticUpdate.main.getCurrentRoom()!=null)
                MainStaticUpdate.main.setCurrentRoom(b.getRoomById(MainStaticUpdate.main.getCurrentRoom().getIdRoom()));
                        Room r = MainStaticUpdate.main.getCurrentRoom();
                        Platform.runLater(() ->MainStaticUpdate.main.showHome());
                        if(r!=null)
                            Platform.runLater(() -> {
                                try {
                                    System.out.println("Atualizou users");
                                    MainStaticUpdate.main.loadRoom(r);
                                } catch (BusinessException ex) {
                                    System.out.println("Erro: " + ex);
                                }
                            });
        }
    }
    
}
