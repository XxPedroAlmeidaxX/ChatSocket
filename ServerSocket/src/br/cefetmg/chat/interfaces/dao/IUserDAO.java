package br.cefetmg.chat.interfaces.dao;

import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.PersistenceException;

public interface IUserDAO {
    public User insertUser(User u) throws PersistenceException;
    public User getUserById(Long id) throws PersistenceException;
    public User deleteUserById(Long id) throws PersistenceException;
    public User updateUserById(Long id, User u) throws PersistenceException;
}
