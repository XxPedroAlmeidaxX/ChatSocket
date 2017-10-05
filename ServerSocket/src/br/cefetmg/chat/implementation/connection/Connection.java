package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.util.HashMap;

public class Connection implements IConnection{
    
    private Socket pDados;
    private Socket pMensagens;
    private HashMap<Long, Socket> mapasMsg;
    private static ServerSocket s;
    private ObjectOutputStream outDados;
    private ObjectInputStream inDados;
    private ObjectOutputStream outMensagens;
    private ObjectInputStream inMensagens;
    
    public Connection() throws ConnectionException {
        try {
            pDados = s.accept();
            System.out.println("Aceita conexão de dados: " + pDados.toString());
            outDados = new ObjectOutputStream(pDados.getOutputStream());
            inDados = new ObjectInputStream (pDados.getInputStream());
            pMensagens = s.accept();
            Long ipCliente = new Long(0);
            for (byte b: pMensagens.getInetAddress().getAddress()){  
                ipCliente = ipCliente << 8 | (b & 0xFF);  
            }
            mapasMsg.put(ipCliente, pMensagens);
            System.out.println("Aceita conexão de mensagens: " + pMensagens.toString());
            outMensagens = new ObjectOutputStream(pMensagens.getOutputStream());
            inMensagens = new ObjectInputStream (pMensagens.getInputStream());
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Cliente: " + ex);
        }
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            pDados.close();
            pMensagens.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Cliente: " + ex);
        }          
    }

    @Override
    public void sendDados(Object obj) throws ConnectionException {
        try {            
            outDados.writeObject(obj);
            outDados.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Cliente: " + ex);
        }
    }

    @Override
    public Object receiveDados() throws ConnectionException {
        try {
            return inDados.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Cliente: " + ex);
        } 
    }
    
    @Override
    public void sendMensagens(Object obj) throws ConnectionException {
        try {            
            outMensagens.writeObject(obj);
            outMensagens.flush();
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

    @Override
    public Long getIp() {
        Long result = new Long(0);
        for (byte b: pDados.getInetAddress().getAddress()){  
            result = result << 8 | (b & 0xFF);  
        }
        return result;
    }
}
