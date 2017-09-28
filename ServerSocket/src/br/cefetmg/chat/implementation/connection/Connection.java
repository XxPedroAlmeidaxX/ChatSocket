package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;

public class Connection implements IConnection{
    
    private Socket p;
    private static ServerSocket s;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public Connection() throws ConnectionException {
        try {
            p = s.accept();
            out = new ObjectOutputStream(p.getOutputStream());
            in = new ObjectInputStream (p.getInputStream());
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Cliente: " + ex);
        }
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            p.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Cliente: " + ex);
        }          
    }

    @Override
    public void send(Object obj) throws ConnectionException {
        try {            
            out.writeObject(obj);
            out.flush();
        }
        catch (IOException ex) {
            throw new ConnectionException("\nErro ao enviar para o Cliente: " + ex);
        }
    }

    @Override
    public Object receive() throws ConnectionException {
        try {
            return in.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException("\nErro ao receber do Cliente: " + ex);
        } 
    }
    
    public static void setServer(int port) throws ConnectionException {
        try {
            s = new ServerSocket(port);
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao definir Socket do servidor: " + ex);
        }
    }
}
