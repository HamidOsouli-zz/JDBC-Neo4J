package Neo;

import Neo.dao.PersonDaoImpl;
import Neo.entity.Person;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try
        {
            PersonDaoImpl personDao = new PersonDaoImpl();
            Person person1 = new Person(1, "Hamid", "Osouli", "hamid.com" );
            Person person2 = new Person(54, "Arian", "Rahmani", "arian.com");
            // Create
            personDao.create( person1 );
            personDao.create( person2 );
            // get single
            System.out.println(personDao.get(person1.getUId()));
            System.out.println(personDao.get(person2.getUId()));
            // add person1 and person2 as a Colleague
            personDao.addAsColleague(person1, person2);
            // getAll
            List<Person> persons = personDao.getAll();
            for ( Person p : persons )
            {
                System.out.println( p );
            }
            // update
            Person editedPerson = person1;
            editedPerson.setFirstName("Hamidreza");
            personDao.update(person1.getUId(), editedPerson);
            System.out.println(personDao.get(person1.getUId()));
            // single delete
            personDao.delete(person1.getUId());
            // delete all
            personDao.deleteAll();
        }
        catch ( SQLException e )
        {
            System.out.println( "Exception occured " + e.getMessage() );
        }
        catch ( Exception e )
        {
            System.out.println( "Exception occured " + e.getMessage() );
        }
    }
}
