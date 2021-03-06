package ESD15s1;

import java.io.*;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * @author Sebastian Rubio
 * (Created on Monday 20 April 2015)
 */
public class IOFiles {

    private static boolean writingStandardOutput = true;
    private final static PrintWriter standardOutput = new PrintWriter(System.out);  // wraps standard output stream
    private static PrintWriter out = standardOutput;   // Stream that data is written to; the current output destination.
    
    /**
     * This method count newlines in the input file
     * @param file is the input file name
     * @return count the quantity of new lines
     */
    public static int numNewlines(String file){
		Scanner s = null;		
		int count = 1;//it increases each time that there is a newline
		String line = null;
	    try {
	        s = new Scanner(new BufferedReader(new FileReader(file)));
	        boolean hasline = s.hasNextLine();
	        while (hasline) {            	
	        	line = s.nextLine();// read rest of the line            	
	        	if(line.equalsIgnoreCase("")){
	        		count++;	        	
            	}
	        } 	        
	    } catch (Exception e) {
	    	//System.out.println(e.getMessage());
	    	//e.printStackTrace();
	    }finally {
		    if (s != null) {		    	
		        s.close();
		    }
	    }
	    return count;
    }
    
    /**
     * This method write a new file
     * @param fileName is the output file name
     */
    public static void writeFile(String fileName) {
        if (fileName == null)  // Go back to reading standard output
            writeStandardOutput();
        else {
            PrintWriter newout;
            try {
                newout = new PrintWriter(new FileWriter(fileName));
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Can't open file \"" + fileName + "\" for output.\n"
                                 + "(Error :" + e + ")");
            }
            if (!writingStandardOutput) {
                try {
                    out.close();
                }
                catch (Exception e) {
                }
            }
            out = newout;
            writingStandardOutput = false;
        }
    }
    
    /**
     * This method write a new file. The second time this method is invoked, overwrite the output file 
     * @param fileName is the output file name
     */
    public static void writeFileT(String fileName) {
        if (fileName == null)  // Go back to reading standard output
            writeStandardOutput();
        else {
            PrintWriter newout;
            try {
                newout = new PrintWriter(new FileWriter(fileName,true));
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Can't open file \"" + fileName + "\" for output.\n"
                                 + "(Error :" + e + ")");
            }
            if (!writingStandardOutput) {
                try {
                    out.close();
                }
                catch (Exception e) {
                }
            }
            out = newout;
            writingStandardOutput = false;

        }
    }
    
    /**
     * After this method is called, output will be written to standard output (as it 
     * is in the default state).  If a file or stream was previously open for output, it
     * will be closed.
     */
    public static void writeStandardOutput() {
        if (writingStandardOutput)
            return;
        try {
            out.close();
        }
        catch (Exception e) {
        }
        out = standardOutput;
        writingStandardOutput = true;
    }
 
    /**
     * This is equivalent to print(x), followed by an end-of-line.
     */
    public static void putln(Object x) { 
        out.println(x);
        out.flush();
    }
    
    /**
     * This is equivalent to printf(x,y)
     */
    public static void putf(String format, Object items) {
        
        try {
            out.printf(format,items);
        }
        catch (IllegalFormatException e) {
            throw new IllegalArgumentException("Illegal format string in IOFile.putf() method.");
        }
        out.flush();
    }
}
