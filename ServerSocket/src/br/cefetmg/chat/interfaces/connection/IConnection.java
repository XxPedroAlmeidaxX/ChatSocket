package br.cefetmg.chat.interfaces.connection;

import br.cefetmg.chat.exception.ConnectionException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IConnection {
    public void disconnect() throws ConnectionException;
    public void sendData(String json, String idt) throws ConnectionException;
    public String receiveData() throws ConnectionException;
    public Object readData() throws ConnectionException;
    public void writeData(Object o) throws ConnectionException;
}
