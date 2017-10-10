package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IUserBusiness;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.util.gson.Handler;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */
public class UserBusiness implements IUserBusiness{
    private final IConnection c;
    
    public UserBusiness(Connection c){
        this.c = c;
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
            c.sendData("User-Insert");
            c.sendData(Handler.toJson(u));
            u = Handler.toUser(c.receiveData("D"));
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
            c.sendData("User-GetId");
            c.sendData(id.toString());
            u = Handler.toUser(c.receiveData("D"));
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
            c.sendData("User-Delete");
            c.sendData(id.toString());
            u = Handler.toUser(c.receiveData("D"));
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
            c.sendData("User-Update");
            c.sendData(id.toString());
            c.sendData(Handler.toJson(u));
            u = Handler.toUser(c.receiveData("D"));
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return u;
    }  

    @Override
    public User getUserByIpAndName(Long ip, String name) throws BusinessException {
        if(ip==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(name==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        User u = null;
        try{
            c.sendData("User-GetIpName");
            c.sendData(Handler.toJson(ip));
            c.sendData(name);
            u = Handler.toUser(c.receiveData("D"));
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return u;
    }

    @Override
    public User logarUser(String name, Long ip) throws BusinessException {
        if(ip==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(name==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        try{
            c.sendData("User-Logar");
            c.sendData(name);
            c.sendData(Handler.toJson(ip));
            User u = Handler.toUser(c.receiveData("D"));
            return u;
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
    }
}
