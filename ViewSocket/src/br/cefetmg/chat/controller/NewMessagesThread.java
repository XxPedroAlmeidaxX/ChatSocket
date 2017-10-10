/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.controller;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.view.MainView;
import javafx.application.Platform;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */
public class NewMessagesThread implements Runnable{
    
    //Conexão do cliente
    private IConnection c;
    //Visão principal do JavaFX
    public MainView p;
    
    public NewMessagesThread(IConnection c, MainView p){
        this.c = c;
        this.p = p;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                //Recebe mensagens de atualização
                String update = (String)c.receiveData("U");
                switch(update){
                    //Nova mensagem
                    case "msg":
                        //Recarrega a sala, executando na thread da aplicação do JavaFX
                        Platform.runLater(() -> {
                            try {
                                p.loadRoom(p.getCurrentRoom());
                            } catch (BusinessException ex) {
                                System.out.println("Erro: " + ex);
                            }
                        });
                        break;
                    //Alterações nas salas
                    case "sala":
                        //Recarrega a home, executando na thread da aplicação do JavaFX
                        Platform.runLater(() -> p.showHome());
                        break;
                    //Alteração nos usuários
                    case "usuarios":
                        //Recarrega a home, e a sala caso o usuário esteja em uma, executando na thread da aplicação do JavaFX
                        Room r = p.getCurrentRoom();
                        Platform.runLater(() ->p.showHome());
                        if(r!=null)
                            Platform.runLater(() -> {
                                try {
                                    p.loadRoom(r);
                                } catch (BusinessException ex) {
                                    System.out.println("Erro: " + ex);
                                }
                            });
                        break;
                }
            } catch (ConnectionException ex) {
               throw new RuntimeException(ex);
            }
        }
    }
    
}
