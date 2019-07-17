package Neo.dao.person;

import Neo.entity.Person;
import java.util.List;

public interface PersonDao {
    void deleteAll();

    void delete(Person p);

    List<Person> getAll();

    Person get( Person p );

    Person update(Person person );

    Person create( Person p );

    void addAsColleague( Person p1, Person p2 );

}
