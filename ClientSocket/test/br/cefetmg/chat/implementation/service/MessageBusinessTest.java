/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.Message;
import br.cefetmg.chat.domain.Room;
import br.cefetmg.chat.domain.User;
import br.cefetmg.chat.exception.BusinessException;
import br.cefetmg.chat.implementation.connection.Connection;
import br.cefetmg.chat.interfaces.connection.IConnection;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import br.cefetmg.chat.util.gson.Handler;
/**
 *
 * @author Adalbs
 */
public class MessageBusinessTest {
    Connection c = mock(Connection.class);
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
     * Test of getMessageById method, of class MessageBusiness.
     */
    @Test
    public void testGetMessageById1() throws Exception {
        Long idMessage=Integer.toUnsignedLong(1);
        User user= new User(Integer.toUnsignedLong(1), "teste", Integer.toUnsignedLong(2));
        Room room= new Room();
        String textMessage="mackito for the wins";
        Boolean stateMessage=true;
        User targetMessage = new User(Integer.toUnsignedLong(3), "teste", Integer.toUnsignedLong(4));
        Message m = new Message();
        m.setRoom(room);
        m.setStateMessage(stateMessage);
        m.setTargetMessage(targetMessage);
        m.setTextMessage(textMessage);
        m.setUser(user);
        m.setIdMessage(idMessage);
        doNothing().when(c).sendData(anyString());
        when(c.receiveData()).thenReturn(Handler.toJson(m));
        MessageBusiness msg = new MessageBusiness(c);
        assertEquals(msg.getMessageById(idMessage),m);
    }
    /**
     * Test of getMessageById method, of class MessageBusiness.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMessageById2() throws Exception {
       Long idMessage=null;
       MessageBusiness msg = new MessageBusiness(c);
       try{  
           msg.getMessageById(idMessage);
           fail("devia ter lançado exception");  
       }catch(BusinessException e){
           assertTrue(e.getMessage().matches("Id não pode ser nulo"));   
       } 
    }
    @Test
    public void testDeleteMessageById1() throws Exception {
        Long idMessage=Integer.toUnsignedLong(1);
        User user= new User(Integer.toUnsignedLong(1), "teste", Integer.toUnsignedLong(2));
        Room room= new Room();
        String textMessage="mackito for the wins";
        Boolean stateMessage=true;
        User targetMessage = new User(Integer.toUnsignedLong(3), "teste", Integer.toUnsignedLong(4));
        Message m = new Message();
        m.setRoom(room);
        m.setStateMessage(stateMessage);
        m.setTargetMessage(targetMessage);
        m.setTextMessage(textMessage);
        m.setUser(user);
        m.setIdMessage(idMessage);
        doNothing().when(c).sendData(anyString());
        when(c.receiveData()).thenReturn(Handler.toJson(m));
        MessageBusiness msg = new MessageBusiness(c);
        assertEquals(msg.deleteMessageById(idMessage),m);
    }
    /**
     * Test of deleteMessageById method, of class MessageBusiness.
     */
    @Test
    public void testDeleteMessageById2() throws Exception {
        Long idMessage=null;
       MessageBusiness msg = new MessageBusiness(c);
       try{  
           msg.deleteMessageById(idMessage);
           fail("devia ter lançado exception");  
       }catch(BusinessException e){
           assertTrue(e.getMessage().matches("Id não pode ser nulo"));   
       } 
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
