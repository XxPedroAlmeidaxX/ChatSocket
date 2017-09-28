package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.interfaces.dao.IRoomDAO;
import br.cefetmg.chat.implementation.dao.RoomDAO;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.List;

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
    public List<Room> getAllRoom() throws BusinessException, PersistenceException {
        return dao.getAllRoom();
    }   
}
