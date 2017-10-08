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

public class AdapterServer implements Runnable{
    
    //Conexão do servidor com cliente
    private final Connection con;
    //Cliente conectado
    private User cliente;
    //Instancias das classes de serviço
    private MessageBusiness msgB;
    private UserBusiness usB;
    private RoomBusiness roomB;
    
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
                operation = (String)con.receiveData();
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
                                name = (String)con.receiveData();
                                ip = (Long) con.receiveData();
                                
                                //Se o usuário existe, o retorna
                                if(usB.getUserByIpAndName(ip, name).getIdUser()!=null){
                                    cliente = usB.getUserByIpAndName(ip, name);
                                    con.sendData(cliente);
                                }else{
                                    //Senão cria um novo usuário e o retorna
                                    User us = new User();
                                    us.setIpUser(ip);
                                    us.setNameUser(name);
                                    cliente = usB.insertUser(us);
                                    con.sendData(cliente);
                                }
                                //Adiciona usuário na tabela de clientes ativos
                                Notificator.addTabela(cliente, con);
                                break;
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
                        //Seleciona as possibilidades de operações
                        switch(sOperation[1]) {
                            case "Insert":
                                r = (Room)con.receiveData();
                                con.sendData(roomB.insertRoom(r));
                                //Notifica clientes da criação da nova sala
                                Notificator.notifyRoom();
                                break;
                            case "Get":
                                id = (Long)con.receiveData();
                                con.sendData(roomB.getRoomById(id));
                                break;
                            case "Delete":
                                id = (Long)con.receiveData();
                                con.sendData(roomB.deleteRoomById(id));
                                Notificator.notifyRoom();
                                break;
                            case "Update":
                                id = (Long)con.receiveData();
                                r = (Room)con.receiveData();
                                con.sendData(roomB.updateRoomById(id, r));
                                break;
                            case "all":
                                con.sendData(roomB.getAllRoom());
                                break;
                            case "insertUserRoom":
                                u = (User)con.receiveData();
                                id = (Long)con.receiveData();
                                con.sendData(roomB.insertUserRoom(u, id));
                                //Notifica clientes da entrada de um usuário na sala
                                Notificator.notifyUserRoom();
                                break;
                            case "removeUserRoom":
                                Long idUser = (Long)con.receiveData();
                                Long idRoom = (Long)con.receiveData();
                                //Remove usuário da sala
                                Room roomAlterada = roomB.removeUserRoom(idUser);
                                con.sendData(roomAlterada);
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
                                m = (Message)con.receiveData();
                                con.sendData(msgB.insertMessage(m));
                                Notificator.notifyMessage(m);
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
            } catch(ConnectionException | BusinessException | PersistenceException ex) {
                try {
                    //Caso a conexão falhe, remove o cliente da sala
                    roomB.removeUserRoom(cliente.getIdUser());
                    //Notifica da saída do cliente
                    Notificator.notifyUserRoom();
                } catch (BusinessException | PersistenceException | NullPointerException ex1) {
                    throw new RuntimeException(ex1.getMessage());
                }
                //Remove o cliente da tabela
                Notificator.removeTabela(cliente);
                throw new RuntimeException(ex);
            }
        }
    }
}
