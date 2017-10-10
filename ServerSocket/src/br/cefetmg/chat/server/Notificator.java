/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.server;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */
public class Notificator {
    
    //Tabela de usuários
    private static final Map<Long, Connection> tabelaUsuarios = new HashMap<>();

    public static Map<Long, Connection> getTabelaUsuarios() {
        return tabelaUsuarios;
    }
    
    //Remove usuário da tabela
    public static void removeTabela(User u){
        tabelaUsuarios.remove(u.getIdUser());
    }
    
    //Adiciona usuário na tabela
    public static void addTabela(User u, Connection s){
        tabelaUsuarios.put(u.getIdUser(), s);
    }
    
    public static void notifyMessage(Message m){
        System.out.println("Notifica");
        //Se a mensagem é privada
        if(m.getStateMessage()){
            System.out.println("Privada");
            //Obtem o alvo
            Connection s = tabelaUsuarios.get(m.getTargetMessage().getIdUser());
            //Envia a atualização para o alvo
            try {
                s.sendData("msg", "U");
            } catch (ConnectionException ex) {
                Logger.getLogger(Notificator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //Para cada usuário da sala
            System.out.println(m.getRoom().getUsuarios().size());
            for(User u:m.getRoom().getUsuarios()){
                System.out.println("Usuário: " + u.getNameUser());
                //Obtem a conexão do usuário
                Connection s = tabelaUsuarios.get(u.getIdUser());
                //Envia a atualização
                try {
                    s.sendData("msg", "U");
                } catch (ConnectionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    
    public static void notifyRoom(){
        //Para cada usuário, já que todos veem informações da sala
        for(Map.Entry<Long, Connection> entrada : tabelaUsuarios.entrySet()){
            //Obtem a conexão
            Connection s = entrada.getValue();
            //Envia a atualização
            try {
                s.sendData("sala", "U");
            } catch (ConnectionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public static void notifyUserRoom(){
        //Para cada usuário, já que todos veem ao menos o numero de usuários de uma sala
        for(Map.Entry<Long, Connection> entrada : tabelaUsuarios.entrySet()){
            //Obtem a conexão
            Connection s = entrada.getValue();
            //Envia a atualização
            try {
                s.sendData("usuarios", "U");
            } catch (ConnectionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
}
