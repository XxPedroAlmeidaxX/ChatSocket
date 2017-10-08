package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IUserBusiness {
    public User insertUser(User u) throws BusinessException, PersistenceException;
    public User getUserById(Long id) throws BusinessException, PersistenceException;
    public User deleteUserById(Long id) throws BusinessException, PersistenceException;
    public User getUserByIpAndName(Long ip, String name) throws BusinessException, PersistenceException;
    public User updateUserById(Long id, User u) throws BusinessException, PersistenceException;
    
}
