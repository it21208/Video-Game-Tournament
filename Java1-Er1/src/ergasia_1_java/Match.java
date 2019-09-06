
package ergasia_1_java;

public class Match implements java.io.Serializable
{
  private Gamer gamerA;
  private Gamer gamerB;
  private int gamerApoints;     // οι μετάβλητες 'gamerApoints' και 'gamerBpoints'   'κρατάνε' είτε τους πόντους
  private int gamerBpoints;     // που έχει κάθε παίκτης σε κάθε ματς(TimeGame) ή πόσους γύρους κέρδισε σε αυτό το ματς(RoundGame)
                                 // ανάλογα με τον είδος του παιχνιδιού
 
  
  
  /*      Constructors      */
  Match()
  {
    gamerA = null;
    gamerB = null;
    gamerApoints = -1;
    gamerBpoints = -1;
  }
  
  Match(Gamer playerA , Gamer playerB)
  {
    this.gamerA = playerA;
    this.gamerB = playerB;
    gamerApoints = -1;
    gamerBpoints = -1;
  }

  
  
  /*      Setters-Getters      */
  public Gamer getGamerA()
  {
    return gamerA;
  }

  public Gamer getGamerB()
  {
    return gamerB;
  }

  public int getGamerApoints()
  {
    return gamerApoints;
  }
    
  public int getGamerBpoints()
  {
    return gamerBpoints;
  }
  
  public void setGamerA(Gamer newgamerA)
  {
    gamerA = newgamerA;
  }
  
  public void setGamerB(Gamer newgamerB)
  {
    gamerB = newgamerB;
  }
  
  public void setplayerApoints(int newgamerApoints)
  {
    gamerApoints = newgamerApoints;
  }
  
  public void setplayerBpoints(int newgamerBpoints)
  {
    gamerBpoints = newgamerBpoints;
  }
  
  public void setPlayerPoints(int newgamerApoints , int newgamerBpoints)
  {
    gamerApoints = newgamerApoints;
    gamerBpoints = newgamerBpoints;
    
  }

  
  
  
  /*      Match methods      */
  public boolean isPending(Round currentround)      // επιστρέφει 'true' αν το ματς δεν έχει τελειώσει αλλιώς ειστρέφει 'false'
  {
    // για να θεωρηθεί ότι το ματς εκκρεμεί, πρέπει να μην έχει δωθεί βαθμολογία στους παίκτες και οι παίκτες να μην έχουν παραιτηθεί
return gamerApoints < 0 && (!gamerA.isResignedIn(currentround) && !gamerB.isResignedIn(currentround));   
  }

  
  
}
