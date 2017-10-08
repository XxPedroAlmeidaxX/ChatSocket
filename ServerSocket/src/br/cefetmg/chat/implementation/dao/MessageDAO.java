package br.cefetmg.chat.implementation.dao;

import br.cefetmg.chat.interfaces.dao.IMessageDAO;
import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.PersistenceException;
import br.cefetmg.chat.util.db.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class MessageDAO implements IMessageDAO{

    @Override
    public synchronized Message insertMessage(Message m) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO Room (textMessage, stateMessage, targetMessage, senderUser, room) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, m.getTextMessage());
            pstmt.setBoolean(2, m.getStateMessage());
            pstmt.setLong(3, m.getTargetMessage().getIdUser());
            pstmt.setLong(4, m.getUser().getIdUser());
            pstmt.setLong(5, m.getRoom().getIdRoom());
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new PersistenceException("Criação da Mensagem Falhou");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setIdMessage(generatedKeys.getLong(1));
                }
                else {
                    throw new PersistenceException("Criação falhou, sem id's obtidos");
                }
            }
            pstmt.close();
            connection.close();
            return m;
        } catch (PersistenceException | ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized Message getMessageById(Long id) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT a.idMessage idMessage, a.textMessage textMessage, a.stateMessage stateMessage, "
                    + " b.ipUser ipTarget, b.nameUser nameTarget, b.idUser idTarget "
                    + " c.ipUser ipSender, c.nameUser nameSender, c.idUser idSender"
                    + " d.idRoom idRoom, d.nameRoom nameRoom, d.stateRoom stateRoom, d.password roomPassword "
                    + " FROM Message a"
                    + " JOIN Users b ON a.targetMessage=b.idUser "
                    + " JOIN Users c ON a.senderUser=c.idUser "
                    + " JOIN Room d ON a.room=d.idRoom WHERE idMessage = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            Room r = new Room();
            User target = new User();
            User sender = new User();
            Message m = new Message();
            if (rs.next()) {
                r.setIdRoom(rs.getLong("idRoom"));
                r.setNameRoom(rs.getString("nameRoom"));
                r.setPassword(rs.getString("roomPassword"));
                r.setStateRoom(rs.getBoolean("stateRoom"));
                target.setIpUser(rs.getLong("ipTarget"));
                target.setIdUser(rs.getLong("idTarget"));
                target.setNameUser(rs.getString("nameTarget"));
                sender.setIpUser(rs.getLong("ipSender"));
                sender.setIdUser(rs.getLong("idSender"));
                sender.setNameUser(rs.getString("nameSender"));
                m.setIdMessage(rs.getLong("idMessage"));
                m.setTextMessage(rs.getString("textMessage"));
                m.setStateMessage(rs.getBoolean("stateMessage"));
                m.setRoom(r);
                m.setTargetMessage(target);
                m.setUser(sender);
            }
            rs.close();
            pstmt.close();
            connection.close();
            return m;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized Message deleteMessageById(Long id) throws PersistenceException {
        try {
            Message m = this.getMessageById(id);
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM Message WHERE idMessage = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return m;
        } catch (PersistenceException | ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized Message updateMessageById(Long id, Message m) throws PersistenceException {
        try{
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE Message SET idMessage = ?, textMessage = ?, stateMessage = ?, targetMessage = ?, senderUser = ?, room = ?, nameUser = ?, nameTarget = ? WHERE ipUser = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, m.getIdMessage());
            pstmt.setString(2, m.getTextMessage());
            pstmt.setBoolean(3, m.getStateMessage());
            pstmt.setLong(4, m.getTargetMessage().getIpUser());
            pstmt.setLong(5, m.getUser().getIpUser());
            pstmt.setLong(6, m.getRoom().getIdRoom());
            pstmt.setString(7, m.getUser().getNameUser());
            pstmt.setString(8, m.getTargetMessage().getNameUser());
            pstmt.setLong(9, id);
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return m;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized ArrayList<Message> getMessagesByUser(User u) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT a.idMessage idMessage, a.textMessage textMessage, a.stateMessage stateMessage, "
                    + " b.ipUser ipTarget, b.nameUser nameTarget, "
                    + " c.ipUser ipSender, c.nameUser nameSender, "
                    + " d.idRoom idRoom, d.nameRoom nameRoom, d.stateRoom stateRoom, d.password roomPassword "
                    + " FROM Message a"
                    + " JOIN Users b ON a.targetMessage=b.ipUser AND a.nameTarget=b.nameUser"
                    + " JOIN Users c ON a.senderUser=c.ipUser AND a.nameUser=c.nameUser "
                    + " JOIN Room d ON a.room=d.idRoom WHERE ipSender = ? AND nameSender = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, u.getIpUser());
            pstmt.setString(2, u.getNameUser());
            ResultSet rs = pstmt.executeQuery();
            Room r;
            User target;
            User sender;
            Message m;
            ArrayList<Message> msgs = new ArrayList<>();
            while(rs.next()) {
                m = new Message();
                sender = new User();
                target = new User();
                r = new Room();
                r.setIdRoom(rs.getLong("idRoom"));
                r.setNameRoom(rs.getString("nameRoom"));
                r.setPassword(rs.getString("roomPassword"));
                r.setStateRoom(rs.getBoolean("stateRoom"));
                target.setIpUser(rs.getLong("ipTarget"));
                target.setNameUser(rs.getString("nameTarget"));
                sender.setIpUser(rs.getLong("ipSender"));
                sender.setNameUser(rs.getString("nameSender"));
                m.setIdMessage(rs.getLong("idMessage"));
                m.setTextMessage(rs.getString("textMessage"));
                m.setStateMessage(rs.getBoolean("stateMessage"));
                m.setRoom(r);
                m.setTargetMessage(target);
                m.setUser(sender);
                msgs.add(m);
            }
            rs.close();
            pstmt.close();
            connection.close();
            return msgs;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized ArrayList<Message> getMessagesByRoom(Room r) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT a.idMessage idMessage, a.textMessage textMessage, a.stateMessage stateMessage, "
                    + " b.ipUser ipTarget, b.nameUser nameTarget, "
                    + " c.ipUser ipSender, c.nameUser nameSender, "
                    + " d.idRoom idRoom, d.nameRoom nameRoom, d.stateRoom stateRoom, d.password roomPassword "
                    + " FROM Message a"
                    + " JOIN Users b ON a.targetMessage=b.ipUser AND a.nameTarget=b.nameUser"
                    + " JOIN Users c ON a.senderUser=c.ipUser AND a.nameUser=c.nameUser "
                    + " JOIN Room d ON a.room=d.idRoom WHERE idRoom = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, r.getIdRoom());
            ResultSet rs = pstmt.executeQuery();
            User target;
            User sender;
            Message m;
            ArrayList<Message> msgs = new ArrayList<>();
            while(rs.next()) {
                m = new Message();
                sender = new User();
                target = new User();
                r = new Room();
                r.setIdRoom(rs.getLong("idRoom"));
                r.setNameRoom(rs.getString("nameRoom"));
                r.setPassword(rs.getString("roomPassword"));
                r.setStateRoom(rs.getBoolean("stateRoom"));
                target.setIpUser(rs.getLong("ipTarget"));
                target.setNameUser(rs.getString("nameTarget"));
                sender.setIpUser(rs.getLong("ipSender"));
                sender.setNameUser(rs.getString("nameSender"));
                m.setIdMessage(rs.getLong("idMessage"));
                m.setTextMessage(rs.getString("textMessage"));
                m.setStateMessage(rs.getBoolean("stateMessage"));
                m.setRoom(r);
                m.setTargetMessage(target);
                m.setUser(sender);
                msgs.add(m);
            }
            rs.close();
            pstmt.close();
            connection.close();
            return msgs;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
    
}
