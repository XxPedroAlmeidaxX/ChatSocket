package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.interfaces.dao.IMessageDAO;
import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import br.cefetmg.chat.implementation.dao.MessageDAO;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class MessageBusiness implements IMessageBusiness{
    private final UpdateSender up;
    private final IMessageDAO dao;
    
    public MessageBusiness(){
       up = new UpdateSender();
       dao = new MessageDAO();
    }
    
    @Override
    public Message insertMessage(Message m) throws BusinessException{
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
        try {
            up.receiveMessage(m);
            return dao.insertMessage(m);
        } catch (PersistenceException | RemoteException ex) {
            throw new BusinessException(ex.getMessage());
        }
        
    }

    @Override
    public Message getMessageById(Long id) throws BusinessException{
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        try {
            return dao.getMessageById(id);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
        
    }

    @Override
    public Message deleteMessageById(Long id) throws BusinessException{
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        try {
            return dao.deleteMessageById(id);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
        
    }

    @Override
    public Message updateMessageById(Long id, Message m) throws BusinessException{
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
        try {
            return dao.updateMessageById(id, m);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
        
    }

    @Override
    public ArrayList<Message> getMessagesByUser(User u) throws BusinessException{
        if(u==null){
            throw new BusinessException("Usuário não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        try {
            return dao.getMessagesByUser(u);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public ArrayList<Message> getMessagesByRoom(Room r) throws BusinessException{
        if(r==null){
            throw new BusinessException("Sala não pode ser nula");
        }
        if(r.getIdRoom()==null){
            throw new BusinessException("Id da sala não pode ser nulo");
        }
        try {
            return dao.getMessagesByRoom(r);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }  
}
