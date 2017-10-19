package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IMessageBusiness extends Remote{
    public Message insertMessage(Message m) throws BusinessException, RemoteException;
    public Message getMessageById(Long id) throws BusinessException, RemoteException;
    public Message deleteMessageById(Long id) throws BusinessException, RemoteException;
    public Message updateMessageById(Long id, Message m) throws BusinessException, RemoteException;
    public ArrayList<Message> getMessagesByUser(User u) throws BusinessException, RemoteException;
    public ArrayList<Message> getMessagesByRoom(Room r) throws BusinessException, RemoteException;
}
