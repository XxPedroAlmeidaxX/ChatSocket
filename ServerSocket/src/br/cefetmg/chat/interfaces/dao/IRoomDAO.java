package br.cefetmg.chat.interfaces.dao;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.ArrayList;

public interface IRoomDAO {
    public Room insertRoom(Room r) throws PersistenceException;
    public Room insertUserRoom(User u, Long id) throws PersistenceException;
    public Room removeUserRoom(Long idUser) throws PersistenceException;
    public Room getRoomById(Long id) throws PersistenceException;
    public Room deleteRoomById(Long id) throws PersistenceException;
    public Room updateRoomById(Long id, Room r) throws PersistenceException;
    public ArrayList<Room> getAllRoom() throws PersistenceException;
}
