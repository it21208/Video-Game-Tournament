
package ergasia_1_java;

public class Administrator extends Human implements java.io.Serializable
{
  private String phonenumber;
  
  
  
  /*      Constructors      */
  Administrator()
  {
    phonenumber = "";
  }
  
  Administrator(String firstname , String lastname , String nickname , String phonenumber)
  {
    super(firstname,lastname,nickname);
    this.phonenumber = phonenumber;
  }
  
  
  
  /*      Setters-Getters      */
  public String getPhonenumber()
  {
    return phonenumber;
  }
  
  public void setPhonenumber(String newphonenumber)
  {
     phonenumber = newphonenumber;
  }
  
  
}
