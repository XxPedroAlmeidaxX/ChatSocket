package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IMessageBusiness;
import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.util.ArrayList;

public class MessageBusiness implements IMessageBusiness{
    private IConnection c;
    
    public MessageBusiness(Connection c){
        this.c = c;
    }
    
    @Override
    public Message insertMessage(Message m) throws BusinessException {
        if(m==null){
            throw new BusinessException("Mensagem não pode ser nula");
        }
        if(m.getRoom()==null){
            throw new BusinessException("Sala da mensagem não pode ser nula");
        }
        if(m.getTargetMessage()==null){
            throw new BusinessException("Alvo da mensagem não pode ser nula");
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
            c.sendData("Message-Insert");
            c.sendData(m);
            m = (Message) c.receiveData();
        } catch (ConnectionException ex) {
            throw new BusinessException(ex.getMessage());
        }
        return m;
    }

    @Override
    public Message getMessageById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        Message m = null;
        try{
            c.sendData("Message-Get");
            c.sendData(id);
            m = (Message) c.receiveData();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return m;
    }

    @Override
    public Message deleteMessageById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        Message m = null;
        try{
            c.sendData("Message-Delete");
            c.sendData(id);
            m = (Message) c.receiveData();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return m;
    }

    @Override
    public Message updateMessageById(Long id, Message m) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        if(m==null){
            throw new BusinessException("Mensagem não pode ser nula");
        }
        if(m.getRoom()==null){
            throw new BusinessException("Sala da mensagem não pode ser nula");
        }
        if(m.getTargetMessage()==null){
            throw new BusinessException("Alvo da mensagem não pode ser nula");
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
        try{
            c.sendData("Message-Update");
            c.sendData(id);
            c.sendData(m);
            m = (Message) c.receiveData();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return m;
    }

    @Override
    public ArrayList<Message> getMessagesByUser(User u) throws BusinessException {
        if(u==null){
            throw new BusinessException("Usuário não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        ArrayList<Message> m = null;
        try{
            c.sendData("Message-ByUser");
            c.sendData(u);
            m = (ArrayList<Message>) c.receiveData();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return m;
    }

    @Override
    public ArrayList<Message> getMessagesByRoom(Room r) throws BusinessException {
        if(r==null){
            throw new BusinessException("Sala não pode ser nula");
        }
        if(r.getIdRoom()==null){
            throw new BusinessException("Id da sala não pode ser nulo");
        }
        ArrayList<Message> m = null;
        try{
            c.sendData("Message-ByRoom");
            c.sendData(r);
            m = (ArrayList<Message>) c.receiveData();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return m;
    }  
}
