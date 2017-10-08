package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.util.ArrayList;

public class RoomBusiness implements IRoomBusiness{
    private IConnection c;
    
    public RoomBusiness(Connection c){
        this.c = c;
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
    public ArrayList<Room> getAllRoom() throws BusinessException {
        try{
            c.sendDados("Room-all");
            return (ArrayList<Room>)c.receiveDados();
        }catch(ConnectionException ex){
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
        try{
            c.sendDados("Room-insertUserRoom");
            c.sendDados(u);
            c.sendDados(id);
            return (Room)c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public Room removeUserRoom(Long idUser, Long idRoom) throws BusinessException {
        if(idUser==null){
            throw new BusinessException("Id do usuario não pode ser nulo");
        }
        if(idRoom==null){
            throw new BusinessException("Id da sala não pode ser nulo");
        }
        try{
            c.sendDados("Room-removeUserRoom");
            c.sendDados(idUser);
            c.sendDados(idRoom);
            return (Room)c.receiveDados();
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
    }
}
