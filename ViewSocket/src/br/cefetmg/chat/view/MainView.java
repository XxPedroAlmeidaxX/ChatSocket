package br.cefetmg.chat.view;

import br.cefetmg.chat.controller.landPageController;
import br.cefetmg.chat.controller.HomeController;
import br.cefetmg.chat.controller.RoomMakerController;
import br.cefetmg.chat.controller.NewMessagesThread;
import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.implementation.service.UserBusiness;
import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class MainView extends Application {
    //Stage da view
    private Stage primaryStage;
    //Painel atual carregado na view
    private Pane rootLayout;
    //Objeto do usuário logado atualmente
    private User logado;
    //Lista com todas as salas disponíveis atualmente
    private ArrayList<Room> salas;
    //Conexão do cliente com o servidor
    public Connection conn;
    //Sala atual do usuário
    private Room currentRoom = null;
    //Alvo atual selecionado para mensagens
    private User alvoSelec;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chat");
        try {
            //Conecta ao servidor
            conn = new Connection("localhost", 2223);
            //Inicia thread que verifica alterações de salas, usuários e mensagens
            new Thread(new NewMessagesThread(conn, this)).start();
        } catch (ConnectionException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        //Exibe a tela de login
        showLogin();
    }
    
    public void showHome(){
        try {
            IRoomBusiness roomB = new RoomBusiness(conn);
            //Carrega todas as salas existentes
            salas = roomB.getAllRoom();
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("Home.fxml"));
            rootLayout = (VBox) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            //Define a variável main view do controller
            HomeController controller = loader.getController();
            controller.setMainView(this);
            
            //Obtem o painel de salasDisponiveis
            FlowPane salasDisponiveis = (FlowPane)rootLayout.lookup("#paneAddRooms");
            salasDisponiveis.setVgap(15);
            //Pega a lista de filhas do painel
            ObservableList filhas = salasDisponiveis.getChildren();
            //Remove salas já carregadas
            if(!filhas.isEmpty())
                filhas.remove(0, filhas.size());
            //Para cada sala adiciona um botão
            for(Room r:salas){
                Button b = new Button();
                b.setText(r.getNameRoom()+"\n"+r.getUsuarios().size()+" usuario(s)");
                b.setMinWidth(salasDisponiveis.getWidth());
                b.setTextFill(Paint.valueOf("white")); 
                b.setStyle("-fx-background-color: #660000");
                b.setId("sala"+r.getIdRoom().toString());
                //Define o evento de clique no botão
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //Ao trocar de sala, define o alvo de mensagens como nulo
                        alvoSelec = null;
                        //Obtem o id da sala
                        Long id = Long.parseLong(((Button)event.getSource()).getId().substring(4));
                        try{
                            //Obtem a sala
                            Room r = roomB.getRoomById(id);
                            //Se a sala for privada
                            if(r.getStateRoom()){
                                //Exibe popup para digitar a senha
                                final Stage dialog = new Stage();
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.initOwner(primaryStage);
                                VBox dialogVbox = new VBox(20);
                                dialogVbox.setAlignment(Pos.CENTER);
                                Label l = new Label("\nSenha");
                                dialogVbox.getChildren().add(l);
                                TextField t = new TextField("");
                                t.setId("senhaField");
                                dialogVbox.getChildren().add(t);
                                Button entrar = new Button("Entrar");
                                entrar.setTextFill(Paint.valueOf("white")); 
                                entrar.setStyle("-fx-background-color: #660000");
                                //Evento de clicar no botão de entrar do popup
                                entrar.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        System.out.println(((TextField)dialogVbox.lookup("#senhaField")).getText());
                                        //Se a senha estiver correta
                                        if(r.getPassword().trim().equals(((TextField)dialogVbox.lookup("#senhaField")).getText())){
                                            try {
                                                System.out.println("Sala Privada");
                                                //Carrega a sala
                                                loadRoom(r);
                                                dialog.close();
                                            } catch (BusinessException ex) {
                                                System.out.println("Erro: " + ex);
                                            }
                                        }
                                    }
                                });
                                dialogVbox.getChildren().add(entrar);
                                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                dialog.setScene(dialogScene);
                                //Exibe o popup
                                dialog.show();
                            }else{
                                //Carrega a sala
                                loadRoom(r);
                            }
                            
                        } catch (BusinessException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                //Adiciona o botão às filhas do painel
                filhas.add(b);
            }
        } catch (IOException | BusinessException e) {
            throw new RuntimeException(e);
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
            
            //Define o MainView do controller
            landPageController controller = loader.getController();
            controller.setMainView(this);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    public void loadRoom(Room r) throws BusinessException{
        IMessageBusiness busi = new MessageBusiness(conn); 
        IRoomBusiness roomB = new RoomBusiness(conn);
        Room oldCurrent;
        //Se o usuário estava anteriormente em uma sala e a nova sala é diferente
        if(currentRoom!=null){
            if(r.getIdRoom()!=currentRoom.getIdRoom()){
                //Remove o usuário da sala
                roomB.removeUserRoom(logado.getIdUser(), currentRoom.getIdRoom());
            }
        }
        oldCurrent = currentRoom;
        //Define a sala atual
        currentRoom = r;
        //Se o usuário estava anteriormente em uma sala e a nova sala é diferente
        if(oldCurrent!=null){
            if(r.getIdRoom()!=oldCurrent.getIdRoom()){
                //Insere o usuário da sala
                roomB.insertUserRoom(logado, r.getIdRoom());
            }
        }else{
            roomB.insertUserRoom(logado, r.getIdRoom());
        }
        //Obtem o painel de mensagens
        FlowPane mensagens = (FlowPane)rootLayout.lookup("#mensagensContainer");
        ArrayList<Message> mensagensList = busi.getMessagesByRoom(r);
        ObservableList filhasMensagens = mensagens.getChildren();
        mensagens.setVgap(10);
        //Deleta as mensagens carregadas anteriormente
        if(!filhasMensagens.isEmpty())
            filhasMensagens.remove(0, filhasMensagens.size());
        //Para cada mensagem da sala
        for(Message m:mensagensList){
            //Se a mensagem não for privada, o usuário logado for o alvo da mensagem privada ou o usuário for o remetente
            if(!m.getStateMessage() || m.getTargetMessage().getIdUser()==logado.getIdUser() || m.getUser().getIdUser()==logado.getIdUser()){
                FlowPane fl = new FlowPane();
                //Exibe a mensagem
                Label lMsg = new Label();
                lMsg.setId("mensagem"+m.getIdMessage());
                lMsg.setText(m.getTextMessage());
                lMsg.setAlignment(Pos.BOTTOM_LEFT);
                lMsg.setMinWidth(mensagens.getWidth());
                if(m.getUser().getIdUser()==logado.getIdUser()){
                    fl.setStyle("-fx-background-color: #e0e0e0");
                    lMsg.setTextFill(Paint.valueOf("black")); 
                }else{
                    fl.setStyle("-fx-background-color: #42e8f4");
                    lMsg.setTextFill(Paint.valueOf("white")); 
                }
                Label lUsuario = new Label();
                if(m.getUser().getIdUser()==logado.getIdUser()){
                    lUsuario.setTextFill(Paint.valueOf("black")); 
                }else{
                    lUsuario.setTextFill(Paint.valueOf("white")); 
                }
                lUsuario.setAlignment(Pos.BOTTOM_LEFT);
                lUsuario.setMinWidth(mensagens.getWidth());
                lUsuario.setId("usMsg-"+m.getUser().getIdUser());
                lUsuario.setText(m.getUser().getNameUser());
                lUsuario.setFont(Font.font ("System", 10));
                //Adiciona a mensagem
                fl.getChildren().add(lMsg);
                fl.getChildren().add(lUsuario);
                filhasMensagens.add(fl);
            }
        }
        
        //Obtem o painel de usuários logados
        FlowPane logados = (FlowPane)rootLayout.lookup("#logadosContainer");
        ObservableList filhasLogados = logados.getChildren();
        logados.setVgap(15);
        //Remove os usuário logados já carregados
        if(!filhasLogados.isEmpty())
            filhasLogados.remove(0, filhasMensagens.size());
        //Para cada usuário da sala
        for(User u : r.getUsuarios()){
            //Se o usuário não for o usuário logado
            if(u.getIdUser()!=logado.getIdUser()){
                Label l = new Label();
                l.setId("usLog-"+u.getIdUser());
                l.setText(u.getNameUser());
                l.setMinWidth(logados.getWidth());
                l.setStyle("-fx-background-color: #660000");
                l.setTextFill(Paint.valueOf("white")); 
                //Quando clica no usuário
                l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Long id = Long.parseLong(((Label)event.getSource()).getId().substring(6));
                        try{
                            //Se não se tem um alvo para mensagens ou o alvo é diferente do clicado
                            if(alvoSelec==null || !alvoSelec.getIdUser().equals(id)){
                                //Se tiver um alvo selecionado, remove a cor
                                if(alvoSelec!=null){
                                    String idLabel = "usLog-"+alvoSelec.getIdUser();
                                    Label log = (Label)logados.lookup("#" + idLabel);
                                    log.setStyle("-fx-background-color: #660000");
                                }
                                //Torna o usuário clicado alvo
                                UserBusiness usBus = new UserBusiness(conn);
                                alvoSelec = usBus.getUserById(id);
                                Label label = ((Label)event.getSource());
                                label.setStyle("-fx-background-color: #b30000");
                            }else{
                                //Remove alvo, tornando as mensagens não direcionadas
                                alvoSelec=null;
                                Label label = ((Label)event.getSource());
                                label.setStyle("-fx-background-color: #660000");
                            }
                        } catch (BusinessException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                //Adiciona os usuários
                filhasLogados.add(l);
            }
        }
    }

    public void showRoomMaker() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("RoomMaker.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            //Define o mainview do controller
            RoomMakerController rmc = loader.getController();
            rmc.setMainView(this);
            
            //Exibe a tela de criação de salas
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

    public ArrayList<Room> getSalas() {
        return salas;
    }

    public void setSalas(ArrayList<Room> salas) {
        this.salas = salas;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public User getAlvoSelec() {
        return alvoSelec;
    }

    public void setAlvoSelec(User alvoSelec) {
        this.alvoSelec = alvoSelec;
    }
    
}
