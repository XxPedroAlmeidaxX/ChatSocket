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
import br.cefetmg.chat.view.interfaces.IMainView;
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
        if(Objects.equals(m.getRoom().getIdRoom(), GambsTemp.main.getCurrentRoom().getIdRoom())){
            Platform.runLater(() -> {
                try {
                    GambsTemp.main.loadRoom(GambsTemp.main.getCurrentRoom());
                } catch (BusinessException ex) {
                    System.out.println("Erro: " + ex);
                }
            });
        }
    }

    @Override
    public void receiveUpdate(String idt) throws BusinessException, RemoteException {
        if(idt.equals("sala")){
            Platform.runLater(() -> GambsTemp.main.showHome());
        }else{
            //Usuario
            //Recarrega a home, e a sala caso o usuário esteja em uma, executando na thread da aplicação do JavaFX
            IRoomBusiness b=null;
            try {
                b = (IRoomBusiness) Naming.lookup("RoomBusiness");
            } catch (NotBoundException | MalformedURLException ex) {
                throw new RemoteException(ex.getMessage());
            }
            if(GambsTemp.main.getCurrentRoom()!=null)
                GambsTemp.main.setCurrentRoom(b.getRoomById(GambsTemp.main.getCurrentRoom().getIdRoom()));
                        Room r = GambsTemp.main.getCurrentRoom();
                        Platform.runLater(() ->GambsTemp.main.showHome());
                        if(r!=null)
                            Platform.runLater(() -> {
                                try {
                                    System.out.println("Atualizou users");
                                    GambsTemp.main.loadRoom(r);
                                } catch (BusinessException ex) {
                                    System.out.println("Erro: " + ex);
                                }
                            });
        }
    }
    
}
