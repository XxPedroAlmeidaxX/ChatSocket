package br.cefetmg.chat.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Vitor Rodarte & Pedro Almeida
 */

public class Room implements Serializable{
    //Id da sala
    private Long idRoom;
    //Nome da sala
    private String nameRoom;
    //Senha caso seja privada
    private String password;
    //Definição de se a sala é privada ou não
    private Boolean stateRoom;
    //Usuários logados na sala
    private ArrayList<User> usuarios;
    
    public Room(Long idRoom, String nameRoom, Boolean stateRoom, String password, ArrayList<User> usuarios) {
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

    public ArrayList<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<User> usuarios) {
        this.usuarios = usuarios;
    }
}
