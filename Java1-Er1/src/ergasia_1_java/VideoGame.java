
package ergasia_1_java;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class VideoGame implements java.io.Serializable
{
  protected String name;
  protected Date startingdate;
  protected int maxgamers;
  protected Administrator administrator;
  protected ArrayList<Gamer> gamers;          // αποθηκεύονται οι παίκτες που έχουν πάρει μέρος σε αυτό το παιχνίδι
  protected ArrayList<Round> rounds;            // αποθηκεύονται οι γύροι που υπάρχουν στο παιχνίδι
  
  
  
  /*      Constructors      */
  VideoGame()
  {
    name = "";
    startingdate = null;
    maxgamers = 0;
    gamers = new ArrayList<Gamer>();
    rounds = new ArrayList<Round>();
  }
  
  VideoGame(String name , Date startingdate , int maxgamers)
  {
    this.name = name;
    this.startingdate = startingdate;
    this.maxgamers = maxgamers;
    gamers = new ArrayList<Gamer>();
    rounds = new ArrayList<Round>();
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
  
  public int getMaxgamers()
  {
    return maxgamers;
  }
  
  public Administrator getAdministrator()
  {
    return administrator;
  }
  
  public ArrayList<Gamer> getGamers()
  {
    return gamers;
  }
  
  public ArrayList<Round> getRounds()
  {
    return rounds;
  }
  
  public void setName(String newname)
  {
    name = newname;
  }
  
  public void setStartingdate(Date newstartingdate)
  {
    startingdate = newstartingdate;
  }
  
  public void setMaxgamers(int newmaxgamers)
  {
    maxgamers = newmaxgamers;
  }
  
  public void setAdministrator(Administrator newadministrator)
  {
    administrator = newadministrator;
  }
  
  
  
  /*      Gamer methods      */
  public Gamer getGamer(int gamerindex)
  {
    if(gamerindex < 0 || gamerindex > gamers.size()-1)   // έλεγχος για το 'index'
      return null;
    
    return gamers.get(gamerindex);
  }
  
  public void addGamer(Gamer newgamer)
  {
    gamers.add(newgamer);
  }
  
  public void removeGamer(int gamerindex)
  {
    if(gamerindex < 0 || gamerindex > gamers.size()-1)   // έλεγχος για το 'index'
      return; 
    
    gamers.remove(gamerindex);
  }
  
  public boolean containsGamer(Gamer gamer)
  {
    return gamers.contains(gamer);
  }
  
  public int numofGamers()
  {
    return gamers.size();
  }
  
  
  
  /*      Round methods      */
  public Round getRound(int roundindex)
  {
    // έστω ότι το 'index' δεν είναι εκτός ορίων, αυτό προϋποθέτει ότι πρέπει να έχει ήδη ελεχθεί
    return rounds.get(roundindex); 
  }
  
  public void newRound()
  {
    Random randgen = new Random();
    ArrayList<Gamer> nextroundplayers;     // βοηθητικός πίνακας στον οποίο αποθηκεύονται οι παίκτες που θα παίξουν στο νεό γύρο
    Gamer resignlater = null;              // αν και οι δύο παίκτες έχουν παραιτηθεί, αυτή η μεταβλητή αποθηκεύει τον παίκτη
                                            // που θα περάσει στον επόμενο γύρο και θα παραιτηθεί αυτομάτως, πρέπει να υπάρχει πάντα
                                            // ένας νικητής από κάθε ματς
    
    
    // αποθήκευση παικτών που θα παίξουν στο νέο γύρο
    if(numofRounds() == 0)                  // αν δεν υπάρχει κανένας γύρος, όλοι οι παίκτες θα παίξουν στο νέο γύρο(δηλαδή στον 1ο)
      nextroundplayers = (ArrayList<Gamer>)gamers.clone();   // αντιγραφή όλων των παικτών αυτού του παιχνιδιού(gamers) στον πίνακα 'nextroundplayers'
    else
    {      
      nextroundplayers = new ArrayList<Gamer>();
      Round lastround = getRound(numofRounds()-1);      // τελευταίος γύρος 
      
      // για κάθε γύρο του παιχνιδιού πρέπει να γίνει έλεγχος για το ποιοι παίκτες θα παίξουν στον επόμενο γύρο
      for(int matchindex=0 ; matchindex<lastround.numOfMatches() ; matchindex++)
      {
        Match lastroundmatch = lastround.getMatch(matchindex);
        
        if(lastroundmatch.getGamerApoints() > lastroundmatch.getGamerBpoints())     // αν ο παίκτης Α έχει περισσότερους πόντους από τον πάικτη Β
          nextroundplayers.add(lastroundmatch.getGamerA());      // ο παίκτης Α θα παίξει στον επόμενο γύρο
        else if(lastroundmatch.getGamerApoints() < lastroundmatch.getGamerBpoints())     // αν ο παίκτης Β έχει περισσότερους πόντους από τον πάικτη Α
          nextroundplayers.add(lastroundmatch.getGamerB());      // ο παίκτης Β θα παίξει στον επόμενο γύρο
        else if(lastroundmatch.getGamerA().isResignedIn(lastround) && lastroundmatch.getGamerB().isResignedIn(lastround))   // αν και οι δύο παίκτες έχουν παραιτηθεί στον τελευταίο γύρο
        { 
          nextroundplayers.add(lastroundmatch.getGamerA());    // στον επόμενο γύρο θα περάσει ο παίκτης Α(τυχαία)  
          resignlater = lastroundmatch.getGamerA();            // θα παραιτηθεί αυτομάτως στο νέο γύρο
          // θα μπορούσε να περνάει στον επόμενο γύρο ο τελευταίος που παραιτήθηκε, αλλά σε αυτή την υλοποίηση
          // δεν έχει σημασία αφού για κάθε τουρνουά(κάθε παιχνιδιού) ζητείτε μόνο ο νικητής και όχι όλη η κατάταξη
        }
        else if(lastroundmatch.getGamerA().isResignedIn(lastround))    // αν έχει παραιτηθεί ο παίκτης Α
          nextroundplayers.add(lastroundmatch.getGamerB());      // ο παίκτης Β θα παίξει στον επόμενο γύρο
        else if(lastroundmatch.getGamerB().isResignedIn(lastround))    // αν έχει παραιτηθεί ο παίκτης Β
          nextroundplayers.add(lastroundmatch.getGamerA());      // ο παίκτης Α θα παίξει στον επόμενο γύρο      
      }
      
      Gamer inactiveplayer = lastround.getInactive();
      if(inactiveplayer != null && !inactiveplayer.isResignedIn(lastround))      // αν υπάρχει παίκτης που δεν έχει παίξει στον τελευταίο γύρο και δεν έχει παραιτηθεί
        nextroundplayers.add(inactiveplayer);       // θα παίξει στον επόμενο γύρο
    }
     
    // δημιουργία του νέου γύρου
    rounds.add(new Round());
    
    // δημιουργία ν ματς για το γύρο που μόλις δημιουργήθηκε, όπου ν=nextroundplayers.size()/2
    for(int i=0 ; i<nextroundplayers.size()/2 ; i++)
    {
     //                        τυχαία επιλογή μοναδικών παικτών για κάθε ματς
     // αφού παραχθεί ένα τυχαίος αριθμός στο διάστημα [0,nextroundplayers.size())
     // ο οποίος είναι το index ενός παίκτη στον πίνακα 'nextroundplayers', διαγράφεται ο συγκέκριμένος παίκτης από τον 
     // πίνακα 'nextroundplayers', επιστρέφεται αναφορά σε αυτό τον παίκτη και αποθηκεύετε στη μεταβλητή 'playerA'
     // τώρα ο παραπάνω πίνακας "κρατάει" όλους τους παίκτες που θα παίξουν στον επόμενο γύρο χωρίς τον παίκτη Α
     // με αυτό τον τρόπο επιλέγετε και ο παίκτης Β και είναι και αυτός μοναδικός
     Gamer playerA = nextroundplayers.remove(randgen.nextInt(nextroundplayers.size()));
     Gamer playerB = nextroundplayers.remove(randgen.nextInt(nextroundplayers.size()));
     
     // δημιουργία νέου ματς και εισαγωγή στο γύρο που μόλις δημιουργήθηκε
     rounds.get(rounds.size()-1).addMatch(new Match(playerA,playerB));          
    } 
    
    // αν υπάρχει παίκτης που δεν θα παίξει ματς στον γύρο που μόλις δημιουργήθηκε, αποθηκεύετε ως 'inactive' για αυτό το γύρο
    if(nextroundplayers.size() == 1)
      rounds.get(rounds.size()-1).setInactive(nextroundplayers.get(0));    // ο πίνακας 'nextroundplayers' έχει μόνο 1 στοιχείο(παίκτη)
    
  
    // αν υπάρχει παίκτης που πρέπει να παραιτηθεί στο γύρο που μόλις δημιουργήθηκε
    if(resignlater != null)
    {
      resignlater.addResignedIn(getRound(rounds.size()-1));     // αποθηκεύση αναφοράς για το γύρο στον οποίο παραιτείται ο παίκτης
          
      // από τη στιγμή που έχει αλλάξει η πορεία του τουρνούα, πρέπει να γίνει έλεγχος για πιθανή δημιουργία νέου γύρου
      int roundindex = numofRounds()-1;
      int matchindex;
      for(matchindex=0 ; matchindex<getRound(roundindex).numOfMatches() ; matchindex++)
      {  
        if(getRound(roundindex).getMatch(matchindex).isPending(getRound(roundindex)))   // το ματς δεν έχει τελειώσει
          break;
      }
      
      if(matchindex == getRound(roundindex).numOfMatches())   // αν όλα τα ματς έχουν τελειώσει
      {
        Gamer inactiveplayer = getRound(roundindex).getInactive();
        if(matchindex > 1 || (matchindex == 1 && inactiveplayer != null))     // αν υπάρχει 1 ματς ή περισσότερα από 1 αλλά υπάρχει επίσης και παίκτης που δεν έχει παίξει
          newRound();       // δημιουργία νέου γύρου
      } 
    }
  }
  
  public int numofRounds()
  {
    return rounds.size();
  }

   
}
