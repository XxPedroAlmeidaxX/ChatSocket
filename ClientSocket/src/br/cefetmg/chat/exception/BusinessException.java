package br.cefetmg.chat.exception;

public class BusinessException extends Exception{
    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
