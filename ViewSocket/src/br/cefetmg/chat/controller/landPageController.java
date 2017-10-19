/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.controller;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.implementation.service.UpdateReceiver;
import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.view.MainView;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class landPageController implements Initializable{
    @FXML
    private TextField userName;
    
    //Classe principal do JavaFX
    private MainView main;
    
    public void setMainView(MainView main){
        this.main=main;
    }
    
    @FXML
    private void entrar(){
        try {
            //Obtem o ip do cliente
            InetAddress local = InetAddress.getLocalHost();
            //Converte o ip em long
            Long ip = new Long(0);
            for (byte b:local.getAddress()){  
                ip = ip << 8 | (b & 0xFF);  
            } 
            
            IUserBusiness user = (IUserBusiness) main.getRegistry().lookup("UserBusiness");
            //Obtem o nome do cliente
            String nome = userName.getText();
            //Obtem o usuário do cliente, criando-o caso não exista
            User u = user.logarUser(nome, ip, new UpdateReceiver());
            //Define o usuário logado
            main.setLogado(u);
            //Exibe a tela principal
            main.showHome();
        } catch (UnknownHostException | RemoteException | NotBoundException | BusinessException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}