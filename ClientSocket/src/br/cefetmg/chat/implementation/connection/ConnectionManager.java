package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.*;

public class ConnectionManager {
  
    private static ConnectionManager conexao;
    private static ConnectionFactory cf;

    private ConnectionManager() throws ConnectionException {
         ConnectionManager.cf = new SocketConnection();
    }

    public static ConnectionManager getInstance() throws ConnectionException {
        if(conexao == null)
            conexao = new ConnectionManager();
        return conexao;
    }

    public IConnection getConnection() throws ConnectionException {
        return ConnectionManager.cf.getConnection();
    }
}