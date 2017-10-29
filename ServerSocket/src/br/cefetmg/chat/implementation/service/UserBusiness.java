package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import br.cefetmg.chat.implementation.dao.UserDAO;
import br.cefetmg.chat.interfaces.dao.IUserDAO;
import br.cefetmg.chat.interfaces.service.IUpdateReceiver;
import br.cefetmg.chat.server.Server;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class UserBusiness extends UnicastRemoteObject implements IUserBusiness, Serializable{
    private final UpdateSender up;
    private final IUserDAO dao;
    
    public UserBusiness() throws RemoteException{
       super();
       up = new UpdateSender();
       dao = new UserDAO();
    }
    
    @Override
    public User insertUser(User u) throws BusinessException {
        if(u==null){
            throw new BusinessException("Usuario não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(u.getNameUser()==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        try {
            return dao.insertUser(u);
        }catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        try {
            return dao.getUserById(id);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public User deleteUserById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        try {
            return dao.deleteUserById(id);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public User updateUserById(Long id, User u) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        if(u==null){
            throw new BusinessException("Usuario não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(u.getNameUser()==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        try {
            return dao.updateUserById(id, u);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }  

    @Override
    public User getUserByIpAndName(Long ip, String name) throws BusinessException {
        if(ip==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(name==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        try {
            return dao.getUserByIpAndName(ip, name);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public User logarUser(String user, Long ip, IUpdateReceiver update) throws BusinessException {
        User cliente;
        System.out.println("logar");
        if(getUserByIpAndName(ip, user).getIdUser()==null){
            //Senão existir cria um novo usuário
            User us = new User();
            us.setIpUser(ip);
            us.setNameUser(user);
            insertUser(us);
        }
        cliente = getUserByIpAndName(ip, user);
        Server.connected.put(cliente.getIdUser(), update);
        try {
            up.receiveUpdate("usuario", new RoomBusiness());
        } catch (RemoteException ex) {
            throw new BusinessException(ex.getMessage());
        }
        return cliente;
    }
}
