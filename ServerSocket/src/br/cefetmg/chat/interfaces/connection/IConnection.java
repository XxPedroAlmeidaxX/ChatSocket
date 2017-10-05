package br.cefetmg.chat.interfaces.connection;

import br.cefetmg.chat.exception.ConnectionException;

public interface IConnection {
    public void disconnect() throws ConnectionException;
    public void sendDados(Object obj) throws ConnectionException;
    public Object receiveDados() throws ConnectionException;
    public void sendMensagens(Object obj) throws ConnectionException;
    public Long getIp();
}
