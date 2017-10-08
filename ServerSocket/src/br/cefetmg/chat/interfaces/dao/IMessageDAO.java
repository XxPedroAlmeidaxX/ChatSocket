package br.cefetmg.chat.interfaces.dao;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IMessageDAO {
    public Message insertMessage(Message m) throws PersistenceException;
    public Message getMessageById(Long id) throws PersistenceException;
    public Message deleteMessageById(Long id) throws PersistenceException;
    public Message updateMessageById(Long id, Message m) throws PersistenceException;
    public ArrayList<Message> getMessagesByUser(User u) throws PersistenceException;
    public ArrayList<Message> getMessagesByRoom(Room r) throws PersistenceException;
}
