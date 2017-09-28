package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import java.util.List;

public interface IRoomBusiness {
    public Room insertRoom(Room r) throws BusinessException;
    public Room getRoomById(Long id) throws BusinessException;
    public Room deleteRoomById(Long id) throws BusinessException;
    public Room updateRoomById(Long id, Room r) throws BusinessException;
    public List<Room> getAllRoom() throws BusinessException;
}
