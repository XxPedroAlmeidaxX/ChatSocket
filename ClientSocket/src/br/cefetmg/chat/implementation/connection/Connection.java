package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.net.InetSocketAddress;

public class Connection implements IConnection{
    private Socket pDados;
    private Socket pUpdate;
    private ObjectOutputStream outDados;
    private ObjectInputStream inDados;
    private ObjectInputStream update;
    
    public Connection(String ip, int porta) throws ConnectionException {
        try {
            pDados = new Socket(ip, porta);  
            pUpdate = new Socket(ip, porta);
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Servidor: " + ex);
        }
        
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            pDados.close();
            pUpdate.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Servidor: " + ex);
        }          
    }

    @Override
    public void sendDados(Object obj) throws ConnectionException {
        try {            
            outDados = new ObjectOutputStream(pDados.getOutputStream());
            outDados.writeObject(obj);
            outDados.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Servidor: " + ex);
        }
    }

    @Override
    public Object receiveDados() throws ConnectionException {
        try {
            inDados = new ObjectInputStream (pDados.getInputStream());
            return inDados.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Servidor: " + ex);
        } 
    }
    
    @Override
    public Object receiveUpdates() throws ConnectionException {
        try {
            update = new ObjectInputStream (pUpdate.getInputStream());
            return update.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Servidor: " + ex);
        } 
    }

    @Override
    public Long getIp() {
        Long result = new Long(0);
        for (byte b:((InetSocketAddress)pDados.getRemoteSocketAddress()).getAddress().getAddress()){  
            result = result << 8 | (b & 0xFF);  
        }
        return result;
    }
    
}
