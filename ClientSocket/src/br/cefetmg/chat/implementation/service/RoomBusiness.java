package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.ConnectionManager;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.util.List;

public class RoomBusiness implements IRoomBusiness{
    private IConnection c;
    
    public RoomBusiness(){
        try {
            c = ConnectionManager.getInstance().getConnection();
        } catch (ConnectionException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @Override
    public Room insertRoom(Room r) throws BusinessException{
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
            c.sendDados("Room-Insert");
            c.sendDados(r);
            r = (Room) c.receiveDados();
        } catch (ConnectionException ex) {
            throw new BusinessException(ex.getMessage());
        }
        return r;
    }

    @Override
    public Room getRoomById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        Room r = null;
        try {
            c.sendDados("Room-Get");
            c.sendDados(id);
            r = (Room) c.receiveDados();
        } catch (ConnectionException ex) {
            throw new BusinessException(ex.getMessage());
        }
        return r;
    }

    @Override
    public Room deleteRoomById(Long id) throws BusinessException {
        if(id==null){
            throw new BusinessException("Id não pode ser nulo");
        }
        Room r = null;
        try {
            c.sendDados("Room-Delete");
            c.sendDados(id);
            r = (Room) c.receiveDados();
        } catch (ConnectionException ex) {
            throw new BusinessException(ex.getMessage());
        }
        return r;
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
        try{
            c.sendDados("Room-Update");
            c.sendDados(id);
            c.sendDados(r);
            r = (Room) c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return r;
    }
    
    @Override
    public List<Room> getAllRoom() throws BusinessException {
        try{
            c.sendDados("Room-all");
            return (List<Room>)c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
    }   
}
