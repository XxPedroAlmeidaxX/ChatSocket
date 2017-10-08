package br.cefetmg.chat.util.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface ConnectionFactory {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
}
