package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IRoomBusiness {
    public Room insertRoom(Room r) throws BusinessException;
    public Room insertUserRoom(User u, Long id) throws BusinessException;
    public Room removeUserRoom(Long idUser, Long idRoom) throws BusinessException;
    public Room getRoomById(Long id) throws BusinessException;
    public Room deleteRoomById(Long id) throws BusinessException;
    public Room updateRoomById(Long id, Room r) throws BusinessException;
    public ArrayList<Room> getAllRoom() throws BusinessException;
}
