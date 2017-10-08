package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;

public class Connection implements IConnection{
    
    //Canal para troca de dados entre o cliente e o servidor
    private Socket pData;
    //Canal para atualização das mensagens do cliente
    //São dois canais para evitar conflitos
    private Socket pUpdate;
    //Socket unico do servidor
    private static ServerSocket s;
    //Canal de saida de dados
    private ObjectOutputStream outData;
    //Canal de saida de updates
    private ObjectOutputStream update;
    //Canal de entrada de dados
    private ObjectInputStream inData;
    
    public Connection() throws ConnectionException {
        try {
            pData = s.accept();
            outData = new ObjectOutputStream(pData.getOutputStream());
            inData = new ObjectInputStream(pData.getInputStream());
            pUpdate = s.accept();
            update = new ObjectOutputStream(pUpdate.getOutputStream());
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
            outData.writeObject(obj);
            outData.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Cliente: " + ex);
        }
    }

    @Override
    public Object receiveData() throws ConnectionException {
        try {
            Object d = inData.readObject();
            return d;
            
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Cliente: " + ex);
        } 
    }
    
    @Override
    public void update(Object obj) throws ConnectionException {
        try {            
            update.writeObject(obj);
            update.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Cliente: " + ex);
        }
    }
    
    public static void setServer(int port) throws ConnectionException {
        try {
            s = new ServerSocket(port);
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
