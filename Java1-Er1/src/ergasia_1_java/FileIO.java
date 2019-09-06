

package ergasia_1_java;

import java.io.*;

public class FileIO
{
  // Γράφει το αντικείμενο 'obj' στο αρχείο 'filename'  
  static void objectTofile(Object obj , String filename)
  {
    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
      out.writeObject(obj);
      out.close();     
      System.out.printf("File '%s' saved successfully!\n",filename);
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
  // Φορτώνει ένα αντικείμενο από το αρχείο 'filename' και το επιστρέφει
  static Object objectFromfile(String filename)
  {
    Object obj = null;
    
    try
    {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      obj = in.readObject();
      in.close();
      fileIn.close();
    }
    catch(Exception ex)
    {
      System.out.println("Error encountered while opening file!");
      return null;
      //ex.printStackTrace();
    }
    
    return obj;
  }
   
}