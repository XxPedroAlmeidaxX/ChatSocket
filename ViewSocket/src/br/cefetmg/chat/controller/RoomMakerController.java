package br.cefetmg.chat.controller;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.view.MainView;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida & Pedro Otávio
 */

public class RoomMakerController {
    
    //Classe principal do JavaFX
    private MainView mv;

    public void setMainView(MainView mv) {
        this.mv = mv;
    }

    @FXML
    TextField txtNome;

    @FXML
    PasswordField pswSenha;

    @FXML
    CheckBox chkPrivado;

    @FXML
    private void cancelar() {
        mv.showHome();
    }

    @FXML
    private void criar() {
        try {
            //Cria a nova sala
            Room r = new Room();
            r.setNameRoom(txtNome.getText());
            r.setStateRoom(chkPrivado.isSelected());
            r.setPassword(pswSenha.getText());
            r.setIdRoom(new Long(0));
            IRoomBusiness business = (IRoomBusiness) mv.getRegistry().lookup("RoomBusiness");
            r = business.insertRoom(r);
            
            //Exibe a tela principal e carrega a sala
            mv.showHome();
            mv.loadRoom(r);
        } catch (BusinessException | RemoteException | NotBoundException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
    }
}
