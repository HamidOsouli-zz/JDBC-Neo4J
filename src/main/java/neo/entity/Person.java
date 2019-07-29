package neo.entity;

import org.neo4j.ogm.annotation.*;


@NodeEntity
public class Person
{
   @Id
   @GeneratedValue
   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private long created;
   private long edited;

//   @Relationship(type = "HAS_COLLEAGUES")
//   private List<Person> colleagues = new ArrayList<>();

   public Person()
   {

   }

   public Person(String firstName, String lastName, String email)
   {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

   public Long getId()
   {
      return this.id;
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

   public long getCreated()
   {
      return this.created;
   }

   public void setCreated( long created )
   {
      this.created = created;
   }

   public long getEdited()
   {
      return this.edited;
   }

   public void setEdited( long edited )
   {
      this.edited = edited;
   }

//   public List<Person> getColleague() {
//      return this.colleagues;
//   }

   @Override
   public String toString()
   {
      return "Person [id = " + id + ", firstName = " + firstName + ", lastName = " + lastName + ", email = " + email + ", created = " + created + ", edited = " + edited + "]";
   }
}
