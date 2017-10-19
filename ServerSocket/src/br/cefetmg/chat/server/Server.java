package br.cefetmg.chat.server;

import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.implementation.service.UserBusiness;
import br.cefetmg.chat.interfaces.service.IUpdateReceiver;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class Server {
    public static List<IUpdateReceiver> connected = new ArrayList<>();
    public static Registry registry;
    
    public static void main(String[] args) { 
        try {
            registry = LocateRegistry.createRegistry(2222);
            registry.rebind("MessageBusiness", UnicastRemoteObject.exportObject(new MessageBusiness(), 0));
            registry.rebind("RoomBusiness", UnicastRemoteObject.exportObject(new RoomBusiness(), 0));
            registry.rebind("UserBusiness", UnicastRemoteObject.exportObject(new UserBusiness(), 0));
        } catch (RemoteException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        
    }    
}
