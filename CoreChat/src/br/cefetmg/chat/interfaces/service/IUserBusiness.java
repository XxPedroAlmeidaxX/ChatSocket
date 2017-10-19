package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IUserBusiness extends Remote{
    public User insertUser(User u) throws BusinessException, RemoteException;
    public User getUserById(Long id) throws BusinessException, RemoteException;
    public User getUserByIpAndName(Long ip, String name) throws BusinessException, RemoteException;
    public User deleteUserById(Long id) throws BusinessException, RemoteException;
    public User updateUserById(Long id, User u) throws BusinessException, RemoteException;
    public User logarUser(String user, Long ip, IUpdateReceiver update) throws BusinessException, RemoteException;
}
