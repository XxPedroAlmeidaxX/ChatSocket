package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.interfaces.dao.IRoomDAO;
import br.cefetmg.chat.implementation.dao.RoomDAO;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.PersistenceException;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class RoomBusiness implements IRoomBusiness{
    private final IRoomDAO dao;
    
    public RoomBusiness(RoomDAO dao){
        this.dao=dao;
    }
    
    @Override
    public Room insertRoom(Room r) throws BusinessException {
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
        try {
            return dao.insertRoom(r);
        }catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public Room getRoomById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        try {
            return dao.getRoomById(id);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public Room deleteRoomById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        try {
            return dao.deleteRoomById(id);
        }catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public Room updateRoomById(Long id, Room r) throws BusinessException {
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
        try {
            return dao.updateRoomById(id, r);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
    @Override
    public ArrayList<Room> getAllRoom() throws BusinessException {
        try {
            return dao.getAllRoom();
        }catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }   

    @Override
    public Room insertUserRoom(User u, Long id) throws BusinessException {
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
        try {
            return dao.insertUserRoom(u, id);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
    @Override
    public Room removeUserRoom(Long idUser, Long idRoom) throws BusinessException {
        if(idUser==null){
            throw new BusinessException("Id do usuario não pode ser nulo");
        }
        Room r;
        try {
            r = dao.removeUserRoom(idUser);
        } catch (PersistenceException ex) {
            throw new BusinessException(ex.getMessage());
        }
        if(r.getUsuarios().isEmpty()){
            this.deleteRoomById(r.getIdRoom());
        }
        
        return r;
    }
}
