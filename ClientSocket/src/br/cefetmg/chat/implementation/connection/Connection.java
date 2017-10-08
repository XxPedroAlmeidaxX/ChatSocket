package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import com.google.gson.Gson;
import java.net.InetSocketAddress;

public class Connection implements IConnection{
    //Canal para troca de dados entre o cliente e o servidor
    private Socket pData;
    //Canal para atualização das mensagens do cliente
    //São dois canais para evitar conflitos
    private Socket pUpdate;
    //Canal de saída de dados do cliente
    private ObjectOutputStream outData;
    //Canal de entrada de dados do cliente
    private ObjectInputStream inData;
    //Canal de entrada de dados de atualização
    private ObjectInputStream update;
    //Objeto para fazer a conversão dos dados para Json
    private Gson gson;
    //Objeto para receber a String em Json
    private String json;
    
    public Connection(String ip, int porta) throws ConnectionException {
        try {
            pData = new Socket(ip, porta);  
            outData = new ObjectOutputStream(pData.getOutputStream());
            inData = new ObjectInputStream (pData.getInputStream());
            pUpdate = new Socket(ip, porta+1);
            update = new ObjectInputStream (pUpdate.getInputStream());
            gson = new Gson();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Servidor: " + ex);
        }    
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            pData.close();
            pUpdate.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Servidor: " + ex);
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
            throw new ConnectionException("\nErro ao enviar para o Servidor: " + ex);
        }
    }

    @Override
    public Object receiveData() throws ConnectionException {
        try {
            json = (String) inData.readObject();
            return gson.fromJson(json, Object.class);
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Servidor: " + ex);
        } 
    }
    
    @Override
    public Object receiveUpdates() throws ConnectionException {
        try {           
            json = (String) update.readObject();
            return gson.fromJson(json, Object.class); 
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Servidor: " + ex);
        } 
    }

    @Override
    public Long getIp() {
        Long result = new Long(0);
        for (byte b:((InetSocketAddress)pData.getRemoteSocketAddress()).getAddress().getAddress()){  
            result = result << 8 | (b & 0xFF);  
        }
        return result;
    } 
}
