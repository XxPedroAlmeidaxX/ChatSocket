package br.cefetmg.chat.interfaces.connection;

import br.cefetmg.chat.exception.ConnectionException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IConnection {
    public void disconnect() throws ConnectionException;
    public void sendData(String json) throws ConnectionException;
    public String receiveData(String idt) throws ConnectionException;
    public Object readData() throws ConnectionException;
    public void writeData(Object o) throws ConnectionException;
    public Long getIp();
}