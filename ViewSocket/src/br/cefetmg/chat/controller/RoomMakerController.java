package br.cefetmg.chat.controller;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.view.MainView;
import br.cefetmg.chat.view.MainView;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RoomMakerController {

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
        Room r = new Room();
        r.setIdRoom(Long.MIN_VALUE);
        r.setNameRoom(txtNome.getText());
        r.setStateRoom(chkPrivado.isSelected());
        r.setPassword(pswSenha.getText());
        r.setUsuarios(new ArrayList<>());
        mv.showHome();
    }
}
