/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.interfaces.service.IUpdateReceiver;
import br.cefetmg.chat.server.Server;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author umcan
 */
public class UpdateSender implements IUpdateReceiver{

    @Override
    public void receiveMessage(Message m) throws BusinessException, RemoteException {
        for(int I=0; I<Server.connected.size(); I++){
            IUpdateReceiver up = Server.connected.get(I);
            try{
                up.receiveMessage(m);
            }catch(RemoteException ex){
                Server.connected.remove(I);
                I--;
            }
        }
        
    }

    @Override
    public void receiveUpdate(String idt) throws BusinessException, RemoteException {
        for(int I=0; I<Server.connected.size(); I++){
            IUpdateReceiver up = Server.connected.get(I);
            try{
                up.receiveUpdate(idt);
            }catch(RemoteException ex){
                Server.connected.remove(I);
                I--;
            }
        }
    }
    
}
