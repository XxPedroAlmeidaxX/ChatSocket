package br.cefetmg.chat.domain;

public class User {
    private Long ipUser;
    private String nameUser;

    public User(Long ipUser, String nameUser) {
        this.ipUser = ipUser;
        this.nameUser = nameUser;
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
}
