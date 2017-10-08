package br.cefetmg.chat.interfaces.connection;

import br.cefetmg.chat.exception.ConnectionException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IConnection {
    public void disconnect() throws ConnectionException;
    public void sendData(String json) throws ConnectionException;
    public String receiveData() throws ConnectionException;
    public String receiveUpdates() throws ConnectionException;
    public Long getIp();
}