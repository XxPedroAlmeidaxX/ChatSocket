package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IUserBusiness {
    public User logarUser(String name, Long ip) throws BusinessException;
    public User insertUser(User u) throws BusinessException;
    public User getUserById(Long id) throws BusinessException;
    public User getUserByIpAndName(Long ip, String name) throws BusinessException;
    public User deleteUserById(Long id) throws BusinessException;
    public User updateUserById(Long id, User u) throws BusinessException;
    
}
