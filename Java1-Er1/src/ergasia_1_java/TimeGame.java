
package ergasia_1_java;

import java.util.Date;

public class TimeGame extends VideoGame implements java.io.Serializable
{
  private int matchtime;
  
  
  
  /*      Constructors      */
  TimeGame()
  {
    super();
    matchtime = 0;
  }
  
  TimeGame(String name , Date startingdate , int maxplayers , int matchtime)
  {
    super(name,startingdate,maxplayers);
    this.matchtime = matchtime;
  }
  
  
  
  /*      Setters-Getters      */
  public int getMatchtime()
  {
    return matchtime;
  }
  
  public void setMatchtime(int newmatchtime)
  {
    matchtime = newmatchtime;
  }
  
}
