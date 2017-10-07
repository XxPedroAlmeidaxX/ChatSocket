/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.server;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.User;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
                    ObjectOutputStream o = new ObjectOutputStream(s.getOutputStream());
                    o.writeObject("msg");
                    o.flush();
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
                ObjectOutputStream o = new ObjectOutputStream(s.getOutputStream());
                o.writeObject("sala");
                o.flush();
            }
        }catch (IOException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
    }
    
    public static void notifyUserRoom(){
        try {
            for(Map.Entry<User, Socket> entrada : tabelaUsuarios.entrySet()){
                Socket s = entrada.getValue();
                ObjectOutputStream o = new ObjectOutputStream(s.getOutputStream());
                o.writeObject("usuarios");
                o.flush();
            }
        }catch (IOException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
    }
    
}
