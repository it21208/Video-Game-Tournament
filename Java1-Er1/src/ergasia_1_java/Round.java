
package ergasia_1_java;

import java.util.ArrayList;

public class Round implements java.io.Serializable
{
  private ArrayList<Match> matches;      // 'κρατάει' όλα τα ματς που έχουν δημιουργηθεί για το συγκεκριμένο γύρο
  private Gamer inactive;               // επειδή όλα τα ματς παίζονται ένας-εναντίον-ενός, είναι δυνατόν το πολύ ένας παίκτης
                                         // να μην παίζει στο συγκεκριμένο γύρο, αν υπάρχει τέτοιος παίκτης η μεταβλητή 'inactive'
                                         // 'δείχνει' σε αυτό τον παίκτη αλλιώς έχει την τιμή 'null'
  
  
  
  /*      Constructors      */  
  Round()
  {
    inactive = null;
    matches = new ArrayList<Match>();
  }
  
  
  
  /*      Setters-Getters      */  
  public Gamer getInactive()
  {
    return inactive;
  }
  
  public ArrayList<Match> getMatches()
  {
    return matches;
  }
  
  public void setInactive(Gamer newinactive)
  {
    inactive = newinactive;
  }
  
  
  
  
  /*      Match methods      */
  public void addMatch(Match newmatch)
  {
    matches.add(newmatch);
  }
  
  public Match getMatch(int index)
  {
    if(index < 0 || index > matches.size()-1)   // έλεγχος για το 'index'
      return null;
    
    return matches.get(index);
  }
  
  public int numOfMatches()
  {
    return matches.size();
  }
  
}
