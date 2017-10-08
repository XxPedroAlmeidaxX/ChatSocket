package br.cefetmg.chat.implementation.connection;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.ConnectionException;
import java.util.ArrayList;

public class ConnectionTest {
    public static void  main(String args[]) {
        //Declaração de variáveis teste
        User tUser1 = new User(Long.parseLong("1"), "tuser1", Long.parseLong("1"));
        User tUser2 = new User(Long.parseLong("2"), "tuser2", Long.parseLong("2"));
        User tUser3 = new User(Long.parseLong("3"), "tuser3", Long.parseLong("3"));
        ArrayList<User> tUserArray = new ArrayList();
        tUserArray.add(tUser1);tUserArray.add(tUser2);tUserArray.add(tUser3);
        Room tRoom = new Room(Long.parseLong("1"), "troom", false, "senha", tUserArray);
        //Realização dos testes
        try {
            Connection.setServer(2223);
            Connection con = new Connection();
        } catch(ConnectionException ex) {
            System.out.println("Erro: " + ex);
        } 
    }
}
