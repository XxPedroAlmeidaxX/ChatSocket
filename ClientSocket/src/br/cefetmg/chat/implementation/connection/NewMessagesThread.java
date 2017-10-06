/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import javafx.scene.layout.Pane;

/**
 *
 * @author aluno
 */
public class NewMessagesThread implements Runnable{
    
    private IConnection c;
    public Pane p;
    
    public NewMessagesThread(IConnection c, Pane p){
        this.c = c;
        this.p = p;
    }
    
    @Override
    public void run() {
        try {
            String update = (String)c.receiveMensagens();
            switch(update){
                case "msg":
                    Message m = (Message)c.receiveMensagens();
                    
                case "sala":
                    Room r = (Room)c.receiveMensagens();
            }
        } catch (ConnectionException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
}
