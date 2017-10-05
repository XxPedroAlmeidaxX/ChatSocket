/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.view;

import br.cefetmg.chat.domain.User;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Aluno
 */
public class MainView extends Application {
    
    private Stage primaryStage;
    private Pane rootLayout;
    private User logado;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Logar Chat");
        showLogin();
    }
    
    public void showLogin(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("landPage.fxml"));
            rootLayout = (VBox) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            landPageController controller = loader.getController();
            controller.setMainView(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showHome(){
        
    }

    public void showRoomMaker() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("RoomMaker.fxml"));
            rootLayout = (VBox) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RoomMakerController rmc = loader.getController();
            rmc.setMainView(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public User getLogado() {
        return logado;
    }

    public void setLogado(User logado) {
        this.logado = logado;
    }
    
    
}
