package Neo;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Neo.entity.Person;
import Neo.services.PersonService;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        try
        {
            Person person1 = PersonService.create(65, "hamid", "osouli", "hamid@gmail.com");
            Person person2 = PersonService.create(66, "Arian", "rahmani", "rahmani@gmail.com");
            // get single

            // add person1 and person2 as a Colleague
            PersonService.addAsColleague(person1, person2);
            // getAll
            List<Person> persons = PersonService.getAll();
            for (Person p : persons)
            {
                logger.info(p.toString());
            }
            // update
            Person editedPerson = person1;
            editedPerson.setFirstName("Hamidreza");
            editedPerson = PersonService.update(person1.getUId(), editedPerson);
            logger.info(editedPerson.toString());
            logger.info(PersonService.get(person1.getUId()).toString());
            // single delete
            PersonService.delete(person1);
            // delete all
            PersonService.deleteAll();
        }
        catch ( SQLException e )
        {
            logger.error(e.getMessage());
        }
        catch ( Exception e )
        {
            logger.error(e.getMessage());
        }
    }
}
