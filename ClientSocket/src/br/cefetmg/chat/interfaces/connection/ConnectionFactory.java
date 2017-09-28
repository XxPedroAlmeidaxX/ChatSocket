package br.cefetmg.chat.interfaces.connection;

import br.cefetmg.chat.exception.ConnectionException;

public interface ConnectionFactory {
    public IConnection getConnection() throws ConnectionException;
}
