/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.interfaces.service.IUpdateReceiver;
import br.cefetmg.chat.server.Server;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author umcan
 */
public class UpdateSender extends UnicastRemoteObject implements IUpdateReceiver, Serializable{

    public UpdateSender() throws RemoteException{
        super();
    }
   
    @Override
    public void receiveMessage(Message m) throws BusinessException, RemoteException {
        Set<Map.Entry<Long, IUpdateReceiver>> updateRec = Server.connected.entrySet();
        ArrayList<Long> ids = new ArrayList<>();
        for(Map.Entry<Long, IUpdateReceiver> update:updateRec){
            IUpdateReceiver up = update.getValue();
            try{
                up.receiveMessage(m);
            }catch(RemoteException ex){
                ids.add(update.getKey());
                throw new RuntimeException(ex);
                
            }
        }
        for(Long id:ids){
            RoomBusiness room = new RoomBusiness();
            //O usuário só fica em uma sala
            room.removeUserRoom(id, null);
            Server.connected.remove(id);
        }
        
    }

    @Override
    public void receiveUpdate(String idt, IRoomBusiness b) throws BusinessException, RemoteException {
        Set<Map.Entry<Long, IUpdateReceiver>> updateRec = Server.connected.entrySet();
        ArrayList<Long> ids = new ArrayList<>();
        for(Map.Entry<Long, IUpdateReceiver> update:updateRec){
            IUpdateReceiver up = update.getValue();
            try{
                up.receiveUpdate(idt, b);
            }catch(RemoteException ex){
                ids.add(update.getKey());
                throw new RuntimeException(ex);
            }
        }
        for(Long id:ids){
            RoomBusiness room = new RoomBusiness();
            //O usuário só fica em uma sala
            try{
                room.removeUserRoom(id, null);
            }catch(NullPointerException ex){
                
            }
            
            Server.connected.remove(id);
        }
    }
    
}
