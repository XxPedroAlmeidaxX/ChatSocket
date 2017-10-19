package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte
 */

public interface IRoomBusiness extends Remote{
    public Room insertRoom(Room r) throws BusinessException, RemoteException;
    public Room insertUserRoom(User u, Long id) throws BusinessException, RemoteException;
    public Room removeUserRoom(Long idUser, Long idRoom) throws BusinessException, RemoteException;
    public Room getRoomById(Long id) throws BusinessException, RemoteException;
    public Room deleteRoomById(Long id) throws BusinessException, RemoteException;
    public Room updateRoomById(Long id, Room r) throws BusinessException, RemoteException;
    public ArrayList<Room> getAllRoom() throws BusinessException, RemoteException;
}
