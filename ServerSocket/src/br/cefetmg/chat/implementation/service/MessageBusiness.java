package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.interfaces.dao.IMessageDAO;
import br.cefetmg.chat.implementation.dao.MessageDAO;
import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class MessageBusiness implements IMessageBusiness{
    private final IMessageDAO dao;
    
    public MessageBusiness(MessageDAO dao){
       this.dao=dao;
    }
    
    @Override
    public Message insertMessage(Message m) throws BusinessException, PersistenceException {
        if(m==null){
            throw new BusinessException("Mensagem não pode ser nula");
        }
        if(m.getRoom()==null){
            throw new BusinessException("Sala da mensagem não pode ser nula");
        }
        if(m.getStateMessage()==null){
            throw new BusinessException("Estado da mensagem não pode ser nula");
        }
        if(m.getTextMessage()==null){
            throw new BusinessException("Texto da mensagem não pode ser nula");
        }
        if(m.getUser()==null){
            throw new BusinessException("Usuario da mensagem não pode ser nula");
        }
        return dao.insertMessage(m);
    }

    @Override
    public Message getMessageById(Long id) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.getMessageById(id);
    }

    @Override
    public Message deleteMessageById(Long id) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.deleteMessageById(id);
    }

    @Override
    public Message updateMessageById(Long id, Message m) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        if(m==null){
            throw new BusinessException("Mensagem não pode ser nula");
        }
        if(m.getRoom()==null){
            throw new BusinessException("Sala da mensagem não pode ser nula");
        }
        if(m.getStateMessage()==null){
            throw new BusinessException("Estado da mensagem não pode ser nula");
        }
        if(m.getTextMessage()==null){
            throw new BusinessException("Texto da mensagem não pode ser nula");
        }
        if(m.getUser()==null){
            throw new BusinessException("Usuario da mensagem não pode ser nula");
        }
        return dao.updateMessageById(id, m);
    }

    @Override
    public ArrayList<Message> getMessagesByUser(User u) throws BusinessException, PersistenceException {
        if(u==null){
            throw new BusinessException("Usuário não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        return dao.getMessagesByUser(u);
    }

    @Override
    public ArrayList<Message> getMessagesByRoom(Room r) throws BusinessException, PersistenceException {
        if(r==null){
            throw new BusinessException("Sala não pode ser nula");
        }
        if(r.getIdRoom()==null){
            throw new BusinessException("Id da sala não pode ser nulo");
        }
        return dao.getMessagesByRoom(r);
    }  
}
