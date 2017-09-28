package br.cefetmg.chat.domain;

import java.util.List;

public class Room {
    private Long idRoom;
    private String nameRoom;
    private String password;
    private Boolean stateRoom;
    private List<User> usuarios;
    
    public Room(Long idRoom, String nameRoom, Boolean stateRoom, String password, List<User> usuarios) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
        this.stateRoom = stateRoom;
        this.password = password;
        this.usuarios=usuarios;
    }

    public Room() {}

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public Boolean getStateRoom() {
        return stateRoom;
    }

    public void setStateRoom(Boolean stateRoom) {
        this.stateRoom = stateRoom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }
}
