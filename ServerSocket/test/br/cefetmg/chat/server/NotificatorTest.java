/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.server;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.implementation.connection.Connection;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adalbs
 */
public class NotificatorTest {
    
    public NotificatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTabelaUsuarios method, of class Notificator.
     */
    @Test
    public void testGetTabelaUsuarios() {
        System.out.println("getTabelaUsuarios");
        Map<User, Connection> expResult = null;
        Map<User, Connection> result = Notificator.getTabelaUsuarios();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTabela method, of class Notificator.
     */
    @Test
    public void testRemoveTabela() {
        System.out.println("removeTabela");
        User u = null;
        Notificator.removeTabela(u);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTabela method, of class Notificator.
     */
    @Test
    public void testAddTabela() {
        System.out.println("addTabela");
        User u = null;
        Connection s = null;
        Notificator.addTabela(u, s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notifyMessage method, of class Notificator.
     */
    @Test
    public void testNotifyMessage() {
        System.out.println("notifyMessage");
        Message m = null;
        Notificator.notifyMessage(m);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notifyRoom method, of class Notificator.
     */
    @Test
    public void testNotifyRoom() {
        System.out.println("notifyRoom");
        Notificator.notifyRoom();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notifyUserRoom method, of class Notificator.
     */
    @Test
    public void testNotifyUserRoom() {
        System.out.println("notifyUserRoom");
        Notificator.notifyUserRoom();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
