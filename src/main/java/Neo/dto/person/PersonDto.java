package Neo.dto.person;


public class PersonDto
{
    private int uid;
    private String firstName;
    private String lastName;
    private String email;
    private long created;
    private long edited;

    public PersonDto()
    {

    }

    public PersonDto(int uid, String firstName, String lastName, String email)
    {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getUId()
    {
        return this.uid;
    }

    public void setUId( int uid )
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

    @Override
    public String toString()
    {
        return "PersonDto [uid = " + uid + ", firstName = " + firstName + ", lastName = " + lastName + ", email = " + email + ", created = " + created + ", edited = " + edited + "]";
    }
}
