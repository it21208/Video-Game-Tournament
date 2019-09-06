
package ergasia_1_java;

import java.util.ArrayList;

public class Gamer extends Human implements java.io.Serializable
{ 
  private String email;
  private ArrayList<Round> resignedin;
  
  
  /*      Constructors      */
  Gamer()
  {
    super();
    email = "";
    resignedin = new ArrayList<Round>();
  }
  
  Gamer(String firstname , String lastname , String nickname , String email)
  {
    super(firstname,lastname,nickname);
    this.email = email;
    resignedin = new ArrayList<Round>();
  }
  
  
  
  /*      Setters-Getters      */
  public String getEmail()
  {
    return email;
  }
  
  public ArrayList<Round> getResignedin()
  {
    return resignedin;
  }
  
  public void setEmail(String newemail)
  {
    email = newemail;
  }
  
  
  /*      player resignation methods      */
  public void addResignedIn(Round resignedinround)
  {
    // εισάγετε ο γύρος στον οποίο ο παίκτης έχει παραιτηθεί
    resignedin.add(resignedinround);
  }
  
  public boolean isResignedIn(Round resignedinround)
  {
    // γίνετε έλεγχος για το αν ο παίκτης έχει παραιτηθεί στο γύρο 'resignedinround'
    // επειδή ο γύρος είναι αντικείμενο και όχι απλά το index του, δεν χρειάζετε να ελεχθεί και το παιχνίδι 
    return resignedin.contains(resignedinround);
  }
  
    
}
