
package ergasia_1_java;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Secretary
{
  private static Scanner in = new Scanner(System.in);
  
    public static void main(String[] args)
    {    
      Tournament tournament = new Tournament();
      String savefile = "tournament";
      
      System.out.print("1) New Tournament\n2) Open\n3) Exit\n\n");
      int option;
      do
      {
        option = ForceInputInt("Option: ");
      }while(option <= 0 || option > 3);
       
      if(option == 1)     // create new tournament
      {
        tournament.editTournament();        
      }
      else if(option == 2)      // load tournament from file
      {
        tournament = (Tournament) FileIO.objectFromfile(savefile);
        
        if(tournament == null)   // if error encoutered, exit
         System.exit(1);
      }
      else     // exit
      {
        System.exit(0); 
      }
      
            
      
      while(true)
      {
        
        System.out.print("\n1) Tournament\n2) Games\n3) Players\n4) Open\n5) Save\n6) Exit\n\n");
        do
        {
          option = ForceInputInt("Option: ");
        }while(option <= 0 || option > 6);
        
        
        switch(option)
        {
          case 1:                                                               // Tournament
              System.out.print("1) View Tournament Info\n2) Edit\n3) Back\n\n");
              do
              {
                option = ForceInputInt("Option: ");
              }while(option <= 0 && option > 3);
 
              if(option == 1)
                tournament.viewTournamentInfo();
              else if(option ==  2)
                tournament.editTournament();  
          break;
          case 2:                                                               // Games
              System.out.print("1) Add\n2) Edit\n3) Delete\n4) View Game Info\n5) List Games\n6) List Players in Game\n7) Progress\n8) Main Menu\n\n");
              do
              {
               option = ForceInputInt("Option: ");
              }while(option <= 0 || option > 8);
              
              if(option == 1)
                addGame(tournament);
              else if(option ==  2)
                editGame(tournament);
              else if(option ==  3)
                deleteGame(tournament);
              else if(option ==  4)
                viewGameinfo(tournament);
              else if(option ==  5)
                listGames(tournament);
              else if(option ==  6)
                listGamersInGame(tournament);
              else if(option ==  7)
              {
                System.out.print("1) Show progress\n2) Edit progress\n3) Start Matches\n4) Main Menu\n\n");
                do
                {
                  option = ForceInputInt("Option: ");
                }while(option <= 0 || option > 4);
                
                if(option == 1)
                  showProgress(tournament);
                else if(option ==  2)
                  editProgress(tournament);
                else if(option ==  3)
                  startMatches(tournament);
              }  
           break;  
          case 3:                                                               // Players
            System.out.print("1) Add\n2) Edit\n3) Delete\n4) View Player Info\n5) List Players\n6) List Games that Player has signed up in\n7) Add Player to Game\n8) Remove Player from Game\n9) Main Menu\n\n");
            do
            {
              option = ForceInputInt("Option: ");
            }while(option <= 0 || option > 9);
            
            if(option == 1)
              addGamer(tournament);
            else if(option == 2)
              editGamer(tournament);
            else if(option == 3)
              deleteGamer(tournament);
            else if(option == 4)
              viewGamerinfo(tournament);
            else if(option == 5)
              listGamers(tournament);
            else if(option == 6)
              listGamerSignedupGames(tournament);      
            else if(option == 7)
              addGamerToGame(tournament);
            else if(option == 8)
              removeGamerFromGame(tournament);                
          break;
          case 4:                                                               // Open
             //  System.out.print("File name: ");
             //  String filename = in.nextLine();
             tournament = (Tournament) FileIO.objectFromfile(savefile);
             System.out.println("Save file loaded successfully!\n");
          break;  
          case 5:                                                               // Save 
              FileIO.objectTofile(tournament,savefile);
              System.out.println("Changes saved successfully!\n");
          break;
          case 6:                                                               // Exit
            System.exit(0);
          break;       
        } 
       }
   
    }
    
    
    
    
    
    
    
    
      /*      VideoGame methods      */
  private static void addGame(Tournament tournament)
  {   
    ArrayList<VideoGame> games = tournament.getGames();
    System.out.print("Name: ");
    String gamename = in.nextLine();

    
    // Εισαγωγή παιχνιδιού
    String gamestartingdate;
    do
    {
      System.out.print("Starting date (e.x 22/12/2014): ");
      gamestartingdate = in.nextLine();
    }while(!tournament.isValidDate(gamestartingdate) || tournament.stringToDate(gamestartingdate).before(tournament.getStartingdate()) || tournament.stringToDate(gamestartingdate).after(tournament.getEndingdate()) );         // force valid date   
   

    int gamemaxplayers;
    do
     {
       gamemaxplayers = ForceInputInt("Max players(at least 2): ");
     }while(gamemaxplayers < 2);
    
    int option;
     do
     {
       option = ForceInputInt("Game Type: 1)Time Match"+"\n           2)Round Match\noption: ");
     }while(option != 1 && option != 2);
        
    if(option == 1)      // create a time match game
    {
      int gamematchtime;
      do
      {
        gamematchtime = ForceInputInt("Match time(minutes): ");
      } while(gamematchtime <= 0);
      games.add(new TimeGame(gamename,tournament.stringToDate(gamestartingdate),gamemaxplayers,gamematchtime));   // δημιουργία και εισαγωγή παιχνιδιού τύπου "χρόνου"
    }
    else                // create a round match game
    {
      int gamemaxrounds;
      do
      {
        gamemaxrounds = ForceInputInt("Max rounds(has to be odd): ");
      } while(gamemaxrounds <= 0 || gamemaxrounds%2==0);   
      games.add(new RoundGame(gamename,tournament.stringToDate(gamestartingdate),gamemaxplayers,gamemaxrounds));   // δημιουργία και εισαγωγή παιχνιδιού τύπου "γύρου"
    }
    
    System.out.println("\nGame added successfully! Now you need to add a manager for this game.\n");    
    
    // Εισαγωγή διαχειριστή
    String fname,lname,nname,phonenumber;
    do
    {
      System.out.print("First Name: ");
      fname = in.nextLine();
    }while(!isValidName(fname));
    
    do
    {
      System.out.print("Last Name: ");
      lname = in.nextLine();
    }while(!isValidName(lname));
        
    System.out.print("Nickname: ");
    nname = in.nextLine();
    
    do
    {
      System.out.print("Phonenumber: ");
      phonenumber = in.nextLine();
    }while(!isValidPhonenumber(phonenumber));
    
    games.get(games.size()-1).setAdministrator(new Administrator(fname,lname,nname,phonenumber));   // Δημιουργία και εισαγωγή διαχειριστή
    
    System.out.println("\nManager has been added successfully! You 're good to go.");     
  }
  
  private static void editGame(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
        
    // έλεγχος για το αν υπαρχουν παιχνίδια
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
      
    // Επεξεργασία παιχνιδιού
    int gameindex;
    do
     {
       gameindex = ForceInputInt("Select Game: ");
     }while(gameindex <= 0 || gameindex > games.size());         // πρέπει το index να είναι στα όρια του πίνακα games
           
    gameindex--;
    
    System.out.println("Edit game "+games.get(gameindex).getName());    
    System.out.print("Name: ");
    String gamename = in.nextLine();
 
    String gamestartingdate;
    do
    {
      System.out.print("Starting date (e.x 22/12/2014): ");
      gamestartingdate = in.nextLine();
    }while(!tournament.isValidDate(gamestartingdate) || tournament.stringToDate(gamestartingdate).before(tournament.getStartingdate()) || tournament.stringToDate(gamestartingdate).after(tournament.getEndingdate()) );         // force valid date   
   
    
    int gamemaxplayers;
    do
     {
       gamemaxplayers = ForceInputInt("Max players(at least 2): ");
     }while(gamemaxplayers < 2);
    
     // make sure user enters correct match type
    int option;
     do
     {
       option = ForceInputInt("Game Type: 1)Time Match"+"\n           2)Round Match\noption: ");
     }while(option != 1 && option != 2);
        
    if(option == 1)      // create a time match game
    {
      int gamematchtime;
      do
      {
        gamematchtime = ForceInputInt("Match time(minutes): ");
      } while(gamematchtime <= 0);
      games.set(gameindex,new TimeGame(gamename,tournament.stringToDate(gamestartingdate),gamemaxplayers,gamematchtime));   // replace game
    }
    else                // create a round match game
    {
      int gamemaxrounds;
      do
      {
        gamemaxrounds = ForceInputInt("Max rounds: ");
      } while(gamemaxrounds <= 0);   
      games.set(gameindex,new RoundGame(gamename,tournament.stringToDate(gamestartingdate),gamemaxplayers,gamemaxrounds));   // replace game
    }
    
    System.out.println("\nGame edited successfully! Now edit manager.");    
  
    String fname,lname,nname,phonenumber;
    do
    {
      System.out.print("First Name: ");
      fname = in.nextLine();
    }while(!isValidName(fname));
    
    do
    {
      System.out.print("Last Name: ");
      lname = in.nextLine();
    }while(!isValidName(lname));
        
    System.out.print("Nickname: ");
    nname = in.nextLine();
    
    do
    {
      System.out.print("Phonenumber: ");
      phonenumber = in.nextLine();
    }while(!isValidPhonenumber(phonenumber));
    
    games.get(gameindex).setAdministrator(new Administrator(fname,lname,nname,phonenumber));
    
    System.out.println("\nManager adited successfully! You 're good to go.");
     
  }
 
  private static void deleteGame(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
    
    int gameindex;
    do
     {
       gameindex = ForceInputInt("Select Game: ");
     }while(gameindex <= 0 || gameindex > games.size());         // πρέπει το index να είναι στα όρια του πίνακα games
    
    gameindex--;
    
    games.remove(gameindex);  // διαγραφή του παιχνιδιού απο τον πίνακα games
    
    System.out.println("Game removed successfully!");  
  }  
  
  private static void viewGameinfo(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
        
    int gameindex;
    do
     {
       gameindex = ForceInputInt("Select Game: ");
     }while(gameindex <= 0 || gameindex > games.size());         // πρέπει το index να είναι στα όρια του πίνακα games
    
   gameindex--; 
    
   System.out.println("Name: "+games.get(gameindex).getName());
   System.out.println("Starting date: "+(new SimpleDateFormat("dd/MM/yy")).format(games.get(gameindex).getStartingdate()));
   System.out.println("Max players: "+games.get(gameindex).getMaxgamers());
   System.out.println("Manager: "+games.get(gameindex).getAdministrator().getFirstname()+" "+games.get(gameindex).getAdministrator().getLastname()+", '"+games.get(gameindex).getAdministrator().getNickname()+"', "+games.get(gameindex).getAdministrator().getPhonenumber());
   
   if(games.get(gameindex) instanceof TimeGame)    // τύπος παιχνιδιού "χρόνου"
   {
     System.out.println("Type: time match");
     System.out.println("Match time: "+((TimeGame)games.get(gameindex)).getMatchtime()+" minutes");
   }
   else                                             // τύπος παιχνιδιού "γύρων"
   {
     System.out.println("Type: round match");
     System.out.println("Max rounds: "+((RoundGame)games.get(gameindex)).getMaxrounds());
   }
    
  }
  
  private static void listGames(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
    
    System.out.println();
    for(int i=1 ; i<=games.size() ; i++)
      System.out.printf("%d) %s\n",i,games.get(i-1).getName());
    System.out.println();
  }
  
  private static void listGamersInGame(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    int gameindex;
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
    
    do
    {
      gameindex = ForceInputInt("Select game: ");
    }while(gameindex <= 0 || gameindex > games.size());
    
    gameindex--;
    
    if(games.get(gameindex).gamers.isEmpty())
    {
      System.out.println("\nThere are no players in this game!\n");
      return;
    }
        
    System.out.println();
    for(int i=1 ; i<=games.get(gameindex).numofGamers() ; i++)   // για όλους τους παίκτες εκτυπώνεται το όνομα και το επίθετο
      System.out.printf("%d) %s %s\n",i,games.get(gameindex).getGamer(i-1).getFirstname(),games.get(gameindex).getGamer(i-1).getLastname());
    System.out.println();
  }
  
  private static void showProgress(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
    
    int gameindex;
    do
     {
       gameindex = ForceInputInt("Select Game: ");
     }while(gameindex <= 0 || gameindex > games.size());         // make sure index is not out of bounds
           
    gameindex--;
    
    
    // Εμφάνιση προόδου παιχνιδιού
    if(games.get(gameindex).numofRounds() == 0)
    {
      System.out.println("\nMatches have not been started!\n");
      return;
    }
    
    // για κάθε γύρο από το παιχνίδι, εκτυπώνεται κάθε ματς(αντίπαλοι και βαθμολογία) και ο παίκτης που δεν έχει παίξει σε αυτό το γύρο
    for(int roundindex=0 ; roundindex<games.get(gameindex).numofRounds() ; roundindex++ )
    {
      Gamer inactiveplayer = games.get(gameindex).getRound(roundindex).getInactive();
      System.out.printf("\nRound %d\n",roundindex+1);
      for(int matchindex=0 ; matchindex<games.get(gameindex).getRound(roundindex).numOfMatches() ; matchindex++)
      {          
        System.out.printf("   %d) ",matchindex+1);
        System.out.printf("%s %s %s",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getFirstname()
                                    ,games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getLastname()
                                    ,games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().isResignedIn(games.get(gameindex).getRound(roundindex))?"(Resigned)":"");
        System.out.print("    vs    ");
        System.out.printf("%s %s %s",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getFirstname()
                                    ,games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getLastname()
                                    ,games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().isResignedIn(games.get(gameindex).getRound(roundindex))?"(Resigned)":""); 
        
        
        if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().isResignedIn(games.get(gameindex).getRound(roundindex))  || (games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().isResignedIn(games.get(gameindex).getRound(roundindex)) && games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().isResignedIn(games.get(gameindex).getRound(roundindex))))
           System.out.printf("      ( %s %s to next round )\n",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getFirstname()
                                                            ,games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getLastname()); 
        else if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().isResignedIn(games.get(gameindex).getRound(roundindex)))
          System.out.printf("      ( %s %s to next round )\n",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getFirstname()
                                                            ,games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getLastname());
        else if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).isPending(games.get(gameindex).getRound(roundindex)))
          System.out.println("      ( Pending )");  
        else
          System.out.printf("      ( %d - %d )\n",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerApoints(),games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerBpoints());
      
      
       // αν υπάρχει μόνο ένα ματς στο γύρο και έχει τελειώσει και δεν υπάρχει παίκτης που δεν έχει παίξει(ή αν υπάρχει έχει παραιτηθεί), υπάρχει νικητής οπότε εκτυπώνεται
       if(games.get(gameindex).getRound(roundindex).numOfMatches() == 1 && !games.get(gameindex).getRound(roundindex).getMatch(matchindex).isPending(games.get(gameindex).getRound(roundindex)) && (inactiveplayer == null || (inactiveplayer != null && inactiveplayer.isResignedIn(games.get(gameindex).getRound(roundindex)))))
       {
         if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerApoints() > games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerBpoints()) 
           System.out.printf("Winner:   %s %s\n\n",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getFirstname(),games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getLastname());
         else
           System.out.printf("Winner:   %s %s\n\n",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getFirstname(),games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getLastname());  
       }
      }
      
      Gamer inactive = games.get(gameindex).getRound(roundindex).getInactive();
      if(inactive != null)      // εκευπώνεται ο παίκτης που δεν έχει παίξει(αν υπάρχει)
        System.out.printf("      %s %s  (Inactive) %s\n",inactive.getFirstname(),inactive.getLastname(),inactive.isResignedIn(games.get(gameindex).getRound(roundindex))?"(Resigned)":"");
    }
    
  
    
  }
  
  private static void editProgress(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
    
    int gameindex;
    do
    {
       gameindex = ForceInputInt("Select Game: ");
    }while(gameindex <= 0 || gameindex > games.size());         // πρέπει το index να είναι εντός ορίων του πίνακα games
           
    gameindex--;
    
    if(games.get(gameindex).numofRounds() == 0)
    {
      System.out.println("\nMatches have not been started!\n");
      return;
    }
    
    int roundindex = games.get(gameindex).numofRounds()-1;
      
    int matchindex;
    do
     {
       matchindex = ForceInputInt("Select Match: ");
     }while(matchindex <= 0 || matchindex > games.get(gameindex).getRound(roundindex).numOfMatches());    // make sure index is not out of bounds
           
    matchindex--;
    
    // αν υπάρχει έστω και ένα παίκτης που έχει παραιτηθεί σε αυτό το ματς, το ματς δεν μπορεί να επεξεργαστεί(δεν υπάρχει νόημα να δωθεί βαθμολογία για ματς που δεν έχει παιχτεί)
    if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().isResignedIn(games.get(gameindex).getRound(roundindex)) || games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().isResignedIn(games.get(gameindex).getRound(roundindex)))
    {
      System.out.println("\nProgress could not be edited! There is at least a player who is resigned.\n");
      return;
    }
    
    int playerApoints,playerBpoints;
    do
     {
       System.out.printf("%s %s   points: ",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getFirstname(),games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().getLastname());
       playerApoints = ForceInputInt("");
     }while(playerApoints < 0);

    do
     {
       System.out.printf("%s %s   points: ",games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getFirstname(),games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().getLastname());
       playerBpoints = ForceInputInt("");
     }while(playerBpoints < 0);
    
    // το άθροισμα των πόντων και των δύο παικτών για αυτό το ματς δεν πρέπει να υπερβαίνει τη μέγιστη βαθμολογία που επιτρέπεται
    if(games.get(gameindex) instanceof RoundGame)
    {
      if(playerApoints+playerBpoints > ((RoundGame)games.get(gameindex)).getMaxrounds())
      {  
        System.out.printf("\nMaximum number of rounds: %d    , results have not been saved.",((RoundGame)games.get(gameindex)).getMaxrounds());
        return;
      }
    }
    
  
    games.get(gameindex).getRound(roundindex).getMatch(matchindex).setPlayerPoints(playerApoints, playerBpoints);


    for(matchindex=0 ; matchindex<games.get(gameindex).getRound(roundindex).numOfMatches() ; matchindex++)
    {  
     if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).isPending(games.get(gameindex).getRound(roundindex)))   // match has not been finished
       break;
    }
    
    // έλεγχος για δημιουργία νέου γύρου
    if(matchindex == games.get(gameindex).getRound(roundindex).numOfMatches())
    {
      Gamer inactiveplayer = games.get(gameindex).getRound(roundindex).getInactive();
      if(matchindex > 1 || (matchindex == 1 && inactiveplayer != null && !inactiveplayer.isResignedIn(games.get(gameindex).getRound(roundindex))))                    // if there are more than one finished matches in this round or there is only 1 match(and it has finisthed) but there is also an inactive player(who is not resigned), create next round
        games.get(gameindex).newRound();
    }
  }
  
  private static void startMatches(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(games.isEmpty())
    {
      System.out.println("\nThere are no games!\n");
      return;
    }
    
    int gameindex;
    do
     {
       gameindex = ForceInputInt("Select Game: ");
     }while(gameindex <= 0 || gameindex > games.size());         // make sure index is not out of bounds
           
    gameindex--;
    
    if(games.get(gameindex).numofGamers() < 2)               // in order for matches to begin, there has to be at least 2 gamers signed up
    {
      System.out.println("\nNot enough players!\n");
      return;
    }
    
    if(games.get(gameindex).numofRounds() != 0)
    {
      System.out.println("\nMatches have already been started\n");
      return;
    }
    
    if(games.get(gameindex).numofGamers() < 2)
    {
      System.out.println("\nThere are not enough players for this game to start matches!\n");
      return;
    }
    
    // δημιουργία του 1ου γύρου
    games.get(gameindex).newRound();
  }
  
  private static boolean isGameFinished(Tournament tournament,int gameindex)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(gameindex < 0 || gameindex >= games.size())      // έλεγχος για index ώστε να είναι εντός ορίων
      return false;
    
    if(games.get(gameindex).numofRounds() == 0)         // έλεγχος για το αν έχουν ξεκινίσει οι αγώνες για το συγκεκριμένο παιχνίδι
      return false;
   
    int roundindex = games.get(gameindex).numofRounds()-1;      // τελευταίος γύρος

    // ματς έχουν τελειώσει αν υπάρχει ένα ματς(στον τελευταίο γύρο) και έχει τελειώσει
    return games.get(gameindex).getRound(roundindex).numOfMatches() == 1 && !games.get(gameindex).getRound(roundindex).getMatch(0).isPending(games.get(gameindex).getRound(roundindex));
  }
  
  private static boolean isGameStarted(Tournament tournament,int gameindex)
  {
    ArrayList<VideoGame> games = tournament.getGames();
    
    if(gameindex < 0 || gameindex >= games.size())      // έλεγχος για index ώστε να είναι εντός ορίων
      return false;
    
    return games.get(gameindex).numofRounds() != 0;      // αν υπάρχει έστω και ένας γύρο για αυτό το παιχνίδι, τα ματς έχουν ξεκινίσει
 }
  
  
  
  /*      Gamer methods      */
  private static void addGamer(Tournament tournament)
  {    
    ArrayList<Gamer> players = tournament.getGamers();
    
    String fname,lname,nname,email;
    do
    {
      System.out.print("First Name: ");
      fname = in.nextLine();
    }while(!isValidName(fname));
    
    do
    {
      System.out.print("Last Name: ");
      lname = in.nextLine();
    }while(!isValidName(lname));
        
    System.out.print("Nickname: ");
    nname = in.nextLine();
    
    do
    {
      System.out.print("Email: ");
      email = in.nextLine();
    }while(!isValidEmail(email));
    
    players.add(new Gamer(fname,lname,nname,email));           // δημιουργία και εισαγωγή παίκτη
    
    System.out.println("\nPlayer added successfully!");    
  }
    
  private static void editGamer(Tournament tournament)
  {        
    ArrayList<Gamer> players = tournament.getGamers();
    
    if(players.isEmpty())
    {
      System.out.println("\nThere are no players!\n");
      return;
    }
    
    int playerindex;
    do
     {
       playerindex = ForceInputInt("Select player: ");
     }while(playerindex <= 0 || playerindex > players.size());      // έλεγχος για index ώστε να είναι εντός ορίων
           
    playerindex--;

    String fname,lname,nname,email;
    do
    {
      System.out.print("First Name: ");
      fname = in.nextLine();
    }while(!isValidName(fname));
    
    do
    {
      System.out.print("Last Name: ");
      lname = in.nextLine();
    }while(!isValidName(lname));
        
    System.out.print("Nickname: ");
    nname = in.nextLine();
    
    do
    {
      System.out.print("Email: ");
      email = in.nextLine();
    }while(!isValidEmail(email));
    
    players.set(playerindex,new Gamer(fname,lname,nname,email));   // δημιουργία και εισαγωγή νέου παίκτη στη θέση αυτού που επεξεράστηκε(στην πραγματικότητα δεν επεξεργάστηκε, σβήστηκε και δημιουργήθηκε νέος στη θέση του)
    
    System.out.println("\nPlayer edited successfully!");
  }
  
  private static void deleteGamer(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();    
    ArrayList<Gamer> players = tournament.getGamers();
    
    int playerindex;
    do
    {
      playerindex = ForceInputInt("Select player: ");
    }while(playerindex <= 0 || playerindex > players.size());      // έλεγχος για index ώστε να είναι εντός ορίων      
    playerindex--;
    
    if(games.isEmpty())                 // αν δεν υπάρχουν παιχνίδια διαγραφή παίκτη και έξοδος
    {
      players.remove(playerindex);
      System.out.println("Player has been deleted!\n");
      return;
    }
    
    // για κάθε παιχνίδι
    for(int gameindex=0 ; gameindex<games.size() ; gameindex++)
    {
      if(games.get(gameindex).containsGamer(players.get(playerindex)))       // αν ο παίκτης έχει δηλώσει συμμετοχή στο συγκεκριμένο παιχνίδι, δεν μπορεί να διαγραφεί(πρέπει να διαγραφεί πρώτα από το παιχνίδι)
      {
        System.out.println("Player could not be deleted!");
        break;
      }
      else
      {
        players.remove(playerindex);
        System.out.println("Player has been deleted!\n");    
      }
    }
  }
  
  private static void viewGamerinfo(Tournament tournament)
  {
    ArrayList<Gamer> players = tournament.getGamers();    
    if(players.isEmpty())
    {
      System.out.println("\nThere are no players!\n");
      return;
    }
        
    int playerindex;
    do
     {
       playerindex = ForceInputInt("Select player: ");
     }while(playerindex <= 0 || playerindex > players.size());      // έλεγχος για index ώστε να είναι εντός ορίων
    
   playerindex--; 
    
   System.out.println("First Name: "+players.get(playerindex).getFirstname());
   System.out.println("Last Name: "+players.get(playerindex).getLastname());
   System.out.println("Nickname: "+players.get(playerindex).getNickname());
   System.out.println("Email: "+players.get(playerindex).getEmail());
  }
  
  private static void listGamers(Tournament tournament)
  {
    ArrayList<Gamer> players = tournament.getGamers();
    
    if(players.isEmpty())
    {
      System.out.println("\nThere are no players!\n");
      return;
    }
    
    System.out.println();
    for(int i=1 ; i<=players.size() ; i++)
      System.out.printf("%d) %s %s\n",i,players.get(i-1).getFirstname(),players.get(i-1).getLastname());
    System.out.println();
  } 
    
  private static void listGamerSignedupGames(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();    
    ArrayList<Gamer> players = tournament.getGamers();
    
    if(players.isEmpty() || games.isEmpty())
    {
      System.out.println("\nThere are no games or players!\n");
      return;
    }
        
    int playerindex;
    do
     {
       playerindex = ForceInputInt("Select player: ");
     }while(playerindex <= 0 || playerindex > players.size());      // έλεγχος για index ώστε να είναι εντός ορίων
           
    playerindex--;
    
    int j=0;
    for(int gameindex=0 ; gameindex<games.size() ; gameindex++)    // για κάθε παιχνίδι
    {
      if(games.get(gameindex).containsGamer(players.get(playerindex)))  // αν ο παίκτης έχει δηλώσει συμμετοχύ
        System.out.printf("%d) %s\n",++j,games.get(gameindex).getName());  // εκτύπωση του παιχνιδιού
    }
    System.out.println();
  }
  
  private static void addGamerToGame(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();    
    ArrayList<Gamer> players = tournament.getGamers();    
    int gameindex,playerindex;
    
    if(games.isEmpty() || players.isEmpty())
    {
      System.out.println("\nThere are no games or players!\n");
      return;
    }
    
    do
    {
      gameindex = ForceInputInt("Select game: ");
    }while(gameindex <= 0 || gameindex > games.size());       // έλεγχος για index ώστε να είναι εντός ορίων
    
    gameindex--;
    
    if(games.get(gameindex).getMaxgamers() == games.get(gameindex).numofGamers())
    {
      System.out.println("\nCan not be added any more player for this game!\n");
      return;
    }
    
    do
    {
      playerindex = ForceInputInt("Select player: ");
    }while(playerindex <= 0 || playerindex > players.size());
    
    playerindex--;
    
    if(!games.get(gameindex).containsGamer(players.get(playerindex)))
    {
      games.get(gameindex).addGamer(players.get(playerindex));    // εισαγωγή παίκτη στο παιχνίδι
      System.out.println("\nPlayer signed up!\n");
    }
    else
      System.out.println("\nPlayer is already signed up for this game!\n");
  }
  
  private static void removeGamerFromGame(Tournament tournament)
  {
    ArrayList<VideoGame> games = tournament.getGames();    
    ArrayList<Gamer> players = tournament.getGamers();
    
    int playerindex;
    do
    {
      playerindex = ForceInputInt("Select player: ");
    }while(playerindex <= 0 || playerindex > players.size());      // έλεγχος για index ώστε να είναι εντός ορίων      
    playerindex--;
    
    int gameindex;
    do
    {
      gameindex = ForceInputInt("Select game: ");
    }while(gameindex <= 0 || gameindex > games.size());      // έλεγχος για index ώστε να είναι εντός ορίων       
    gameindex--;
    
    if(!games.get(gameindex).containsGamer(players.get(playerindex)))     // αν ο παίκτης δεν έχει δηλώσει συμμετοχή για αυτό το παιχνίδι, έξοδος
    {
      System.out.println("Player is not signed up for this game!\n");
      return;
    }
    
    if(!isGameStarted(tournament,gameindex))            // αν τα ματς δεν έχουν ξεκινήσει για το παιχνίδι που θέλει να διαγραφεί, τότε διαγράφετε από το παιχνίδι
    {
      games.get(gameindex).removeGamer(playerindex); 
      System.out.println("\nPlayer has been removed successfully!\n");
    }    
    else if(isGameStarted(tournament,gameindex) && !isGameFinished(tournament,gameindex))       // αν τα ματς έχουν ξεκινήσει αλλά δεν έχουν τελειώσει, ο παίκτης πρέπει να παραιτηθεί ώστε να διαγραφεί αργότερα
    {
      if(resignGamer(tournament,gameindex,playerindex))       // παραίτηση παίκτη
      {
        System.out.println("\nPlayer has been resigned successfully!\n");
        
        
        // πρέπει να γίνει έλεγχο για δημιουργία νέου γύρου
        int roundindex = games.get(gameindex).numofRounds()-1;
        int matchindex;
        for(matchindex=0 ; matchindex<games.get(gameindex).getRound(roundindex).numOfMatches() ; matchindex++)
        {  
          if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).isPending(games.get(gameindex).getRound(roundindex)))   // το ματς δεν έχει τελειώσει
            break;
        }
        // αν όλα τα ματς έχουν τελειώσει, δημιουργία νέου γύρου
        if(matchindex == games.get(gameindex).getRound(roundindex).numOfMatches())
        {
          Gamer inactiveplayer = games.get(gameindex).getRound(roundindex).getInactive();
          if(matchindex > 1 || (matchindex == 1 && inactiveplayer != null))                    // αν υπάρχουν περισσότεροι από ένα τελειωμένο ματς σε αυτό το γύρο ή υπάρχει ένα ματς(και δεν έχει τελειώσει) αλλά υπάρχει και παίκτης που δεν έχει παίξει σε αυτό το γύρο
            games.get(gameindex).newRound();          // δημιουργία νέου γύρου
        }  
      }
      else
        System.out.println("\nPlayer could not be removed from game!\n");
    }
    else if(isGameFinished(tournament,gameindex))       // αν τα ματς έχουν τελειώσει, ο παίκτης δεν μπορεί να διαγραφεί από το ματς(για λόγους ιστορικού, θα μπορούσε το ιστορικό να αποθηκεύτε σαν strgin αλλά και πάλι δεν θα υπήργαν αρκετές πληροφορίες για τον παίκτη)
    {                                                  
      System.out.println("\nPlayer could not be removed from game!\n");
    }

  }

  private static boolean resignGamer(Tournament tournament,int gameindex , int playerindex)
  {
    ArrayList<VideoGame> games = tournament.getGames();    
    ArrayList<Gamer> players = tournament.getGamers();
    
    int roundindex = games.get(gameindex).numofRounds()-1;           // index τελευταίου γύρου
    
    Gamer inactiveplayer = games.get(gameindex).getRound(roundindex).getInactive();
    
    if(inactiveplayer != null)      // if there is inactive player
    {
      if(inactiveplayer.equals(players.get(playerindex)))       // αν αυτός που θα παραιτηθεί δεν έχει παίξει σε αυτό το γύρο
      {
        players.get(playerindex).addResignedIn(games.get(gameindex).getRound(roundindex));   // παραίτηση
        return true;
      }
    }
	
    int matchindex; 
    
    // αν αυτός που θα παραιτηθεί είναι στον τελευταίο γύρο(ο παίκτης μπορεί να παραιτηθεί μόνο αν εμφανίζεται στον τελεταίο γύρο)
    for(matchindex=0 ; matchindex<games.get(gameindex).getRound(roundindex).numOfMatches() ; matchindex++)
    {  
      if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerA().equals(players.get(playerindex)))
      {
        players.get(playerindex).addResignedIn(games.get(gameindex).getRound(roundindex));   // παραίτηση παίκτης Α
        return true;
      }
      else if(games.get(gameindex).getRound(roundindex).getMatch(matchindex).getGamerB().equals(players.get(playerindex)))
      {
        players.get(playerindex).addResignedIn(games.get(gameindex).getRound(roundindex));   // παραίτηση παίκτης Β
        return true;
      }
    }
    
    // εδώ ο παίκτης δεν εμφανίζεται στον τελευταίο γύρο οπότε δεν έχει παραιτηθεί
    return false; 
  }
  
  
  
  
 
  
 
  
  private static  int ForceInputInt(String s)
  {
    int input = 0;
    boolean reenter;
    
    // Επιβάλετε στο χρήστη να εισάγει ακέραιο
    do
    {   
      reenter = false;
      System.out.print(s);
      
      try
      {
        input = in.nextInt();
        in.nextLine();             // βγάζει το 'Enter'(από το stream) που πάτησε ο χρήστης μετά την είσοδο
      }
      catch(java.util.InputMismatchException ex)       // ο χρήστης δεν έχει δώσει ακέραιο στην είσοδο, exception 
      {
        reenter = true;
        in.next();         // πρέπει να αγνοηθεί αυτό που έδωσε ο χρήστης(και δεν είναι ακέραιος) γιατί σύμφωνα με το documentation δεν αγνοείται αυτόματα
      }
    }while(reenter);
    
    return input;
  }
  
  private static  boolean isValidName(String name)
  {
    // έλεγχος για το αν το όνομα έχει μόνο αγγλικούς χαρακτήρες(είτε πεζούς είτε κεφαλαίους είτε και τα δύο)
    Pattern p = Pattern.compile("^[a-zA-Z]+$");
    Matcher m = p.matcher(name);
    return m.matches();
  }
  
  private static  boolean isValidPhonenumber(String phonenumber)
  {
    // το τηλέφωνο πρέπει να έχει 10 χαρακτήρες απο 0 μέχρι 9
    Pattern p = Pattern.compile("^[0-9]{10}$");
    Matcher m = p.matcher(phonenumber);
    return m.matches();
  }
  
  private static  boolean isValidEmail(String email)
  {
    // έλεγχος για σωστό email
    Pattern p = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$");
    Matcher m = p.matcher(email);
    return m.matches();
  }
    

}
