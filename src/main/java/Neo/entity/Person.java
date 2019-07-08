package Neo.entity;

public class Person
{
   private long uid;
   private String firstName;
   private String lastName;
   private String email;

   public Person()
   {

   }

   public Person( long uid, String firstName, String lastName, String email )
   {
      this.uid = uid;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;

   }

   public long getUId()
   {
      return uid;
   }

   public void setId( long uid )
   {
      this.uid = uid;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName( String firstName )
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName( String lastName )
   {
      this.lastName = lastName;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   @Override
   public String toString()
   {
      return "Person [id=" + uid + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
   }

}
