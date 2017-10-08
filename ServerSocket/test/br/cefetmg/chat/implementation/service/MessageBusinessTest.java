/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import java.util.ArrayList;
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
public class MessageBusinessTest {
    
    public MessageBusinessTest() {
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
     * Test of insertMessage method, of class MessageBusiness.
     */
    @Test
    public void testInsertMessage() throws Exception {
        System.out.println("insertMessage");
        Message m = null;
        MessageBusiness instance = null;
        Message expResult = null;
        Message result = instance.insertMessage(m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageById method, of class MessageBusiness.
     */
    @Test
    public void testGetMessageById() throws Exception {
        System.out.println("getMessageById");
        Long id = null;
        MessageBusiness instance = null;
        Message expResult = null;
        Message result = instance.getMessageById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMessageById method, of class MessageBusiness.
     */
    @Test
    public void testDeleteMessageById() throws Exception {
        System.out.println("deleteMessageById");
        Long id = null;
        MessageBusiness instance = null;
        Message expResult = null;
        Message result = instance.deleteMessageById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateMessageById method, of class MessageBusiness.
     */
    @Test
    public void testUpdateMessageById() throws Exception {
        System.out.println("updateMessageById");
        Long id = null;
        Message m = null;
        MessageBusiness instance = null;
        Message expResult = null;
        Message result = instance.updateMessageById(id, m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessagesByUser method, of class MessageBusiness.
     */
    @Test
    public void testGetMessagesByUser() throws Exception {
        System.out.println("getMessagesByUser");
        User u = null;
        MessageBusiness instance = null;
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = instance.getMessagesByUser(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessagesByRoom method, of class MessageBusiness.
     */
    @Test
    public void testGetMessagesByRoom() throws Exception {
        System.out.println("getMessagesByRoom");
        Room r = null;
        MessageBusiness instance = null;
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = instance.getMessagesByRoom(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
