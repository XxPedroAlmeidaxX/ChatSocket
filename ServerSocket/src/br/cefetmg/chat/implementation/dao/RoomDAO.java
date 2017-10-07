package br.cefetmg.chat.implementation.dao;

import br.cefetmg.chat.interfaces.dao.IRoomDAO;
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
import java.util.List;

public class RoomDAO implements IRoomDAO{

    @Override
    public synchronized Room insertRoom(Room r) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO Room (nameRoom, stateRoom, password) VALUES(?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, r.getNameRoom());
            pstmt.setBoolean(2, r.getStateRoom());
            pstmt.setString(3, r.getPassword());
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new PersistenceException("Criação da Sala Falhou");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setIdRoom(generatedKeys.getLong(1));
                }
                else {
                    throw new PersistenceException("Criação falhou, sem id's obtidos");
                }
            }
            pstmt.close();
            connection.close();
            return r;
        } catch (PersistenceException | ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized Room getRoomById(Long id) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT idRoom, nameRoom, stateRoom, password FROM Room WHERE idRoom = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            Room r = new Room();
            if (rs.next()) {
                r.setIdRoom(rs.getLong("idRoom"));
                r.setNameRoom(rs.getString("nameRoom"));
                r.setPassword(rs.getString("password"));
                r.setStateRoom(rs.getBoolean("stateRoom"));
            }
            sql = "SELECT idUser, ipUser, nameUser FROM UsersRoom a JOIN Users b ON a.idUser=b.idUser WHERE a.idRoom = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            List<User> users = new ArrayList<>();
            User u;
            while(rs.next()){
                u = new User();
                u.setIdUser(rs.getLong("idUser"));
                u.setIpUser(rs.getLong("ipUser"));
                u.setNameUser(rs.getString("nameUser"));
                users.add(u);
            }
            r.setUsuarios(users);
            rs.close();
            pstmt.close();
            connection.close();
            return r;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized Room deleteRoomById(Long id) throws PersistenceException {
        try {
            Room r = this.getRoomById(id);
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM Room WHERE idRoom = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return r;
        } catch (PersistenceException | ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized Room updateRoomById(Long id, Room r) throws PersistenceException {
        try{
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE Room SET idRoom = ?, nameRoom = ?, stateRoom = ?, password = ? WHERE ipUser = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, r.getIdRoom());
            pstmt.setString(2, r.getNameRoom());
            pstmt.setBoolean(3, r.getStateRoom());
            pstmt.setString(4, r.getPassword());
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return r;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public synchronized List<Room> getAllRoom() throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT idRoom, nameRoom, stateRoom, password FROM Room";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Room r;
            List<Room> allRoom = new ArrayList<>();
            while(rs.next()) {
                r = new Room();
                r.setIdRoom(rs.getLong("idRoom"));
                r.setNameRoom(rs.getString("nameRoom"));
                r.setPassword(rs.getString("password"));
                r.setStateRoom(rs.getBoolean("stateRoom"));
                sql = "SELECT ipUser, a.idUser, nameUser FROM UsersRoom a JOIN Users b ON a.idUser=b.idUser WHERE a.idRoom = ?";
                pstmt = connection.prepareStatement(sql);
                pstmt.setLong(1, r.getIdRoom());
                ResultSet rsA = pstmt.executeQuery();
                List<User> users = new ArrayList<>();
                User u;
                while(rsA.next()){
                    u = new User();
                    u.setIdUser(rsA.getLong("idUser"));
                    u.setIpUser(rsA.getLong("ipUser"));
                    u.setNameUser(rsA.getString("nameUser"));
                    users.add(u);
                }
                r.setUsuarios(users);
                allRoom.add(r);
            }
            rs.close();
            pstmt.close();
            connection.close();
            return allRoom;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public Room insertUserRoom(User u, Long id) throws PersistenceException {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO UsersRoom (idUser, idRoom) VALUES(?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, u.getIdUser());
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return getRoomById(id);
        } catch (PersistenceException | ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public Room removeUserRoom(Long idUser) throws PersistenceException {
         try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT idRoom FROM UsersRoom WHERE idUser = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            Long idRoom=null;
            if (rs.next()) {
                idRoom = rs.getLong("idRoom");
            }
            sql = "DELETE FROM UsersRoom WHERE idUser = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, idUser);
            pstmt.executeUpdate();
            Room r = this.getRoomById(idRoom);
            pstmt.close();
            connection.close();
            return r;
        } catch (PersistenceException | ClassNotFoundException | SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
    
}
