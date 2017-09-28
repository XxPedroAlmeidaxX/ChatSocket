package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
  
    private static ConnectionManager conexao;
    private static ConnectionFactory cf;

    private ConnectionManager() {
         ConnectionManager.cf = new SocketConnection();
    }

    public static ConnectionManager getInstance() {
        if(conexao == null)
            conexao = new ConnectionManager();
        return conexao;
    }

    public IConnection getConnection() throws ConnectionException {
        return ConnectionManager.cf.getConnection();
    }
}