package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.util.gson.Handler;
import java.util.ArrayList;

/**
 * 
 * @author Pedro Almeida
 */

public class ConnectionTest {
    public static void  main(String args[]) {
        //Declaração de variáveis teste
        User tUser1 = new User(Long.parseLong("1"), "tuser1", Long.parseLong("1"));
        User tUser2 = new User(Long.parseLong("2"), "tuser2", Long.parseLong("2"));
        User tUser3 = new User(Long.parseLong("3"), "tuser3", Long.parseLong("3"));
        ArrayList<User> tUserArray = new ArrayList();
        tUserArray.add(tUser1);tUserArray.add(tUser2);tUserArray.add(tUser3);
        Room tRoom = new Room();
        //Realização dos testes
        try {
            Connection con = new Connection("localhost", 2223);
            String a1 = con.receiveData("D");
            System.out.println(a1);
            String a2 = con.receiveData("D");
            System.out.println(a2);
            String a3 = con.receiveData("U");
            System.out.println(a3);
            
        } catch(ConnectionException ex) {
            System.out.println("Erro: " + ex);
        } 
    }
}
