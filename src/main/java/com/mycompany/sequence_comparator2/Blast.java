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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Blast extends FXMLController {

    protected static final Logger LOGGER = Logger.getLogger(Blast.class.getName());

    public void Blast() {
    }

    public void search(String plante,String fasta) throws InterruptedException 
    {   
        // Set the path of the driver to driver executable. For Chrome, set the properties as following:       
        File file = new File(System.getProperty("user.dir") + "/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

        // Create a Chrome Web Driver with visual
        WebDriver driver = new ChromeDriver();
        // TO DO: add a  button to switch between the hidden and the visible option
//        Create a Chrome Web Driver without visual 
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Open the Blast homepage
        driver.get("https://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastx&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome");

        // Enter the sequence in the field
        driver.findElement(By.id("seq")).clear();
        driver.findElement(By.id("seq")).sendKeys(fasta);

        // Enter the organism to study 
        driver.findElement(By.id("qorganism")).clear();
        driver.findElement(By.id("qorganism")).sendKeys(plante);

        //pause to allow the selector to be displayed 
        Thread.sleep(3000);
//        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        //click the first line of the selector
        WebElement el = driver.findElement(By.cssSelector(".ui-ncbiautocomplete-holder > .ui-ncbiautocomplete-options >li"));
        el.click();

        // Click the FindJobs button for searching
        driver.findElement(By.id("b1")).click();

        // take parameters to select results 
        // TO DO : change to take user input value
        Integer covery = 50;
        Integer identity = 50;

        // allow the page change 
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        //get the number of results
        Integer nbline = Integer.valueOf(driver.findElement(By.cssSelector(".selctall>li>.small-text>#slcNum")).getText());

        // go through all of the table lines
        for (int i = 0; i < nbline; i++) {
            String text = driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.c7")).get(i).getText();
            Integer identityx = Integer.valueOf(text.split("\\.")[0]);

            String text2 = driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.c5")).get(i).getText();
            Integer cover = Integer.valueOf(text2.split("%")[0]);

            if (identityx < identity | cover < covery) {
                driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.l.c0")).get(i).click();
            }
        }

        //download the results on fasta format : 
        driver.findElement(By.cssSelector(".right-tools> li > #btnDwnld")).click();
        driver.findElement(By.cssSelector(".right-tools> li > #dsDownload > li > #dwFSTAl")).click();

        //A voir si il faut ajouter encore plus de temps d'attente pour les connexions lentes 
        Thread.sleep(5000);
        LOGGER.info("il a fini ");
        // Close the browser
        driver.close();
    }
}
