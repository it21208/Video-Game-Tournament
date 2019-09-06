
package ergasia_1_java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Tournament implements java.io.Serializable
{
  private String name;
  private Date startingdate;
  private Date endingdate;
  private String location;
  private ArrayList<VideoGame> games;
  private ArrayList<Gamer> gamers;
  private static Scanner in = new Scanner(System.in);
  
  
  
  /*      Constructors      */
  Tournament()
  {
    name = "";
    startingdate = null;
    endingdate = null;
    location = "";
    games = new ArrayList<VideoGame>();
    gamers = new ArrayList<Gamer>();
  }
  
  
  
  /*      Setters-Getters      */
  public String getName()
  {
    return name;
  }
  
  public Date getStartingdate()
  {
    return startingdate;
  }
  
  public Date getEndingdate()
  {
    return endingdate;
  }
           
  public String getLocation()
  {
    return location;
  }

  public ArrayList<VideoGame> getGames()
  {
    return games;
  }
  
  public ArrayList<Gamer> getGamers()
  {
    return gamers;
  }
  
  public void setName(String newname)
  {
    name = newname;
  }
  
  public void setStartingdate(Date newstartingdate)
  {
    startingdate = newstartingdate;
  }
  
  public void setEndingdate(Date newendingdate)
  {
    endingdate = newendingdate;
  }
           
  public void setLocation(String newlocation)
  {
    location = newlocation;
  }
  
  
  
  /*      Tournament methods      */
  public void viewTournamentInfo()
  {
    System.out.println("\nName: "+name);
    System.out.println("Location: "+location);
    System.out.println("Starting date: "+(new SimpleDateFormat("dd/MM/yy")).format(startingdate));
    System.out.println("Ending date: "+(new SimpleDateFormat("dd/MM/yy")).format(endingdate));
  }
  
  public void editTournament()
  {    
    Date today = new Date();
    
    System.out.print("Name: ");
    name = in.nextLine();
    System.out.print("Location: ");
    location = in.nextLine();
    
    String sdate,edate;
    do
    {
      System.out.print("Starting date (e.x 23/12/2014): ");
      sdate = in.nextLine();
    }while(!isValidDate(sdate) || stringToDate(sdate).before(today));         // πρέπει η ημερομηνία που εισήγαγε ο χρήστης να είναι έγκυρη(αν και θα έπρεπε όταν ο χρήστης βάζει και τη σημερινή ημερομηνία να το δέχεται, αυτό δεν συμβαίνει)
       
    do
    {
      System.out.print("Ending date: ");
      edate = in.nextLine();
    }while(!isValidDate(edate) || stringToDate(edate).before(stringToDate(sdate)) );         // πρέπει η ημερομηνία που εισήγαγε ο χρήστης να είναι έγκυρη  
    
    // σε αυτό το σημείο και οι δύο ημερομηνίες που εισήγαγε ο χρήστης είναι έγκυρες
    startingdate = stringToDate(sdate);
    endingdate = stringToDate(edate);
    
    System.out.print("\n\nChanges saved successfully!\n\n");
  }      
      
  
  
  /*      Date methods      */
  public boolean isValidDate(String inDate)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    dateFormat.setLenient(false);
    
    try
    {
      dateFormat.parse(inDate.trim());       // try to parse date from string
    }
    catch(ParseException pe)
    {
      return false;                          // if an excpetion has been caught, date is not valid
    }
    
    return true;
  }
  
  public Date stringToDate(String datestr)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    dateFormat.setLenient(false);
    
    try
    {
     return dateFormat.parse(datestr.trim());       // try to parse date from string
    }
    catch(ParseException pe)
    {
      return null;
    }
  }
 
}