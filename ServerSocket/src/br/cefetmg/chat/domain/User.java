package br.cefetmg.chat.domain;

public class User {
    private Long idUser;
    private String nameUser;

    public User(Long idUser, String nameUser) {
        this.idUser = idUser;
        this.nameUser = nameUser;
    }

    public User() {}

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
