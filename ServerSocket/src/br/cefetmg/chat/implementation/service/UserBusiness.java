package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.interfaces.dao.IUserDAO;
import br.cefetmg.chat.implementation.dao.UserDAO;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;

public class UserBusiness implements IUserBusiness{
    private final IUserDAO dao;
    
    public UserBusiness(UserDAO dao){
        this.dao=dao;
    }
    
    @Override
    public User insertUser(User u) throws BusinessException, PersistenceException {
        if(u==null){
            throw new BusinessException("Usuario não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(u.getNameUser()==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        return dao.insertUser(u);
    }

    @Override
    public User getUserById(Long id) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.getUserById(id);
    }

    @Override
    public User deleteUserById(Long id) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.deleteUserById(id);
    }

    @Override
    public User updateUserById(Long id, User u) throws BusinessException, PersistenceException {
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
        return dao.updateUserById(id, u);
    }  
}
