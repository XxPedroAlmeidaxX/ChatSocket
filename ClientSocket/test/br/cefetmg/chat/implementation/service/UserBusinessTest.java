/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.chat.implementation.service;

import br.cefetmg.chat.domain.User;
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
public class UserBusinessTest {
    
    public UserBusinessTest() {
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
     * Test of insertUser method, of class UserBusiness.
     */
    @Test
    public void testInsertUser() throws Exception {
        System.out.println("insertUser");
        User u = null;
        UserBusiness instance = null;
        User expResult = null;
        User result = instance.insertUser(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserById method, of class UserBusiness.
     */
    @Test
    public void testGetUserById() throws Exception {
        System.out.println("getUserById");
        Long id = null;
        UserBusiness instance = null;
        User expResult = null;
        User result = instance.getUserById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteUserById method, of class UserBusiness.
     */
    @Test
    public void testDeleteUserById() throws Exception {
        System.out.println("deleteUserById");
        Long id = null;
        UserBusiness instance = null;
        User expResult = null;
        User result = instance.deleteUserById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUserById method, of class UserBusiness.
     */
    @Test
    public void testUpdateUserById() throws Exception {
        System.out.println("updateUserById");
        Long id = null;
        User u = null;
        UserBusiness instance = null;
        User expResult = null;
        User result = instance.updateUserById(id, u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByIpAndName method, of class UserBusiness.
     */
    @Test
    public void testGetUserByIpAndName() throws Exception {
        System.out.println("getUserByIpAndName");
        Long ip = null;
        String name = "";
        UserBusiness instance = null;
        User expResult = null;
        User result = instance.getUserByIpAndName(ip, name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logarUser method, of class UserBusiness.
     */
    @Test
    public void testLogarUser() throws Exception {
        System.out.println("logarUser");
        String name = "";
        Long ip = null;
        UserBusiness instance = null;
        User expResult = null;
        User result = instance.logarUser(name, ip);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
