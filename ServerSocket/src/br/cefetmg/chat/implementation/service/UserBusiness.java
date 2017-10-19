package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.interfaces.dao.IUserDAO;
import br.cefetmg.chat.implementation.dao.UserDAO;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import br.cefetmg.chat.util.gson.Handler;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class UserBusiness implements IUserBusiness{
    private final IUserDAO dao=null;
    
    public UserBusiness(){
       
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
    public User logarUser(String user, Long ip) throws BusinessException {
        User cliente;
        if(getUserByIpAndName(ip, user).getIdUser()==null){
            //Senão existir cria um novo usuário
            User us = new User();
            us.setIpUser(ip);
            us.setNameUser(user);
            insertUser(us);
        }
        cliente = getUserByIpAndName(ip, user);
        return cliente;
    }
}
