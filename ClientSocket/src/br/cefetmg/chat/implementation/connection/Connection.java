package br.cefetmg.chat.implementation.connection;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class Connection implements IConnection{
    //Canal para troca de dados entre o cliente e o servidor
    private Socket canal;
    //Canal de saída de dados do cliente
    private ObjectOutputStream outData;
    //Canal de entrada de dados do cliente
    private ObjectInputStream inData;
    //ArrayLists para guardar mensagens bufferizadas
    private ArrayList<String> bufferData;
    private ArrayList<String> bufferUpdate;
    
    private final ReentrantLock lockSend = new ReentrantLock(true);
    private final ReentrantLock lockReceive = new ReentrantLock(true);
    
    public Connection(String ip, int porta) throws ConnectionException {
        try {
            canal = new Socket(ip, porta);  
            outData = new ObjectOutputStream(canal.getOutputStream());
            inData = new ObjectInputStream (canal.getInputStream());
            bufferData = new ArrayList<>();
            bufferUpdate = new ArrayList<>();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao criar conexão com o Servidor: " + ex);
        }    
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            canal.close();
        } catch (IOException ex) {
            throw new ConnectionException("\nErro ao desconectar do Servidor: " + ex);
        }          
    }

    @Override
    public void sendData(String json) throws ConnectionException {
        System.out.println("Inicio envio");
        json = "D" + json;
        lockSend.lock();
        try{
            writeData(json);
        }finally{
            lockSend.unlock();
        }
        System.out.println("Enviou dados: " + json);
    }
    
    @Override
    public String receiveData(String idt) throws ConnectionException {
        String data;
        System.out.println("Lock - " + idt);
        lockReceive.lock();
        System.out.println("Pass lock - " + idt);
        if(idt.equals("D")){
            if(!bufferData.isEmpty()){
                System.out.println("Buffer data returned");
                return bufferData.remove(0);
            }
        }else{
            if(!bufferUpdate.isEmpty()){
                return bufferUpdate.remove(0);
            }
        }
        System.out.println("Esperando " + idt);
        data = (String) readData();
        if("D".equals(idt)){
            System.out.println("Recebeu: D - " + data);
            if(data.charAt(0)=='D'){
                do{
                    lockReceive.unlock();
                }while(lockReceive.getHoldCount()>0);
                return data.substring(1);
            }else{
                bufferUpdate.add(data.substring(1));
                do{
                    lockReceive.unlock();
                }while(lockReceive.getHoldCount()>0);
                return this.receiveData(idt);
            }
        }else{
            System.out.println("Recebeu: U - " + data.substring(1));
            if(data.charAt(0)=='U'){
                do{
                    lockReceive.unlock();
                }while(lockReceive.getHoldCount()>0);
                return data.substring(1);
            }else{
                bufferData.add(data.substring(1));
                do{
                    lockReceive.unlock();
                }while(lockReceive.getHoldCount()>0);
                return this.receiveData(idt);
            }
        }
    }

    @Override
    public Long getIp() {
        Long result = new Long(0);
        for (byte b:((InetSocketAddress)canal.getRemoteSocketAddress()).getAddress().getAddress()){  
            result = result << 8 | (b & 0xFF);  
        }
        return result;
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
