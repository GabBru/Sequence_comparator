/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

import static com.mycompany.sequence_comparator2.Clustal.LOGGER;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author gphy
 */
public class Generate_tree {
    String tree;
    Clustal cl = new Clustal();
    public void Generate_tree(){
        tree = cl.getTree();
}
    
    public void submit() throws IOException, InterruptedException{
     // Set the path of the driver to driver executable. For Chrome, set the properties as following:       
        File file = new File(System.getProperty("user.dir")+"/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        
        // Create a Chrome Web Driver with visual
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        // Open the clustalo homepage
        driver.get("http://phylo.io/#");
        //add all lines in the input 
        driver.findElement(By.id("newickInSingle")).sendKeys(tree);
        //click on submit button 
        driver.findElement(By.id("renderButton")).submit();
    }
}
