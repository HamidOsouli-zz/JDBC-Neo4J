package Neo.di;

import Neo.entity.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDI {
    void deleteAll() throws SQLException;

    void delete( int uid ) throws SQLException;

    List<Person> getAll() throws SQLException;

    Person get( int uid ) throws SQLException;

    void update( int uid, Person person ) throws SQLException;

    void create( Person p ) throws SQLException;

    void addAsColleague( Person p1, Person p2 ) throws SQLException;

}
