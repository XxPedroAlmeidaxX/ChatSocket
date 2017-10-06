package br.cefetmg.chat.view;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.ConnectionManager;
import br.cefetmg.chat.implementation.connection.NewMessagesThread;
import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
            new Thread(new NewMessagesThread(conn, rootLayout)).start();
        } catch (ConnectionException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        showHome();
    }
    
    public void showHome(){
        try {
            IRoomBusiness roomB = new RoomBusiness();
            salas = roomB.getAllRoom();
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
                b.setId("sala"+r.getIdRoom().toString());
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Long id = Long.parseLong(((Button)event.getSource()).getId().substring(4));
                        try {
                            loadRoom(roomB.getRoomById(id));
                        } catch (BusinessException ex) {
                            System.out.println("Erro: " + ex.getMessage());
                        }
                    }
                });
                filhas.add(b);
            }
        } catch (IOException | BusinessException e) {
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
    
    public void loadRoom(Room r) throws BusinessException{
         FlowPane mensagens = (FlowPane)rootLayout.lookup("#mensagensContainer");
         IMessageBusiness busi = new MessageBusiness();
         List<Message> mensagensList = busi.getMessagesByRoom(r);
         ObservableList filhasMensagens = mensagens.getChildren();
         mensagens.setVgap(10);
         filhasMensagens.remove(0, filhasMensagens.size());
         for(Message m:mensagensList){
             Label lMsg = new Label();
             lMsg.setId("mensagem"+m.getIdMessage());
             lMsg.setText(m.getTextMessage());
             lMsg.setAlignment(Pos.BOTTOM_LEFT);
             lMsg.setMinWidth(mensagens.getWidth());
             if(m.getUser().getIpUser()==logado.getIpUser() && m.getUser().getNameUser().equals(logado.getNameUser())){
                 lMsg.setStyle("-fx-background-color: #e0e0e0");
                 lMsg.setTextFill(Paint.valueOf("black")); 
             }else{
                 lMsg.setStyle("-fx-background-color: #42e8f4");
                 lMsg.setTextFill(Paint.valueOf("white")); 
             }
             Label lUsuario = new Label();
             if(m.getUser().getIpUser()==logado.getIpUser() && m.getUser().getNameUser().equals(logado.getNameUser())){
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
         }
         FlowPane logados = (FlowPane)rootLayout.lookup("#logadosContainer");
         ObservableList filhasLogados = logados.getChildren();
         logados.setVgap(15);
         filhasLogados.remove(0, filhasMensagens.size());
         for(User u : r.getUsuarios()){
             if(u.getIpUser()!=logado.getIpUser() && !u.getNameUser().equals(logado.getNameUser())){
                 Label l = new Label();
                 l.setId("usLog-"+u.getIpUser()+"-"+u.getNameUser());
                 l.setText(u.getNameUser());
                 l.setMinWidth(logados.getWidth());
                 l.setStyle("-fx-background-color: #660000");
                 filhasLogados.add(l);
             }
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
