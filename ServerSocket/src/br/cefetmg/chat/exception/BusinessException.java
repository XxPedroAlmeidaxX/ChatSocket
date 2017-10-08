package br.cefetmg.chat.exception;

/**
 * 
 * @author Vitor Rodarte
 */


public class BusinessException extends Exception{
    public BusinessException(String msg){
        super(msg);
    }
}
