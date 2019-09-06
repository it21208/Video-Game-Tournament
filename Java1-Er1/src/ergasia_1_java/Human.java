
package ergasia_1_java;

public class Human implements java.io.Serializable
{
  protected String firstname;
  protected String lastname;
  protected String nickname;
 
 
  
  /*      Constructors      */
  Human()
  {
    firstname = "";
    lastname = "";
    nickname = "";        
  }
  
  Human(String firstname , String lastname , String nickname)
  {
    this.firstname = firstname;
    this.lastname = lastname;
    this.nickname = nickname;        
  }
  
  
  
  /*      Setters-Getters      */
  public String getFirstname()
  {
    return firstname;
  }

  public String getLastname()
  {
    return lastname;
  }
  
  public String getNickname()
  {
    return nickname;
  }
  
  public void setFirstname(String newfirstname)
  {
    firstname = newfirstname;  
  }
  
  public void setLastname(String newlastname)
  { 
     lastname = newlastname;
  }
  
  public void setNickname(String newnickname)
  {
    nickname = newnickname; 
  }
  
  
}
