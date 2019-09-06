/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

/**
 *
 * @author Fievet
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Blast extends FXMLController{
     protected static final Logger LOGGER = Logger.getLogger(Blast.class.getName());
    public void Blast(){}
    

    public void search(String plante,String fasta) throws InterruptedException 
    {
        String[] namePlante = plante.split("");
        
        // Set the path of the driver to driver executable. For Chrome, set the properties as following:
        
        File file = new File(System.getProperty("user.dir")+"/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        // Create a Chrome Web Driver
        WebDriver driver = new ChromeDriver();
        // Open the Indeed.com homepage
        driver.get("https://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastx&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome");
        // Enter the sequence in the field
        driver.findElement(By.id("seq")).clear();
//        Thread.sleep(2000);
        driver.findElement(By.id("seq")).sendKeys(fasta);
        
        // Enter the organism to study 
       driver.findElement(By.id("qorganism")).clear();
//        Thread.sleep(2000);
        driver.findElement(By.id("qorganism")).sendKeys(plante);
        //pause to allow the selector to be displayed 
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        //click the first line of the selector
        WebElement el = driver.findElement(By.cssSelector(".ui-ncbiautocomplete-holder > .ui-ncbiautocomplete-options >li"));
             el.click();
       // Click the FindJobs button for searching
       driver.findElement(By.id("b1")).click();
        // Close the browser
//        driver.close();
}
}


