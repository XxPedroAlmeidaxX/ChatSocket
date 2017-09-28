package br.cefetmg.chat.server;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;

public class AdapterServer implements Runnable{
    
    private final Connection con;
    
    public AdapterServer() throws ConnectionException{
        con = new Connection();
    }
    
    @Override
    public void run() {
        
    }
}
