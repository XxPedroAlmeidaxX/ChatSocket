/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.controller;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.view.MainView;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author aluno
 */
public class NewMessagesThread implements Runnable{
    
    private IConnection c;
    public MainView p;
    
    public NewMessagesThread(IConnection c, MainView p){
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
                    FlowPane mensagens = (FlowPane)p.getRootLayout().lookup("#mensagensContainer");
                    ObservableList filhasMensagens = mensagens.getChildren();
                    mensagens.setVgap(10);
                    Label lMsg = new Label();
                    lMsg.setId("mensagem"+m.getIdMessage());
                    lMsg.setText(m.getTextMessage());
                    lMsg.setAlignment(Pos.BOTTOM_LEFT);
                    lMsg.setMinWidth(mensagens.getWidth());
                    if(m.getUser().getIpUser()==p.getLogado().getIpUser() && m.getUser().getNameUser().equals(p.getLogado().getNameUser())){
                        lMsg.setStyle("-fx-background-color: #e0e0e0");
                        lMsg.setTextFill(Paint.valueOf("black")); 
                    }else{
                        lMsg.setStyle("-fx-background-color: #42e8f4");
                        lMsg.setTextFill(Paint.valueOf("white")); 
                    }
                    Label lUsuario = new Label();
                    if(m.getUser().getIpUser()==p.getLogado().getIpUser() && m.getUser().getNameUser().equals(p.getLogado().getNameUser())){
                        lUsuario.setStyle("-fx-background-color: #e0e0e0");
                        lUsuario.setTextFill(Paint.valueOf("black")); 
                    }else{
                        lUsuario.setStyle("-fx-background-color: #42e8f4");
                        lUsuario.setTextFill(Paint.valueOf("white")); 
                    }
                    lUsuario.setAlignment(Pos.BOTTOM_LEFT);
                    lUsuario.setMinWidth(mensagens.getWidth());
                    lUsuario.setId("usMsg-"+m.getUser().getIpUser()+"-"+m.getUser().getNameUser());
                    lUsuario.setText(m.getUser().getNameUser());
                    lUsuario.setFont(Font.font ("System", 10));
                    filhasMensagens.add(lMsg);
                    filhasMensagens.add(lUsuario);
                    break;
                case "sala":
                    Room r = (Room)c.receiveMensagens();
                    FlowPane salasDisponiveis = (FlowPane)p.getRootLayout().lookup("#paneAddRooms");
                    salasDisponiveis.setVgap(15);
                    ObservableList filhas = salasDisponiveis.getChildren();
                    Button b = new Button();
                    b.setText(r.getNameRoom()+"\n"+r.getUsuarios().size()+" usuario(s)");
                    b.setMinWidth(salasDisponiveis.getWidth());
                    b.setTextFill(Paint.valueOf("white")); 
                    b.setStyle("-fx-background-color: #660000");
                    b.setId("sala"+r.getIdRoom().toString());
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Long id = Long.parseLong(((Button)event.getSource()).getId().substring(4));
                            try {
                                IRoomBusiness b = new RoomBusiness();
                                p.loadRoom(b.getRoomById(id));
                            } catch (BusinessException ex) {
                                System.out.println("Erro: " + ex.getMessage());
                            }
                        }
                    });
                    filhas.add(b);
                    break;
            }
        } catch (ConnectionException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
}
