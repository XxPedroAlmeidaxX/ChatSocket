package br.cefetmg.chat.server;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.exception.PersistenceException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.implementation.dao.MessageDAO;
import br.cefetmg.chat.implementation.dao.RoomDAO;
import br.cefetmg.chat.implementation.dao.UserDAO;
import br.cefetmg.chat.implementation.service.MessageBusiness;
import br.cefetmg.chat.implementation.service.RoomBusiness;
import br.cefetmg.chat.implementation.service.UserBusiness;
import br.cefetmg.chat.util.gson.Handler;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class AdapterServer implements Runnable{
    
    //Conexão do servidor com cliente
    private final Connection con;
    //Cliente conectado
    private User cliente;
    //Instancias das classes de serviço
    private final MessageBusiness msgB;
    private final UserBusiness usB;
    private final RoomBusiness roomB;
    
    public AdapterServer(Connection c) throws ConnectionException{
        con = c;
        msgB = new MessageBusiness(new MessageDAO());
        usB = new UserBusiness(new UserDAO());
        roomB = new RoomBusiness(new RoomDAO());
    }
    
    @Override
    public void run() {  
        while(true) {
            try{
                //Recebe do cliente a operação a ser executada
                String operation = "";
                //Separa a classe da operação do seu tipo
                String[] sOperation = new String[2];
                operation = con.receiveData();
                sOperation = operation.split("-");
                
                //Variaveis utilizadas para guardar os dados recebidos
                Long id, ip;
                User u;
                String name;
                Room r;
                Message m;
                
                //Seleciona as possibilidades de classes
                switch(sOperation[0]) {
                    case "User":
                        //Seleciona as possibilidades de operações
                        switch(sOperation[1]) {
                            case "Logar":
                                //Recebe nome e ip
                                name = con.receiveData();
                                ip = Long.parseLong(con.receiveData());
                                
                                //Se o usuário existe, o retorna
                                if(usB.getUserByIpAndName(ip, name).getIdUser()!=null){
                                    cliente = usB.getUserByIpAndName(ip, name);
                                    con.sendData(Handler.toJson(cliente), "D");
                                }else{
                                    //Senão cria um novo usuário e o retorna
                                    User us = new User();
                                    us.setIpUser(ip);
                                    us.setNameUser(name);
                                    usB.insertUser(us);
                                    cliente = usB.getUserByIpAndName(ip, name);
                                    con.sendData(Handler.toJson(cliente), "D");
                                }
                                //Adiciona usuário na tabela de clientes ativos
                                Notificator.addTabela(cliente, con);
                                break;
                            case "Insert":
                                u = Handler.toUser(con.receiveData());
                                con.sendData(Handler.toJson(usB.insertUser(u)), "D");
                                break;
                            case "GetId":
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(usB.getUserById(id)), "D");
                                break;
                            case "Delete":
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(usB.deleteUserById(id)), "D");
                                break;
                            case "Update":
                                id = Long.parseLong(con.receiveData());
                                u = Handler.toUser(con.receiveData());
                                con.sendData(Handler.toJson(usB.updateUserById(id, u)), "D");
                                break;
                            case "GetIpName":
                                ip = Long.parseLong(con.receiveData());
                                name = (String)con.receiveData();
                                con.sendData(Handler.toJson(usB.getUserByIpAndName(ip, name)), "D");
                                break;
                        }
                        break;
                    case "Room":
                        //Seleciona as possibilidades de operações
                        switch(sOperation[1]) {
                            case "Insert":
                                r = Handler.toRoom(con.receiveData());
                                con.sendData(Handler.toJson(roomB.insertRoom(r)), "D");
                                //Notifica clientes da criação da nova sala
                                Notificator.notifyRoom();
                                break;
                            case "Get":
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(roomB.getRoomById(id)), "D");
                                break;
                            case "Delete":
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(roomB.deleteRoomById(id)), "D");
                                Notificator.notifyRoom();
                                break;
                            case "Update":
                                id = Long.parseLong(con.receiveData());
                                r = Handler.toRoom(con.receiveData());
                                con.sendData(Handler.toJson(roomB.updateRoomById(id, r)), "D");
                                break;
                            case "all":
                                con.sendData(Handler.toJson(roomB.getAllRoom()), "D");
                                break;
                            case "insertUserRoom":
                                u = Handler.toUser(con.receiveData());
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(roomB.insertUserRoom(u, id)), "D");
                                //Notifica clientes da entrada de um usuário na sala
                                Notificator.notifyUserRoom();
                                break;
                            case "removeUserRoom":
                                Long idUser = Long.parseLong(con.receiveData());
                                Long idRoom = Long.parseLong(con.receiveData());
                                //Remove usuário da sala
                                Room roomAlterada = roomB.removeUserRoom(idUser);
                                con.sendData(Handler.toJson(roomAlterada), "D");
                                //Se a sala está vazia
                                if(roomAlterada.getUsuarios().isEmpty()){
                                    //Remove a sala
                                    roomB.deleteRoomById(roomAlterada.getIdRoom());
                                }
                                //Notifica usuários da remoção de usuário ou deleção da sala
                                Notificator.notifyUserRoom();
                                break;
                             
                        }
                        break;
                    case "Message":
                        //Seleciona as possibilidades de operações
                        switch(sOperation[1]) {
                            case "Insert":
                                m = Handler.toMessage(con.receiveData());
                                m = msgB.insertMessage(m);
                                con.sendData(Handler.toJson(m), "D");
                                Notificator.notifyMessage(m);
                                break;
                            case "Get":
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(msgB.getMessageById(id)), "D");
                                break;
                            case "Delete":
                                id = Long.parseLong(con.receiveData());
                                con.sendData(Handler.toJson(msgB.deleteMessageById(id)), "D");
                                break;
                            case "Update":
                                id = Long.parseLong(con.receiveData());
                                m = Handler.toMessage(con.receiveData());
                                con.sendData(Handler.toJson(msgB.updateMessageById(id, m)), "D");
                                break;
                            case "ByRoom":
                                r = Handler.toRoom(con.receiveData());
                                con.sendData(Handler.toJson(msgB.getMessagesByRoom(r)), "D");
                                break;
                            case "ByUser":
                                u = Handler.toUser(con.receiveData());
                                con.sendData(Handler.toJson(msgB.getMessagesByUser(u)), "D");
                                break;
                        }
                        break;
                }
            } catch(ConnectionException | BusinessException | PersistenceException ex) {
                try {
                    //Caso a conexão falhe, remove o cliente da sala
                    roomB.removeUserRoom(cliente.getIdUser());
                    //Notifica da saída do cliente
                    Notificator.notifyUserRoom();
                } catch (BusinessException | PersistenceException | NullPointerException ex1) {
                    throw new RuntimeException(ex);
                }
                //Remove o cliente da tabela
                Notificator.removeTabela(cliente);
                throw new RuntimeException(ex);
            }
        }
    }
}
