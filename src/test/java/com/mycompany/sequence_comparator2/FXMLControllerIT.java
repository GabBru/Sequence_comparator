/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brunetgabriel
 */
public class FXMLControllerIT {
    
    public FXMLControllerIT() {
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
     * Test of initialize method, of class FXMLController.
     */
    @org.junit.Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FXMLController instance = new FXMLController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomPlante method, of class FXMLController.
     */
    @org.junit.Test
    public void testGetNomPlante() throws Exception {
        System.out.println("getNomPlante");
        ConnectionDataBase dataAccess = null;
        FXMLController instance = new FXMLController();
        ObservableList<String> expResult = null;
        ObservableList<String> result = instance.getNomPlante(dataAccess);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTypeProt method, of class FXMLController.
     */
    @org.junit.Test
    public void testGetTypeProt() throws Exception {
        System.out.println("getTypeProt");
        String nom_plante = "";
        FXMLController instance = new FXMLController();
        ObservableList<String> expResult = null;
        ObservableList<String> result = instance.getTypeProt(nom_plante);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNomProt method, of class FXMLController.
     */
    @org.junit.Test
    public void testGetNomProt() throws Exception {
        System.out.println("getNomProt");
        String nom_plante = "";
        String nom_type_plante = "";
        FXMLController instance = new FXMLController();
        ObservableList<String> expResult = null;
        ObservableList<String> result = instance.getNomProt(nom_plante, nom_type_plante);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of launchBlast method, of class FXMLController.
     */
    @org.junit.Test
    public void testLaunchBlast() throws Exception {
        System.out.println("launchBlast");
        MouseEvent event = null;
        FXMLController instance = new FXMLController();
        instance.launchBlast(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeq_nom_plante method, of class FXMLController.
     */
    @org.junit.Test
    public void testGetSeq_nom_plante() {
        System.out.println("getSeq_nom_plante");
        FXMLController instance = new FXMLController();
        String expResult = "";
        String result = instance.getSeq_nom_plante();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeq method, of class FXMLController.
     */
    @org.junit.Test
    public void testGetSeq() {
        System.out.println("getSeq");
        FXMLController instance = new FXMLController();
        String expResult = "";
        String result = instance.getSeq();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
