package br.cefetmg.chat.util.gson;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

/**
 * 
 * @author Pedro Almeida
 */

//Classe para a manipulação de JSON, já que o envio direto de objetos estava causando erros
public class Handler {
    
    public static String toJson(Object obj) {
        return (new Gson().toJson(obj));
    }
    
    public static Message toMessage(String json) {
        return (new Gson().fromJson(json, Message.class));
    }
    
    public static Room toRoom(String json) {
        return (new Gson().fromJson(json, Room.class));
    }
    
    public static User toUser(String json) {
        return (new Gson().fromJson(json, User.class));
    }
    
    public static ArrayList<Message> toMessageAray(String json) {
        return (new Gson().fromJson(json, new TypeToken<ArrayList<Message>>(){}.getType())); 
    }
    
    public static ArrayList<Room> toRoomAray(String json) {
        return (new Gson().fromJson(json, new TypeToken<ArrayList<Room>>(){}.getType())); 
    }
}
