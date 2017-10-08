/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

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
public class RoomBusinessTest {
    
    public RoomBusinessTest() {
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
     * Test of insertRoom method, of class RoomBusiness.
     */
    @Test
    public void testInsertRoom() throws Exception {
        System.out.println("insertRoom");
        Room r = null;
        RoomBusiness instance = null;
        Room expResult = null;
        Room result = instance.insertRoom(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomById method, of class RoomBusiness.
     */
    @Test
    public void testGetRoomById() throws Exception {
        System.out.println("getRoomById");
        Long id = null;
        RoomBusiness instance = null;
        Room expResult = null;
        Room result = instance.getRoomById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRoomById method, of class RoomBusiness.
     */
    @Test
    public void testDeleteRoomById() throws Exception {
        System.out.println("deleteRoomById");
        Long id = null;
        RoomBusiness instance = null;
        Room expResult = null;
        Room result = instance.deleteRoomById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateRoomById method, of class RoomBusiness.
     */
    @Test
    public void testUpdateRoomById() throws Exception {
        System.out.println("updateRoomById");
        Long id = null;
        Room r = null;
        RoomBusiness instance = null;
        Room expResult = null;
        Room result = instance.updateRoomById(id, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllRoom method, of class RoomBusiness.
     */
    @Test
    public void testGetAllRoom() throws Exception {
        System.out.println("getAllRoom");
        RoomBusiness instance = null;
        ArrayList<Room> expResult = null;
        ArrayList<Room> result = instance.getAllRoom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertUserRoom method, of class RoomBusiness.
     */
    @Test
    public void testInsertUserRoom() throws Exception {
        System.out.println("insertUserRoom");
        User u = null;
        Long id = null;
        RoomBusiness instance = null;
        Room expResult = null;
        Room result = instance.insertUserRoom(u, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

   
    
}
