package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.interfaces.dao.IRoomDAO;
import br.cefetmg.chat.implementation.dao.RoomDAO;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.ArrayList;

public class RoomBusiness implements IRoomBusiness{
    private final IRoomDAO dao;
    
    public RoomBusiness(RoomDAO dao){
        this.dao=dao;
    }
    
    @Override
    public Room insertRoom(Room r) throws BusinessException, PersistenceException {
        if(r==null){
            throw new BusinessException("Sala não pode ser nula");
        }
        if(r.getStateRoom()==null){
            throw new BusinessException("Estado da sala não pode ser nulo");
        }
        if(r.getIdRoom()==null){
            throw new BusinessException("Id da sala não pode ser nulo");
        }
        if(r.getNameRoom()==null){
            throw new BusinessException("Nome da sala não pode ser nulo");
        }
        return dao.insertRoom(r);
    }

    @Override
    public Room getRoomById(Long id) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.getRoomById(id);
    }

    @Override
    public Room deleteRoomById(Long id) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.deleteRoomById(id);
    }

    @Override
    public Room updateRoomById(Long id, Room r) throws BusinessException, PersistenceException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        if(r==null){
            throw new BusinessException("Sala não pode ser nula");
        }
        if(r.getStateRoom()==null){
            throw new BusinessException("Estado da sala não pode ser nulo");
        }
        if(r.getIdRoom()==null){
            throw new BusinessException("Id da sala não pode ser nulo");
        }
        if(r.getNameRoom()==null){
            throw new BusinessException("Nome da sala não pode ser nulo");
        }
        return dao.updateRoomById(id, r);
    }
    
    @Override
    public ArrayList<Room> getAllRoom() throws BusinessException, PersistenceException {
        return dao.getAllRoom();
    }   

    @Override
    public Room insertUserRoom(User u, Long id) throws BusinessException, PersistenceException {
        if(u==null){
            throw new BusinessException("Usuario não pode ser nulo");
        }
        if(u.getIpUser()==null){
            throw new BusinessException("Ip do usuário não pode ser nulo");
        }
        if(u.getNameUser()==null){
            throw new BusinessException("Nome do usuário não pode ser nulo");
        }
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        return dao.insertUserRoom(u, id);
    }

    @Override
    public Room removeUserRoom(Long idUser) throws BusinessException, PersistenceException {
        if(idUser==null){
            throw new BusinessException("Id do usuario não pode ser nulo");
        }
        return dao.removeUserRoom(idUser);
    }
}
