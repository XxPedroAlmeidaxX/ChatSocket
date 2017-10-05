/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.view;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.ConnectionManager;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javax.swing.event.HyperlinkEvent;

/**
 *
 * @author Aluno
 */
public class MainView extends Application {
    
    private Stage primaryStage;
    private Pane rootLayout;
    private User logado;
    private List<Room> salas;
    public IConnection conn;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Logar Chat");
        try {
            conn = ConnectionManager.getInstance().getConnection();
        } catch (ConnectionException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        salas = new ArrayList<>();
        ArrayList<User> list = new ArrayList<>();
        list.add(new User());
        Room r1 = new Room(new Long(0), "sala 1", true, " ", list);
        list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        Room r2 = new Room(new Long(1), "sala 2", true, " ", list);
        list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        Room r3 = new Room(new Long(2), "sala 3", true, " ", list);
        salas.add(r1);
        salas.add(r2);
        salas.add(r3);
        showHome();
    }
    
    public void showHome(){
        try {
            //IRoomBusiness roomB = new RoomBusiness();
            //salas = roomB.getAllRoom();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("Home.fxml"));
            rootLayout = (VBox) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            HomeController controller = loader.getController();
            controller.setMainView(this);
            FlowPane salasDisponiveis = (FlowPane)rootLayout.lookup("#paneAddRooms");
            salasDisponiveis.setVgap(15);
            ObservableList filhas = salasDisponiveis.getChildren();
            for(Room r:salas){
                Button b = new Button();
                b.setText(r.getNameRoom()+"\n"+r.getUsuarios().size()+" usuario(s)");
                b.setMinWidth(salasDisponiveis.getWidth());
                b.setTextFill(Paint.valueOf("white")); 
                b.setStyle("-fx-background-color: #660000");
                b.setId(r.getIdRoom().toString());
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(((Button)event.getSource()).getId());
                    }
                });
                filhas.add(b);
            }
        } catch (IOException  e) {
            System.out.println("Erro: " + e.getMessage());
        }
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
            System.out.println("Erro: " + e.getMessage());
        }
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

    public Pane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(Pane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public List<Room> getSalas() {
        return salas;
    }

    public void setSalas(List<Room> salas) {
        this.salas = salas;
    }
    
    
}
