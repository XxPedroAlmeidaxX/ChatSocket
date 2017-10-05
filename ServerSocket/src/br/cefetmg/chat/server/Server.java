package br.cefetmg.chat.server;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;

public class Server {
    public static void main(String[] args) {
        try {
            Connection.setServer(3333);
        } catch (ConnectionException ex) {
            System.out.println("\nErro ao definir a porta do servidor: " + ex);
            System.exit(0);
        }
        
        while(true) {
            try {
                new Thread(new AdapterServer()).start();
            } catch (ConnectionException ex) {
                System.out.println("\nFalha em uma das conexões: " + ex);
            }
        }
    }    
}
