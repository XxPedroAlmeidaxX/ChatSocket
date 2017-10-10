package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.interfaces.service.IRoomBusiness;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.interfaces.connection.IConnection;
import br.cefetmg.chat.util.gson.Handler;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class RoomBusiness implements IRoomBusiness{
    private final IConnection c;
    
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
            c.sendData("Room-Insert");
            c.sendData(Handler.toJson(r));
            r = Handler.toRoom(c.receiveData("D"));
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
            c.sendData("Room-Get");
            c.sendData(id.toString());
            r = Handler.toRoom(c.receiveData("D"));
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
            c.sendData("Room-Delete");
            c.sendData(id.toString());
            r = Handler.toRoom(c.receiveData("D"));
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
            c.sendData("Room-Update");
            c.sendData(id.toString());
            c.sendData(Handler.toJson(r));
            r = Handler.toRoom(c.receiveData("D"));
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
        return r;
    }
    
    @Override
    public ArrayList<Room> getAllRoom() throws BusinessException {
        try{
            c.sendData("Room-all");
            return Handler.toRoomAray(c.receiveData("D"));
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
            c.sendData("Room-insertUserRoom");
            c.sendData(Handler.toJson(u));
            c.sendData(id.toString());
            return Handler.toRoom(c.receiveData("D"));
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
            c.sendData("Room-removeUserRoom");
            c.sendData(Handler.toJson(idUser));
            c.sendData(Handler.toJson(idRoom));
            return Handler.toRoom(c.receiveData("D"));
        }catch(ConnectionException ex){
            throw new BusinessException(ex.getMessage());
        }
    }
}
