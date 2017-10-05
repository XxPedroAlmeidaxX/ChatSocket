package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Connection implements IConnection{
    private Socket pDados;
    private Socket pMensagens;
    private ObjectOutputStream outDados;
    private ObjectInputStream inDados;
    private ObjectOutputStream outMensagens;
    private ObjectInputStream inMensagens;
    private ArrayList<Object> msgData;
    private ArrayList<Object> serverResponse;
    
    public Connection(String ip, int porta) throws ConnectionException {
        msgData = new ArrayList<>();
        serverResponse = new ArrayList<>();
        try {
            pDados = new Socket(ip, porta);  
            outDados = new ObjectOutputStream(pDados.getOutputStream());
            inDados = new ObjectInputStream (pDados.getInputStream());
            pMensagens = new Socket(ip, porta);  
            outMensagens = new ObjectOutputStream(pMensagens.getOutputStream());
            inMensagens = new ObjectInputStream (pMensagens.getInputStream());
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Servidor: " + ex);
        }
        new Thread(new NewMessagesThread()).start();
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            pDados.close();
            pMensagens.close();
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
    public Object receiveMensagens() throws ConnectionException {
        try {
            return inMensagens.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Servidor: " + ex);
        } 
    }

    @Override
    public Long getIp() {
        Long result = new Long(0);
        for (byte b: ((InetSocketAddress)pDados.getRemoteSocketAddress()).getAddress().getAddress()){  
            result = result << 8 | (b & 0xFF);  
        }
        return result;
    }
    
}
