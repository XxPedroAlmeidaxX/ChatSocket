package br.cefetmg.chat.server;

import br.cefetmg.chat.exception.ConnectionException;
import br.cefetmg.chat.implementation.connection.Connection;
import java.lang.RuntimeException;

public class AdapterServer implements Runnable{
    
    private final Connection con;
    
    public AdapterServer(Connection c) throws ConnectionException{
        con = c;
    }
    
    @Override
    public void run() {  
        while(true) {
            try{
                //Recebe do cliente a operação a ser executada
                String operation = "";
                String[] sOperation = new String[2];
                
                operation = (String)con.receiveData();
                sOperation = operation.split("-");

                switch(sOperation[0]) {
                    case "User":
                        switch(sOperation[1]) {
                            case "Insert":
                                break;
                            case "GetId":
                                break;
                            case "Delete":
                                break;
                            case "Update":
                                break;
                            case "GetIpName":
                                break;
                            
                        }

                }
                
            } catch(Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
