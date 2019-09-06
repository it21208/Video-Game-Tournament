
package ergasia_1_java;

import java.util.Date;

public class RoundGame extends VideoGame implements java.io.Serializable
{
  private int maxrounds;
  
  
  
  /*      Constructors      */
  RoundGame()
  {
    super();
    maxrounds = 0;
  }
  
  RoundGame(String name , Date startingdate , int maxplayers , int maxrounds)
  {
    super(name,startingdate,maxplayers);
    this.maxrounds = maxrounds;
  }
  
  
  
  /*      Setters-Getters      */
  public int getMaxrounds()
  {
    return maxrounds;
  }
  
  public void setMaxrounds(int newmaxrounds)
  {
    maxrounds = newmaxrounds;
  }
  
}
