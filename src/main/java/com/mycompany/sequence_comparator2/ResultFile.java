/*
 classe to read and delete file downloaded from Blast
 when blast results are download and results extracted, we have to delete the file for the next Blast request 
otherwise the extraction won't work because of the new file name 
 */
package com.mycompany.sequence_comparator2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fievet
 */
public class ResultFile {
    protected static final Logger LOGGER = Logger.getLogger(ResultFile.class.getName());
    // the downloaded file 
    // TO DO : find how to change the path to work with the teacher computer
    private File file = new File("C:\\Users\\Fievet\\Downloads\\seqdump.txt");
    
    public void ResultFile(){}
    
    // read the file and extract sequences from it 
    public List<String> readFile() throws FileNotFoundException, IOException{
        // create a stream to be read 
    InputStream flux =new FileInputStream(file);
    InputStreamReader lecture=new InputStreamReader(flux);
    BufferedReader buff=new BufferedReader(lecture);
    
        // create a variable to put all sequences in a single line to be able to split them
    String ligne;
    String result = "";
    List<String> sequences = new ArrayList<String>();
        // concat all lines in a single one in result 
    while ((ligne=buff.readLine())!=null){
//    result = result+ligne;
sequences.add(ligne);
    }
        // split all sequences in a list of sequences 
//    for(int i=1;i<result.split(">").length;i++){
//        LOGGER.info("match "+ i + " : "+result.split(">")[i]);
//        sequences.add(">"+result.split(">")[i]);
//}
    // close the reader
    buff.close(); 
    return sequences;
}
    // method to delete the file
    public void deleteFile(){
    file.delete();
    }
}

