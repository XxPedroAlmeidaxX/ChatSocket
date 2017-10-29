package br.cefetmg.chat.server;

import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.implementation.service.UpdateSender;
import br.cefetmg.chat.implementation.service.UserBusiness;
import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.interfaces.service.IUpdateReceiver;
import br.cefetmg.chat.interfaces.service.IUserBusiness;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.HashMap;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class Server {
    public static Map<Long, IUpdateReceiver> connected = new HashMap<>();
    public static Registry registry;
    
    public static void main(String[] args) { 
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }        
        
        try {
            System.out.println("Security ok");
            IRoomBusiness room = new RoomBusiness();
            IMessageBusiness message = new MessageBusiness();
            IUserBusiness user = new UserBusiness();
            IUpdateReceiver sender = new UpdateSender();
            System.out.println("Export ok");
            Registry registry = LocateRegistry.createRegistry(2222);
            System.out.println("Registry ok");
            registry.rebind("MessageBusiness", message);
            registry.rebind("UserBusiness", user);
            registry.rebind("RoomBusiness", room);
            registry.rebind("UpdateSender", sender);
            System.err.println("Rebind ok");
        } catch (RemoteException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }    
}
