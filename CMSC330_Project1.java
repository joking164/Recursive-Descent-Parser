package cmsc330_project1;

//Imports for reading file
import java.io.*;
import java.util.Scanner;

public class CMSC330_Project1 
{
    public static void main(String[] args)throws Exception
    {
        //File created
        File file = new File("C:\\Users\\Josep_000\\Documents\\NetBeansProjects\\CMSC330_Project1\\test4.txt");
        
        try
        {
            //Variables to read and store input of file
            Scanner read = new Scanner(file);
            Scanner store = new Scanner(file);
            
            //Loop that outputs file in format of txt file
            while(read.hasNext())
            {
                System.out.println(read.nextLine());
            }
            
            //String array to store input
            String[] input = new String[99];
            
            //Loop to store input
            for(int i=0; store.hasNext(); i++)
            {
                input[i] = store.next();
            }
            
            //Call to recursive descent parser
            RecursiveDescentParserGUI run = new RecursiveDescentParserGUI(input);
        }
        catch(Exception e)
        {
            System.out.println("File could not be read");
        }
    }   
}