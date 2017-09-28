package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import java.util.List;

public interface IMessageBusiness {
    public Message insertMessage(Message m) throws BusinessException;
    public Message getMessageById(Long id) throws BusinessException;
    public Message deleteMessageById(Long id) throws BusinessException;
    public Message updateMessageById(Long id, Message m) throws BusinessException;
    public List<Message> getMessagesByUser(User u) throws BusinessException;
    public List<Message> getMessagesByRoom(Room r) throws BusinessException;
}
