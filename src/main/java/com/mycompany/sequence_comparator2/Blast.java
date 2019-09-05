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
        String namePlantURL="";
        for(int i=0;i<namePlante.length;i++){
	if(i==0){
            	namePlantURL=namePlantURL+namePlante[i];
            }else{
                       namePlantURL="+"+namePlantURL+namePlante[i];
         
        }
        
        
        // Set the path of the driver to driver executable. For Chrome, set the properties as following:
        
        File file = new File(System.getProperty("user.dir")+"/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        // Create a Chrome Web Driver
        //
        LOGGER.info("ta mère " + file);
        WebDriver driver = new ChromeDriver();
        //
        // Open the Indeed.com homepage
        //
        driver.get("https://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastx&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome");
        //
        // Enter the sequence in the field, What
        //
        driver.findElement(By.id("seq")).clear();
//        Thread.sleep(2000);
        driver.findElement(By.id("seq")).sendKeys(fasta);
        
        // Enter the organism to study 
       driver.findElement(By.id("qorganism")).clear();
//        Thread.sleep(2000);
        driver.findElement(By.id("qorganism")).sendKeys(plante);
//        WebElement test = driver.findElement(By.className("ui-ncbiautocomplete-holder shadow"));
//        LOGGER.info("test => " + test);
driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
WebElement el = driver.findElement(By.cssSelector(".ui-ncbiautocomplete-holder > .ui-ncbiautocomplete-options >li"));
//             WebElement el = driver.findElement(By.xpath("//div[@class='ui-ncbiautocomplete-holder shadow']/ul[@class='ui-ncbiautocomplete-options']/"));
         LOGGER.info("ok " + el);
             el.click();
//             WebElement test = driver.findElement(By.className("ui-ncbiautocomplete-options"));
//             test.findElement(By.cssSelector("li[valueid=medicago 3877]")).click();
         
//        LOGGER.log(Level.INFO, "test => {0}", test.getText());
        
//        List<WebElement> choice = new ArrayList<WebElement>();
//        choice.add(driver.findElement(By.name("menuitem")));
//        String patate = choice.get(0).getText();
//        LOGGER.info("patate => " + patate);
//        driver.findElement(By.id("qorganism")).clear();
//        driver.findElement(By.id("qorganism")).sendKeys(patate);
//        
//        //
//        // Click the FindJobs button for searching
//        //
       driver.findElement(By.id("b1")).click();

        //
        // Close the browser
        //
driver.close();
    }
}
}


