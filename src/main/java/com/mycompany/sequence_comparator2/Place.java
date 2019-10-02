/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

import static com.mycompany.sequence_comparator2.Blast.LOGGER;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Fievet
 */
public class Place {
    protected static final Logger LOGGER = Logger.getLogger(Place.class.getName());
//    ResultFile resultFile = new ResultFile();
    
    public void Place(){}
    //get ARNm sequences from invertase protein 
    public String blastN(String plante,List<String> sequences) throws InterruptedException
    {
         // Set the path of the driver to driver executable. For Chrome, set the properties as following:       
        File file = new File(System.getProperty("user.dir")+"/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        
        for (int i=0;i<sequences.size();i++){
        // Create a Chrome Web Driver with visual
        WebDriver driver = new ChromeDriver();
        // TO DO: add a  button to switch between the hidden and the visible option
//        Create a Chrome Web Driver without visual 
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
          // Open the Ncbi homepage
        driver.get("https://www.ncbi.nlm.nih.gov/gene");
        
//        // Enter the sequence in the field
//        driver.findElement(By.id("seq")).clear();
//        LOGGER.info("sequence taille " + sequences.size());
//        for (int i=0;i<sequences.size();i++){
//            System.out.println("liste fasta "+sequences.get(i));
//            driver.findElement(By.id("seq")).sendKeys(sequences.get(i));
//        }
//        System.out.println("après la boucle ");
// Enter the organism to study 
       driver.findElement(By.id("term")).clear();
        driver.findElement(By.id("term")).sendKeys(plante);
        driver.findElement(By.id("search")).click();
        driver.findElement(By.id("assembly_blast")).click();
        
    // Enter the sequences to blast 
        driver.findElement(By.id("seq")).sendKeys(sequences.get(i)+"\n");
        // launch the blast 
        driver.findElement(By.id("b1")).click();
        while(driver.findElement(By.cssSelector(".pageTitle")).isDisplayed()){LOGGER.info("not displayed ");
        Thread.sleep(20000);}
        LOGGER.info("partie de la boucle ");
        if(driver.findElement(By.cssSelector(".alignments")).isDisplayed()){
        //select the first result 
        driver.findElement(By.cssSelector(".alignments")).click();
        // click to go on ncbi 
        driver.findElement(By.cssSelector(".alnAll>div>div>span>span>a")).click();
        // then go on fasta sequence 
        driver.findElement(By.cssSelector(".aux>span>a")).click();
        //enter the sequence number
        String from = driver.findElement(By.cssSelector(".text")).getText();
        LOGGER.info(from);
        return "Ok";}
        else{return null;}
        // maybe wait until : usa-button-secondary is displayed 
        }
        return null;
        
    }
       
    public List<String> tBlastN(String plante,List<String> blastResult) throws IOException, InterruptedException
    {
        List<String> futurBlast = new ArrayList<>();
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

        // Open the tblastn page  
        driver.get("https://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=tblastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome");
        
//        // set the sequences into the input field
        driver.findElement(By.id("seq")).clear();
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        for (int i=0;i<blastResult.size();i++){
        driver.findElement(By.id("seq")).sendKeys(blastResult.get(i)+"\n");}
        
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
       Thread.sleep(50000);
//       driver.findElement(By.cssSelector(".deflnDesc")).click();
//       driver.findElement(By.cssSelector(".alnAll>div>div>span>span>a")).click();
//download the results on fasta format : 
        File seqfile = new File("C:\\Users\\Fievet\\Downloads\\seqdump.txt");
//        Integer nbline = Integer.valueOf(driver.findElement(By.cssSelector("#hjbt>a>span")).getText().split("\\ ")[0]);
// for (int i=1;i<= nbline;i++){
LOGGER.info(" taille de blastresult " + blastResult.size());
if (blastResult.size()!=1){
    LOGGER.info("blastResult >1 ");
    List<String> sequences = new ArrayList<String>();
     for(int i=1;i<=blastResult.size();i++)
     {
         LOGGER.info("tour => " +i );
         driver.findElement(By.cssSelector("dd>#queryList>option:nth-of-type("+i+")")).click();

        // go through all of the table lines if there is/are result(s)
//        if(driver.findElement(By.className("results-tabs")).isDisplayed()){
//        driver.findElement(By.cssSelector("dd>#queryList>option:nth-of-type(1)")).click();}
       driver.findElement(By.cssSelector(".selctall>li")).click();
       Thread.sleep(3000);
       driver.findElements(By.cssSelector(".dscTable > tbody >tr>td.l.c0")).get(0).click();
       Thread.sleep(3000);
        driver.findElement(By.cssSelector(".right-tools> li > #btnDwnld")).click();
        Thread.sleep(7000);
        driver.findElement(By.cssSelector(".right-tools> li > #dsDownload > li > #dwFSTAl")).click();
        
       Thread.sleep(15000);
       
       InputStream flux =new FileInputStream(seqfile);
        InputStreamReader lecture=new InputStreamReader(flux);
         BufferedReader buff=new BufferedReader(lecture);
    
    String ligne;
    String result = "";
        // concat all lines in a single one in result 
    while ((ligne=buff.readLine())!=null){
    result = result+"\n"+ligne;
    }
//  seqfile.delete();
    sequences.add(result.split(">")[1]);
//    for(int i=1;i<result.split(">").length;i++){
//        LOGGER.info("match "+ i + " : "+result.split(">")[i]);
//        sequences.add(">"+result.split(">")[i]);
//           sequences.add(ligne);
    buff.close();
     seqfile.delete();}
return sequences;
     }
LOGGER.info("passé ");
        return null;
    
//    LOGGER.info("a la fin " + sequences.get(0));
//    futurBlast.add(sequences.get(0));
//    sequences.clear();}
// 
//    LOGGER.info("futurBlast " + futurBlast.size());
       // TO DO : 
       // for all sequences go find the 1500 pb before the cDNA sequence obtained in blast (don't take the mARN)  
    } 
    public void place() throws FileNotFoundException, IOException
    {
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

        // Open the Ncbi homepage
        driver.get("https://www.dna.affrc.go.jp/PLACE/?action=newplace");
        // add a sequence to find cis element 
        //to do : recuperer les séquences obtenus en blastn et les mettre à la place de la séquence en dure 
        driver.findElement(By.className("seq")).sendKeys("1 tagaattatg tagttagata tagtttttaa actatttatt tattctctaa aaaaaaaact\n" +
"       61 atttatttat gtatttttta taacgcactc atattagtta tagataaatt caaatatcac\n" +
"      121 tacaaaaaaa gaagataaat tcaaatatta agcattcaaa ttagttatag ataaattcaa\n" +
"      181 atattactag tacgagtgga taggtttatc tgtcattagg tcttcgatct tagccattgg\n" +
"      241 tgagagaatt acggccaaaa caatgaatta tccaaatcct atttttttta accttcttaa\n" +
"      301 tcgcaacttc ctcccgtacg cactttattt taacgtatct ttctttacac tttctgtatt\n" +
"      361 tcctaatttc ctttcctatg ctaattaaat agaaaagtaa gtaagtacta tccgttttgt\n" +
"      421 tcttgattat atctaatggt tctctcaaag gttgtaacga tctttgttgc ggttctctcc\n" +
"      481 attagtttat tgcttataaa caatggtgtt gaagcatttc ataaagtata ccctcacctt\n" +
"      541 caatctgttt cagccatttc cgtgagcgaa gtacatagaa ctggatacca ttttcaacct\n" +
"      601 cctaggaact ggatcaacgg ttcgtaacat tcttgctttt taaccctact ttattatgaa\n" +
"      661 tttatgatat attttcggtg cacttttttg tgattcttga attattgtga tgattatacc\n" +
"      721 ataatggttg gttctacgtc cataatggag atatattttt tttgtggaga ttgcattatt\n" +
"      781 atataaaatc taaaatgata ataactatct gagggcgcat aataacttga atacaatagt\n" +
"      841 ggtaggctgg ataaattgtg acgatataac attgaacaac attattgatt ttacaaaaaa\n" +
"      901 gatttttgaa ccatggtgtg atacaaataa attataaata cttcgaccct taacgatttg\n" +
"      961 gtctagagtt cgatctcaaa ccgtgtatgt gacgtaaagc ggttatatta acaatcgttt\n" +
"     1021 tttacctcct tttatagttg ctgttgcacc attttgaaag gaaataaaaa ttacagggaa\n" +
"     1081 gtcacaaaaa tcaatttgta tctctgaacc tgatatgatg agaatagctt aaaattgaat\n" +
"     1141 gaatattaat atttttttat tatatttttg ataaattaaa gtgaataatt ttctacacaa\n" +
"     1201 aaatatatta tatttcaata acacattatt tgatattttt atttcatcat aaagtttgat\n" +
"     1261 gcataaaccc tcaatttatt ttttacatat aagtatgtta accaaggtta aaaaagggtt\n" +
"     1321 aagctaacaa gtacctttaa aactcttctt aaagaatcaa aaatgaaaat tattggtaaa\n" +
"     1381 ttttatgtaa aaaaagttgt ttttttaagt ttcaatatat atatagaata cataattttt\n" +
"     1441 aaaataaaat tttcccttca gattttttaa caagtgttac aaggtcaata tttagggtgg\n" +
"     1501 ccatgattcc tctgtgctca ctttaatgaa gtttgcttat aaaaggaaga gtcaaaaagg\n" +
"     1561 aggatatgat tttaatggtg gagagaaaga aaaataactt tttctcctaa atattttgga\n" +
"     1621 ggcttaaata tgtatttcaa acgaccccta gttgcaagga tgaaatacat gtttaaactt\n" +
"     1681 attttggata aaagttgaaa tcaagtgaga ggagttttgt tctcttggat tccgctttcc\n" +
"     1741 tctcccatta aatcttgatc cagaaaatat aacttaaata attccttttc agttttcatc\n" +
"     1801 ttcaattatg ctaactttat ttgcttactg attctttcat ggtttctaaa ttggtgtaat\n" +
"     1861 ggacgcatca atcacatgca gacccaaatg gtaagtaaaa ttcgcatttg actttttttt\n" +
"     1921 tttttacttc atttgtcaat gtccttcgta ttatctgcat gtacttgaac taattattta\n" +
"     1981 tgaaccatgc ag");
        //click on submit button
        driver.findElement(By.cssSelector(".formed>form>input:nth-of-type(3)")).click();
        
        //recuperer le resultat : la galère 
        String resultat = driver.findElement(By.tagName("pre")).getText();
       String table= resultat.split("____________________________________________________________________________")[1];
       LOGGER.info("nb parts "+table.split("\n").length);
    List<String> sequences = new ArrayList<String>();
       for(int i=1;i<table.split("\n").length;i++)
       {
           sequences.add(table.split("\n")[i]);
       }

//       InputStream flux =new FileInputStream(table);
//    InputStreamReader lecture=new InputStreamReader(flux);
//    BufferedReader buff=new BufferedReader(lecture);
//    String ligne;
//        // concat all lines in a single one in result 
//    while ((ligne=buff.readLine())!=null){
////    result = result+ligne;
//sequences.add(ligne);
//    }
//        LOGGER.info("resultat 1 => "+sequences.get(1));
//        
    }
}
