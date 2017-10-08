package br.cefetmg.chat.domain;

import java.io.Serializable;

public class User implements Serializable{
    //Endereço de ip do usuário
    private Long ipUser;
    //Nome do usuário
    private String nameUser;
    //Id do usuário
    private Long idUser;

    public User(Long ipUser, String nameUser, Long idUser) {
        this.ipUser = ipUser;
        this.nameUser = nameUser;
        this.idUser=idUser;
    }

    public User() {}

    public Long getIpUser() {
        return ipUser;
    }

    public void setIpUser(Long ipUser) {
        this.ipUser = ipUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
