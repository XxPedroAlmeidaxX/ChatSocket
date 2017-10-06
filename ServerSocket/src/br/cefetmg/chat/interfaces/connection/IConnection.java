package br.cefetmg.chat.interfaces.connection;

import br.cefetmg.chat.exception.ConnectionException;

public interface IConnection {
    public void disconnect() throws ConnectionException;
    public void sendData(Object obj) throws ConnectionException;
    public Object receiveData() throws ConnectionException;
    public void update(Object obj) throws ConnectionException;
}
