package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import com.google.gson.Gson;

public class Connection implements IConnection{ 
    //Canal para troca de dados entre o cliente e o servidor
    private Socket pData;
    //Canal para atualização das mensagens do cliente
    //São dois canais para evitar conflitos
    private Socket pUpdate;
    //Socket unico do servidor
    private static ServerSocket sData;
    private static ServerSocket sUpdate;
    //Canal de saida de dados
    private ObjectOutputStream outData;
    //Canal de saida de updates
    private ObjectOutputStream update;
    //Canal de entrada de dados
    private ObjectInputStream inData;
    //Objeto para fazer a conversão dos dados para Json
    private Gson gson;
    //Objeto para receber a String em Json
    private String json;    
    
    public Connection() throws ConnectionException {
        try {
            pData = sData.accept();
            outData = new ObjectOutputStream(pData.getOutputStream());
            inData = new ObjectInputStream(pData.getInputStream());
            pUpdate = sUpdate.accept();
            update = new ObjectOutputStream(pUpdate.getOutputStream());
            gson = new Gson();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Cliente: " + ex);
        }
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            pData.close();
            pUpdate.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Cliente: " + ex);
        }          
    }

    @Override
    public void sendData(Object obj) throws ConnectionException {
        try {    
            json = gson.toJson(obj);
            outData.writeObject(json);
            outData.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Cliente: " + ex);
        }
    }

    @Override
    public Object receiveData() throws ConnectionException {
        try {
            json = (String) inData.readObject();
            return gson.fromJson(json, Object.class);  
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Cliente: " + ex);
        } 
    }
    
    @Override
    public void update(Object obj) throws ConnectionException {
        try {            
            json = gson.toJson(obj);
            update.writeObject(json);
            update.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Cliente: " + ex);
        }
    }
    
    public static void setServer(int port) throws ConnectionException {
        try {
            sData = new ServerSocket(port);
            sUpdate = new ServerSocket(port+1);
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao definir Socket do servidor: " + ex);
        }
    }

    public Socket getpData() {
        return pData;
    }

    public ObjectOutputStream getUpdate() {
        return update;
    }      
}
