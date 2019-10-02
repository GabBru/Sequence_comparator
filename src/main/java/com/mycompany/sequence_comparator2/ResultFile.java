/*
 class to read and delete file downloaded from Blast
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
    
    static boolean fileFound;
static String fileName = "seqdump.txt";
public static String searchFile(File f)     //File f is "C:\\"
{  
    String found ="";
   try
   {
    if(f.isDirectory())
    {
    File [] fi = f.listFiles();
    for(int i=0;i<fi.length;i++)
    {
    if(fileFound==true) 
    {
        found = "ok";
      break;
    } 
    //LOGGER.info("repository " + fi[i].getAbsolutePath());
    //System.out.println(fi[i].getName());
    searchFile(fi[i]);
    
    {
    if(f.getName().equalsIgnoreCase(fileName) ||  f.getName().toLowerCase().startsWith(fileName.toLowerCase())||(fi[i].getName().toLowerCase().endsWith(fileName.toLowerCase())))
    {    
    System.out.print("file found " + f.getAbsolutePath()); 
    fileFound=true;
    found = "oui";
    fileName=fi[i].getPath();
    return fi[i].getAbsolutePath();
    }
    }}}
   }
    catch(Exception e)
      {
          found = "non";
      }
        return fileName;
 }
    protected static final Logger LOGGER = Logger.getLogger(ResultFile.class.getName());
    // the downloaded file 
    // TO DO : find how to change the path to work with the teacher computer
    private File file = new File(searchFile(new File("/Users/")));
    String os = System.getProperty("os.name");
//    private File file = new File("C:\\Users\\gphy\\Downloads\\seqdump.txt");
    public void ResultFile(){}

    public File getFile() {
        return file;
    }
    
    // read the file and extract sequences from it 
    public List<String> readFile() throws FileNotFoundException, IOException {
        if ("Mac OS X".equals(os)) {
            file = new File("~/Downloads/seqdump.txt");
        } else {
            file = new File("C:\\Users\\Fievet\\Downloads\\seqdump.txt");
        }
        // create a stream to be read 
        InputStream flux = new FileInputStream(file);
        InputStreamReader lecture = new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(lecture);

        // create a variable to put all sequences in a single line to be able to split them
        String ligne;
        String seq1 = "";
        List<String> sequences = new ArrayList<String>();
        // concat all lines in a single one in result 
        Pattern p = Pattern.compile(">");
        while ((ligne = buff.readLine()) != null) {
            Matcher m = p.matcher(ligne);
            if (!m.find()) {
                seq1 = seq1 + ("\n");
                seq1 = seq1 + (ligne);

            } else {
                if (seq1.equals("")) {
                    seq1 = ligne;
                } else {
                    sequences.add(seq1);
                    seq1 = ligne;
                }
            }
        }
        // close the reader
        buff.close();
        LOGGER.info(sequences.get(0));
        return sequences;
    }

    // method to delete the file
    public void deleteFile() {
        file.delete();
    }
}
