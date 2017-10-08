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

/**
 *
 * @author aluno
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
                String update = (String)c.receiveUpdates();
                switch(update){
                    //Nova mensagem
                    case "msg":
                        //Recarrega a sala
                        p.loadRoom(p.getCurrentRoom());
                        break;
                    //Alterações nas salas
                    case "sala":
                        //Recarrega a home
                        p.showHome();
                        break;
                    //Alteração nos usuários
                    case "usuarios":
                        //Recarrega a home, e a sala caso o usuário esteja em uma
                        Room r = p.getCurrentRoom();
                        p.showHome();
                        if(r!=null)
                            p.loadRoom(r);
                        break;
                }
            } catch (ConnectionException | BusinessException ex) {
               throw new RuntimeException("Erro: " +ex.getMessage());
            }
        }
    }
    
}
