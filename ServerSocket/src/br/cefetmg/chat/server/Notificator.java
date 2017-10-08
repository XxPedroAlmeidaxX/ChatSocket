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
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author umcan
 */
public class Notificator {
    
    //Tabela de usuários
    private static final Map<User, Connection> tabelaUsuarios = new IdentityHashMap<>();

    public static Map<User, Connection> getTabelaUsuarios() {
        return tabelaUsuarios;
    }
    
    //Remove usuário da tabela
    public static void removeTabela(User u){
        tabelaUsuarios.remove(u);
    }
    
    //Adiciona usuário na tabela
    public static void addTabela(User u, Connection s){
        tabelaUsuarios.put(u, s);
    }
    
    public static void notifyMessage(Message m){
        //Se a mensagem é privada
        if(m.getStateMessage()){
            //Obtem o alvo
            Connection s = tabelaUsuarios.get(m.getTargetMessage());
            //Envia a atualização para o alvo
            try {
                s.update("msg");
            } catch (ConnectionException ex) {
                Logger.getLogger(Notificator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //Para cada usuário da sala
            for(User u:m.getRoom().getUsuarios()){
                //Se o usuário não é o remetente da mensagem
                if(u.getIdUser()!=m.getUser().getIdUser()){
                    //Obtem a conexão do usuário
                    Connection s = tabelaUsuarios.get(u);
                    //Envia a atualização
                    try {
                        s.update("msg");
                    } catch (ConnectionException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
    
    public static void notifyRoom(){
        //Para cada usuário, já que todos veem informações da sala
        for(Map.Entry<User, Connection> entrada : tabelaUsuarios.entrySet()){
            //Obtem a conexão
            Connection s = entrada.getValue();
            //Envia a atualização
            try {
                s.update("sala");
            } catch (ConnectionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public static void notifyUserRoom(){
        //Para cada usuário, já que todos veem ao menos o numero de usuários de uma sala
        for(Map.Entry<User, Connection> entrada : tabelaUsuarios.entrySet()){
            //Obtem a conexão
            Connection s = entrada.getValue();
            //Envia a atualização
            try {
                s.update("usuarios");
            } catch (ConnectionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
}
