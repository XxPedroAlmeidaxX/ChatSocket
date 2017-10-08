package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.net.InetSocketAddress;

public class Connection implements IConnection{
    //Canal para troca de dados entre o cliente e o servidor
    private Socket pDados;
    //Canal para atualização das mensagens do cliente
    //São dois canais para evitar conflitos
    private Socket pUpdate;
    //Canal de saída de dados do cliente
    private ObjectOutputStream outDados;
    //Canal de entrada de dados do cliente
    private ObjectInputStream inDados;
    //Canal de entrada de dados de atualização
    private ObjectInputStream update;
    
    public Connection(String ip, int porta) throws ConnectionException {
        try {
            pDados = new Socket(ip, porta);  
            outDados = new ObjectOutputStream(pDados.getOutputStream());
            inDados = new ObjectInputStream (pDados.getInputStream());
            pUpdate = new Socket(ip, porta);
            update = new ObjectInputStream (pUpdate.getInputStream());
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
            return inDados.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Servidor: " + ex);
        } 
    }
    
    @Override
    public Object receiveUpdates() throws ConnectionException {
        try {
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
