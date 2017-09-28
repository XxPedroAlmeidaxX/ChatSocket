package br.cefetmg.chat.domain;

public class Message {
    private Long idMessage;
    private User user;
    private Room room;
    private String textMessage;
    private Boolean stateMessage;
    private User targetMessage;

    public Message() {}

    public Message(Long idMessage, User user, Room room, String textMessage, Boolean stateMessage, User targetMessage) {
        this.idMessage=idMessage;
        this.user = user;
        this.room = room;
        this.textMessage = textMessage;
        this.stateMessage = stateMessage;
        this.targetMessage = targetMessage;
    }

    public Message(Long idMessage, User user, Room room, String textMessage, Boolean stateMessage) {
        this.idMessage=idMessage;
        this.user = user;
        this.room = room;
        this.textMessage = textMessage;
        this.stateMessage = stateMessage;
    }

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Boolean getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(Boolean stateMessage) {
        this.stateMessage = stateMessage;
    }

    public User getTargetMessage() {
        return targetMessage;
    }

    public void setTargetMessage(User targetMessage) {
        this.targetMessage = targetMessage;
    }  
}
