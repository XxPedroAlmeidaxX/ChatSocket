package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class Connection implements IConnection{ 
    //Canal para troca de dados entre o cliente e o servidor
    private Socket canal;
    //Socket unico do servidor
    private static ServerSocket server;
    //Canal de saida de dados
    private ObjectOutputStream outData;
    //Canal de entrada de dados
    private ObjectInputStream inData; 
    //ArrayList para buffer
    private ArrayList<String> bufferData;
    
    private final ReentrantLock lockSend = new ReentrantLock();
    private final ReentrantLock lockReceive = new ReentrantLock();
    
    public Connection() throws ConnectionException {
        try {
            canal = server.accept();
            outData = new ObjectOutputStream(canal.getOutputStream());
            inData = new ObjectInputStream(canal.getInputStream());
            bufferData = new ArrayList<>();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Cliente: " + ex);
        }
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            canal.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Cliente: " + ex);
        }          
    }

    @Override
    public void sendData(String json, String idt) throws ConnectionException {
        System.out.println("Inicio envio dados");
        json = idt + json;
        lockSend.lock();
        try{
            writeData(json);
        }finally{
            lockSend.unlock();
        }
        System.out.println("Enviou dados: " + json);
    }

    @Override
    public String receiveData() throws ConnectionException {
        System.out.println("Inicio dados");
        String data;
        lockReceive.lock();
        try{
            if(!bufferData.isEmpty()){
                return bufferData.remove(0);
            }
            data = (String) readData();
            System.out.println("Recebeu dados: " + data.substring(1) );
            if(data.charAt(0)=='D'){
                return data.substring(1);
            }else{
                return this.receiveData();
            }
        }finally{
            lockReceive.unlock();
        }
    }
    
    public static void setServer(int port) throws ConnectionException {
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao definir Socket do servidor: " + ex);
        }
    }      
    
    @Override
    public Object readData() throws ConnectionException {
        try {
            return inData.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionException(ex.getMessage());
        }
    }

    @Override
    public void writeData(Object o) throws ConnectionException {
        try {
            outData.writeObject(o);
            outData.flush();
        } catch (IOException ex) {
            throw new ConnectionException(ex.getMessage());
        }
    }
}
