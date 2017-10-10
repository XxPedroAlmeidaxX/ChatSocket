/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.controller;
import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.view.MainView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class HomeController implements Initializable {

    @FXML
    private TextArea txtMsg;
    
    //Classe principal do JavaFX
    private MainView main;
    
    public void setMainView(MainView main){
        this.main=main;
    }
    
    @FXML
    private void enviarMsg(){
        //Cria a nova mensagem
        MessageBusiness msgB = new MessageBusiness(main.conn);
        Message m = new Message();
        m.setIdMessage(new Long(0));
        m.setRoom(main.getCurrentRoom());
        m.setStateMessage(Boolean.FALSE);
        m.setTextMessage(txtMsg.getText());
        m.setUser(main.getLogado());
        //Se tem um alvo
        if(main.getAlvoSelec()!=null){
            //A mensagem é direcionada
            m.setStateMessage(true);
            m.setTargetMessage(main.getAlvoSelec());
        }else{
            //A mensagem não é direcionada
            m.setStateMessage(false);
        }
        
        //Insere a mensagem
        try {
            msgB.insertMessage(m);
        } catch (BusinessException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        txtMsg.setText("");
    }
    
    @FXML
    private void criarSala(){
        //Exibe a tela de criar sala
        main.showRoomMaker();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
}
