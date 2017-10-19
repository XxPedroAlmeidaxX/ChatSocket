/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.interfaces.service.IMessageReceiver;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.view.IMainView;
import javafx.application.Platform;

/**
 *
 * @author Aluno
 */
public class MessageReceiver implements IMessageReceiver{

    private IMainView ma;
    
    public MessageReceiver(IMainView ma){
        this.ma=ma;
    }
    
    @Override
    public void receiveMessage(Message m) throws BusinessException {
        //Recarrega a sala, executando na thread da aplicação do JavaFX
        Platform.runLater(() -> {
            try {
                ma.loadRoom(ma.getCurrentRoom());
            } catch (BusinessException ex) {
                System.out.println("Erro: " + ex);
            }
        });
    }

    @Override
    public void receiveUpdate(String idt) throws BusinessException {
        if(idt.equals("sala")){
            Platform.runLater(() -> ma.showHome());
        }else{
            //Usuario
            //Recarrega a home, e a sala caso o usuário esteja em uma, executando na thread da aplicação do JavaFX
            IRoomBusiness b = new RoomBusiness();
            if(ma.getCurrentRoom()!=null)
                ma.setCurrentRoom(b.getRoomById(ma.getCurrentRoom().getIdRoom()));
                        Room r = ma.getCurrentRoom();
                        Platform.runLater(() ->ma.showHome());
                        if(r!=null)
                            Platform.runLater(() -> {
                                try {
                                    System.out.println("Atualizou users");
                                    ma.loadRoom(r);
                                } catch (BusinessException ex) {
                                    System.out.println("Erro: " + ex);
                                }
                            });
        }
    }
    
}
