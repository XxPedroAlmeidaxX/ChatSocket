package br.cefetmg.chat.server;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.implementation.dao.MessageDAO;
import br.cefetmg.chat.implementation.dao.RoomDAO;
import br.cefetmg.chat.implementation.dao.UserDAO;
import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.implementation.service.UserBusiness;
import java.lang.RuntimeException;

public class AdapterServer implements Runnable{
    
    private final Connection con;
    
    public AdapterServer(Connection c) throws ConnectionException{
        con = c;
    }
    
    @Override
    public void run() {  
        while(true) {
            try{
                //Recebe do cliente a operação a ser executada
                String operation = "";
                String[] sOperation = new String[2];
                MessageBusiness msgB = new MessageBusiness(new MessageDAO());
                UserBusiness usB = new UserBusiness(new UserDAO());
                RoomBusiness roomB = new RoomBusiness(new RoomDAO());
                operation = (String)con.receiveData();
                sOperation = operation.split("-");
                Long id, ip;
                User u;
                String name;
                Room r;
                Message m;
                switch(sOperation[0]) {
                    case "User":
                        switch(sOperation[1]) {
                            case "Insert":
                                u = (User)con.receiveData();
                                con.sendData(usB.insertUser(u));
                                break;
                            case "GetId":
                                id = (Long)con.receiveData();
                                con.sendData(usB.getUserById(id));
                                break;
                            case "Delete":
                                id = (Long)con.receiveData();
                                con.sendData(usB.deleteUserById(id));
                                break;
                            case "Update":
                                id = (Long)con.receiveData();
                                u = (User)con.receiveData();
                                con.sendData(usB.updateUserById(id, u));
                                break;
                            case "GetIpName":
                                ip = (Long)con.receiveData();
                                name = (String)con.receiveData();
                                con.sendData(usB.getUserByIpAndName(ip, name));
                                break;
                        }
                        break;
                    case "Room":
                        switch(sOperation[1]) {
                            case "Insert":
                                r = (Room)con.receiveData();
                                con.sendData(roomB.insertRoom(r));
                                break;
                            case "Get":
                                id = (Long)con.receiveData();
                                con.sendData(roomB.getRoomById(id));
                                break;
                            case "Delete":
                                id = (Long)con.receiveData();
                                con.sendData(roomB.deleteRoomById(id));
                                break;
                            case "Update":
                                id = (Long)con.receiveData();
                                r = (Room)con.receiveData();
                                con.sendData(roomB.updateRoomById(id, r));
                                break;
                            case "all":
                                con.sendData(roomB.getAllRoom());
                                break;
                        }
                        break;
                    case "Message":
                        switch(sOperation[1]) {
                            case "Insert":
                                m = (Message)con.receiveData();
                                con.sendData(msgB.insertMessage(m));
                                break;
                            case "Get":
                                id = (Long)con.receiveData();
                                con.sendData(msgB.getMessageById(id));
                                break;
                            case "Delete":
                                id = (Long)con.receiveData();
                                con.sendData(msgB.deleteMessageById(id));
                                break;
                            case "Update":
                                id = (Long)con.receiveData();
                                m = (Message)con.receiveData();
                                con.sendData(msgB.updateMessageById(id, m));
                                break;
                            case "ByRoom":
                                r = (Room)con.receiveData();
                                con.sendData(msgB.getMessagesByRoom(r));
                                break;
                            case "ByUser":
                                u = (User)con.receiveData();
                                con.sendData(msgB.getMessagesByUser(u));
                                break;
                        }
                        break;
                        

                }
                
            } catch(Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
