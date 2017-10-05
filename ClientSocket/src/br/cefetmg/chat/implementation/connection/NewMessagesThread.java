/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;

/**
 *
 * @author aluno
 */
public class NewMessagesThread implements Runnable{
    
    private IConnection c;
    public static boolean esperando;
    
    public NewMessagesThread(Connection c){
        this.c = c;
    }
    
    @Override
    public void run() {
        try {
            Object mensagem = c.receiveMensagens();
        } catch (ConnectionException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        //TODO
    }
    
}
