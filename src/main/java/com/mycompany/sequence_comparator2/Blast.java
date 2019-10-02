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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    //launch blastx between the interested plante and the referenced proteins. parameter : String plante, observableList sequence of referenced proteins. return a list of sequence list
    public List<String> search(String plante,ObservableList<String> fasta,Integer coverpercent,Integer idpercent,boolean silence) throws InterruptedException, IOException 
    {   
        List<String> resultatBlast = new ArrayList<String>();

        // Detect OS running the application ...
        String os = System.getProperty("os.name");
        if ("Mac OS X".equals(os)) {
            // Set the path of the driver to driver executable. For Chrome, set the properties as following:       
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/driver/chromedriver");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        } else {
            // Set the path of the driver to driver executable. For Chrome, set the properties as following:       
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/driver/chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        }
        // Create a Chrome Web Driver with visual
        ChromeOptions options = new ChromeOptions();
        if(silence){
        options.addArguments("--headless");
        }
        // TO DO: add a  button to switch between the hidden and the visible option
//        Create a Chrome Web Driver without visual 
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Open the Blast homepage
        driver.get("https://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastx&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome");

        // Enter the sequence in the field
        driver.findElement(By.id("seq")).clear();
        LOGGER.info("fasta " + fasta.size());
        for (int i = 0; i < fasta.size(); i++) {
            driver.findElement(By.id("seq")).sendKeys(fasta.get(i));
        }

        // Enter the organism to study 
        driver.findElement(By.id("qorganism")).clear();
        driver.findElement(By.id("qorganism")).sendKeys(plante);

        //pause to allow the selector to be displayed 
        Thread.sleep(2000);
//        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        //click the first line of the selector
        WebElement el = driver.findElement(By.cssSelector(".ui-ncbiautocomplete-holder > .ui-ncbiautocomplete-options >li"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", el);
        el.click();

        // Click the FindJobs button for searching
        driver.findElement(By.id("b1")).click();

        // take parameters to select results 
        // TO DO : change to take user input value
//        Integer covery = 50;
//        Integer identity = 50;
        Integer covery = coverpercent;
        Integer identity = idpercent;

        LOGGER.info(" coverBlast " + covery + " identity " + identity);
        // allow the page change 
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

LOGGER.info("passe après");
// go through the input sequence list 

 for (int j=1;j<= fasta.size();j++){
     // select a sequence 

if(fasta.size()!=1){
     driver.findElement(By.cssSelector("dd>#queryList>option:nth-of-type("+j+")")).click();}

            // go through all of the table lines if there is/are result(s)
            if (driver.findElement(By.className("results-tabs")).isDisplayed()) {
                //get the number of results
                Integer nbline = Integer.valueOf(driver.findElement(By.cssSelector(".selctall>li>.small-text>#slcNum")).getText());
                // go through the result table
                for (int i = 0; i < nbline; i++) {
                    String text = driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.c7")).get(i).getText();
                    Integer identityx = Integer.valueOf(text.split("\\.")[0]);

                    String text2 = driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.c5")).get(i).getText();
                    Integer cover = Integer.valueOf(text2.split("%")[0]);
                    if (identityx < identity | cover < covery) {
                        WebElement element = driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.l.c0")).get(i);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
                        element.click();
                    }
                }

                //download the results on fasta format : 
                driver.findElement(By.cssSelector(".right-tools> li > #btnDwnld")).click();
                driver.findElement(By.cssSelector(".right-tools> li > #dsDownload > li > #dwFSTAl")).click();

                //A voir si il faut ajouter encore plus de temps d'attente pour les connexions lentes 
                Thread.sleep(5000);
                ResultFile resultfile = new ResultFile();
                Pattern p = Pattern.compile(">");
                List<String> fileResult = resultfile.readFile();
                boolean exist = false;

                //add all line of all file in resultBlast
                for (int l = 0; l < fileResult.size(); l++) {
                    LOGGER.info("nombre de séquences dans le fichier " + fileResult.size());
                    String seq = fileResult.get(l);
                    LOGGER.info("sequence " + seq);
                    String id = seq.split(":")[0];
                    LOGGER.info("id " + id);
                    Matcher matcher = p.matcher(id);

                    if (resultatBlast.isEmpty()) {
                        resultatBlast.add(seq);
                    }
                    for (int m = 0; m < resultatBlast.size(); m++) {
                        LOGGER.info("nombre de séquences en sortie de blast " + resultatBlast.size());
                        String idline = resultatBlast.get(m).split(":")[0];
                        LOGGER.info("id : " + id + " idline : " + idline);
                        if (id.equals(idline) && matcher.find()) {
                            exist = true;
//                fileResult.remove(l);
                            LOGGER.info("les meme donc remove " + fileResult.size());
                        }

                    }
                    if (exist == false) {
                        resultatBlast.add(fileResult.get(l));
                    }
                    exist = false;
                    LOGGER.info(" id " + id);
                }
                resultfile.deleteFile();
            }

        }
        // Close the browser
        driver.close();
        return resultatBlast;
    }
}
