/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.interfaces.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.exception.BusinessException;
import java.rmi.RemoteException;

/**
 *
 * @author Aluno
 */
public interface IMessageReceiver {
    public void receiveMessage(Message m) throws BusinessException, RemoteException;
    public void receiveUpdate(String idt) throws BusinessException, RemoteException;
}
