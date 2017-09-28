package br.cefetmg.chat.interfaces.connection;

public interface Connection {
    public void disconnect();
    public void send(Object obj);
    public Object receive();
}
