/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.view;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.ConnectionManager;
import br.cefetmg.chat.implementation.service.UserBusiness;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.interfaces.service.IUserBusiness;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author Adalbs
 */
public class landPageController {
    @FXML
    private TextField userName;
    
    private MainView main;
    
    public void setMainView(MainView main){
        this.main=main;
    }
    
    @FXML
    private void entrar(){
        try {
            IConnection conn = ConnectionManager.getInstance().getConnection();
            Long ip = conn.getIp();
            System.out.println(ip);
            IUserBusiness user = new UserBusiness();
            User u = user.getUserById(ip);
            if(u.getIpUser()==null){            
                u.setNameUser(userName.getText());
                u.setIpUser(conn.getIp());
                user.insertUser(u);
            }else{
                main.setLogado(u);
                main.showHome();
            }
        } catch (BusinessException | ConnectionException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
}