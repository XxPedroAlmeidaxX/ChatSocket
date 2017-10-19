package br.cefetmg.chat.server;

import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.implementation.service.UserBusiness;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class Server {
    public static void main(String[] args) { 
        try {
            Naming.rebind("MessageBusiness", UnicastRemoteObject.exportObject(new MessageBusiness(), 0));
            Naming.rebind("RoomBusiness", UnicastRemoteObject.exportObject(new RoomBusiness(), 0));
            Naming.rebind("UserBusiness", UnicastRemoteObject.exportObject(new UserBusiness(), 0));
        } catch (RemoteException | MalformedURLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        
    }    
}
