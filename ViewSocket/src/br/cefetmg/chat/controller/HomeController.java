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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Adalbs
 */
public class HomeController implements Initializable {

    @FXML
    private TextArea txtMsg;
    
    private MainView main;
    
    public void setMainView(MainView main){
        this.main=main;
    }
    
    @FXML
    private void enviarMsg(){
        MessageBusiness msgB = new MessageBusiness(main.conn);
        Message m = new Message();
        m.setIdMessage(new Long(0));
        m.setRoom(main.getCurrentRoom());
        m.setStateMessage(Boolean.FALSE);
        m.setTextMessage(txtMsg.getText());
        m.setUser(main.getLogado());
        try {
            msgB.insertMessage(m);
        } catch (BusinessException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
    
    @FXML
    private void criarSala(){
        main.showRoomMaker();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
}
