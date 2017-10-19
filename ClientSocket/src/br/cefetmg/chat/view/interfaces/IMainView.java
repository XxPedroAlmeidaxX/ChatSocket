/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.view.interfaces;

import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;

/**
 *
 * @author Aluno
 */
public interface IMainView {
    public void showHome();
    public void showLogin();
    public void loadRoom(Room r) throws BusinessException;
    public void showRoomMaker();
    public Room getCurrentRoom();
    public void setCurrentRoom(Room r);
}
