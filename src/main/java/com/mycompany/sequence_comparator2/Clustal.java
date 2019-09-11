/*
use the clustal page and extract a tree 
 */
package com.mycompany.sequence_comparator2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Fievet
 */
public class Clustal {
    protected static final Logger LOGGER = Logger.getLogger(Clustal.class.getName());
    String tree ;
    public void Clustal(){}
    
    public void submit(List<List<String>> blastResult) throws IOException, InterruptedException{
//        ResultFile blastFile = new ResultFile();
//        List<String> sequences = blastFile.readFile();
     // Set the path of the driver to driver executable. For Chrome, set the properties as following:       
        File file = new File(System.getProperty("user.dir")+"/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        
        // Create a Chrome Web Driver with visual
        WebDriver driver = new ChromeDriver();
        // TO DO: add a  button to switch between the hidden and the visible option
//        Create a Chrome Web Driver without visual 
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Open the clustalo homepage
        driver.get("https://www.ebi.ac.uk/Tools/msa/clustalo/");
        //add all lines in the input 
        for(int i=0;i<blastResult.size();i++){
            for (int j=0;j<blastResult.get(i).size();j++){
        driver.findElement(By.id("sequence")).sendKeys(blastResult.get(i).get(j)+"\n");}
        }
        //click on submit button 
        driver.findElement(By.cssSelector("#jd_submitButtonPanel")).submit();
        // wait for the next page 
        Thread.sleep(20000);
        // extract the tree 
        driver.findElement(By.cssSelector(".actionPanel>li>#phylotree")).click();
        driver.findElement(By.cssSelector(".actionPanel > li > #tree")).click();
        tree = driver.findElement(By.cssSelector("pre")).getText();
        LOGGER.info("tree => "+ tree);
        Thread.sleep(5000);
        driver.close();
    }

    public String getTree() {
        return tree;
    }
    
}
