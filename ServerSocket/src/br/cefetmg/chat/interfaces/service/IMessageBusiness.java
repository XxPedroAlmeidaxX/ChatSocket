package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.List;

public interface IMessageBusiness {
    public Message insertMessage(Message m) throws BusinessException, PersistenceException;
    public Message getMessageById(Long id) throws BusinessException, PersistenceException;
    public Message deleteMessageById(Long id) throws BusinessException, PersistenceException;
    public Message updateMessageById(Long id, Message m) throws BusinessException, PersistenceException;
    public List<Message> getMessagesByUser(User u) throws BusinessException, PersistenceException;
    public List<Message> getMessagesByRoom(Room r) throws BusinessException, PersistenceException;
}
