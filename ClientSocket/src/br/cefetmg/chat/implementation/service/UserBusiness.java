package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.ConnectionManager;
import br.cefetmg.chat.interfaces.connection.IConnection;

public class UserBusiness implements IUserBusiness{
    private IConnection c;
    
    public UserBusiness(){
        try {
            c = ConnectionManager.getInstance().getConnection();
        } catch (ConnectionException ex) {
            throw new RuntimeException(ex.getMessage());
        }
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
        try{
            c.sendDados("User-Insert");
            c.sendDados(u);
            u = (User) c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return u;
    }

    @Override
    public User getUserById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        User u = null;
        try{
            c.sendDados("User-Get");
            c.sendDados(id);
            u = (User) c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return u;
    }

    @Override
    public User deleteUserById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        User u = null;
        try{
            c.sendDados("User-Delete");
            c.sendDados(id);
            u = (User) c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return u;
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
        try{
            c.sendDados("User-Update");
            c.sendDados(id);
            c.sendDados(u);
            u = (User) c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return u;
    }  
}
