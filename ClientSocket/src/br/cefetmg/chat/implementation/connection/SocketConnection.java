/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.interfaces.connection.ConnectionFactory;
import br.cefetmg.chat.interfaces.connection.IConnection;

/**
 *
 * @author aluno
 */
public class SocketConnection implements ConnectionFactory{
    
    private Connection c;
    
    public SocketConnection(){
        try {
            c = new Connection("localhost", 2223);
        } catch (ConnectionException ex) {
            throw new RuntimeException("Erro ao criar conexão: " + ex.getMessage());
        }
    }
    
    @Override
    public IConnection getConnection() throws ConnectionException {
        return c;
    }
    
}
