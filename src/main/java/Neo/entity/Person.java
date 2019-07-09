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
      return this.uid;
   }

   public void setUId( long uid )
   {
      this.uid = uid;
   }

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName( String firstName )
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return this.lastName;
   }

   public void setLastName( String lastName )
   {
      this.lastName = lastName;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   @Override
   public String toString()
   {
      return "Person [uid = " + uid + ", firstName = " + firstName + ", lastName = " + lastName + ", email = " + email + "]";
   }
}
