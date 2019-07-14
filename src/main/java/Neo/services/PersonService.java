package Neo.services;

import Neo.dao.PersonDao;
import Neo.entity.Person;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PersonService {
    private static PersonDao personDao = new PersonDao();

    public static Person create(int uid, String firstName, String lastName, String email) throws SQLException {
        Person person = new Person(uid, firstName, lastName, email);
        person.setCreated(new Date().getTime());
        personDao.create(person);
        return person;
    }

    public static void addAsColleague(Person first, Person last) throws SQLException {
        personDao.addAsColleague(first, last);
    }

    public static List<Person> getAll() throws SQLException {
        return personDao.getAll();
    }

    public static Person update(int uid, Person person) throws SQLException {
        person.setEdited(new Date().getTime());
        personDao.update(uid, person);
        return person;
    }

    public static Person get(int uid) throws SQLException {
        return personDao.get(uid);
    }

    public static void delete(Person person) throws SQLException {
        personDao.delete(person.getUId());
    }

    public static void deleteAll() throws SQLException {
        personDao.deleteAll();
    }

}
