/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.server;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author umcan
 */
public class Notificator {
    private static final Map<User, Socket> tabelaUsuarios = new HashMap<>();

    public static Map<User, Socket> getTabelaUsuarios() {
        return tabelaUsuarios;
    }
    
    public static void removeTabela(User u){
        tabelaUsuarios.remove(u);
    }
    
    public static void addTabela(User u, Socket s){
        tabelaUsuarios.put(u, s);
    }
    
    public static void notifyMessage(Message m){
        try {
            for(User u:m.getRoom().getUsuarios()){
                if(u.getIdUser()!=m.getUser().getIdUser()){
                    Socket s = tabelaUsuarios.get(u);
                    ObjectOutputStream update = new ObjectOutputStream(s.getOutputStream());
                    update.writeObject("msg");
                    update.flush();
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
    }
    
    public static void notifyRoom(){
        try {
            for(Map.Entry<User, Socket> entrada : tabelaUsuarios.entrySet()){
                Socket s = entrada.getValue();
                ObjectOutputStream update;
                update = new ObjectOutputStream(s.getOutputStream());
                update.writeObject("sala");
                update.flush();
            }
        }catch (IOException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
    }
    
    public static void notifyUserRoom(){
        try {
            for(Map.Entry<User, Socket> entrada : tabelaUsuarios.entrySet()){
                Socket s = entrada.getValue();
                ObjectOutputStream update;
                update = new ObjectOutputStream(s.getOutputStream());
                update.writeObject("usuarios");
                update.flush();
            }
        }catch (IOException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
    }
    
}
