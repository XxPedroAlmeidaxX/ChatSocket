/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.controller;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.view.MainView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
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
import javax.swing.JOptionPane;

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
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                            whatismyip.openStream()));

            String ipStr = in.readLine();
            ipStr=ipStr.substring(3);
            String[] parte = ipStr.split("\\.");
            ipStr="";
            for(String p : parte){
                ipStr+=p;
            }
            Long ip = Long.parseLong(ipStr);
            IUserBusiness user = (IUserBusiness) main.getRegistry().lookup("UserBusiness");
            //Obtem o nome do cliente
            String nome = userName.getText();
            //Obtem o usuário do cliente, criando-o caso não exista
            User u = user.logarUser(nome, ip, main.getUpdate());
            //Define o usuário logado
            main.setLogado(u);
            //Exibe a tela principal
            main.showHome();
        } catch (UnknownHostException | RemoteException | NotBoundException | MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException | BusinessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}