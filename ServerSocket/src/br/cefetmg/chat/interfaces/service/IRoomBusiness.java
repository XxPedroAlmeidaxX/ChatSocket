package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IRoomBusiness {
    public Room insertRoom(Room r) throws BusinessException, PersistenceException;
    public Room insertUserRoom(User u, Long id) throws BusinessException, PersistenceException;
    public Room removeUserRoom(Long idUser) throws BusinessException, PersistenceException;
    public Room getRoomById(Long id) throws BusinessException, PersistenceException;
    public Room deleteRoomById(Long id) throws BusinessException, PersistenceException;
    public Room updateRoomById(Long id, Room r) throws BusinessException, PersistenceException;
    public ArrayList<Room> getAllRoom() throws BusinessException, PersistenceException;
}
