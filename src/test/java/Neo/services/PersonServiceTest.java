package Neo.services;

import Neo.dao.person.PersonDaoImpl;
import Neo.dto.person.PersonDto;
import Neo.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


public class PersonServiceTest {

    @Mock
    private PersonDaoImpl personDaoImpl = mock(PersonDaoImpl.class);
    @Mock
    private ObjectMapper mapper = mock(ObjectMapper.class);
    private PersonService personService = new PersonService();
    private Person firstPerson;
    private Person secondPerson;
    private PersonDto firstDto;
    private PersonDto secondDto;
    private ArrayList<Person> persons = new ArrayList<>();
    @Before
    public void setup() {
        persons.clear();
        personService.setPersonDaoImpl(personDaoImpl);
        personService.setObjectMapper(mapper);
        firstDto = new PersonDto(45, "Hamid", "Osouli", "hamid@gmail.com");
        secondDto = new PersonDto(54, "Arian", "Rahmani", "arian@gmail.com");
        firstPerson = new Person(45, "Hamid", "Osouli", "hamid@gmail.com");
        secondPerson = new Person(54, "Arian", "Rahmani", "arian@gmail.com");
        when(personService.getObjectMapper().convertValue(firstDto, Person.class)).thenReturn(firstPerson);
        when(personService.getObjectMapper().convertValue(secondDto, Person.class)).thenReturn(secondPerson);
        when(personService.getObjectMapper().convertValue(firstPerson, PersonDto.class)).thenReturn(firstDto);
        when(personService.getObjectMapper().convertValue(secondPerson, PersonDto.class)).thenReturn(secondDto);
        persons.add(firstPerson);
        persons.add(secondPerson);
    }


    @Test
    public void deleteAllTest() {
        doNothing().when(personService.getPersonDaoImpl()).deleteAll();
        personService.deleteAll();
    }

    @Test
    public void deleteTest() {
        doNothing().when(personService.getPersonDaoImpl()).delete(firstPerson);
        personService.delete(firstDto);
    }

    @Test
    public void getTest() {
        when(personService.getPersonDaoImpl().get(firstPerson)).thenReturn(firstPerson);
        personService.get(firstDto);
        assertEquals(firstPerson.getUId(), firstDto.getUId());
    }
    @Test
    public void updateTest() {
        // clone object
        Person shouldReturnPerson = new Person(firstPerson.getUId(), firstPerson.getFirstName(), firstPerson.getLastName(), firstPerson.getEmail());
        shouldReturnPerson.setEdited(new Date().getTime());
        when(personService.getPersonDaoImpl().update(firstPerson)).thenReturn(shouldReturnPerson);
        personService.update(firstDto);
        assertNotEquals(shouldReturnPerson.getEdited(), 0);
        assertEquals(shouldReturnPerson.getUId(), firstDto.getUId());
    }
    @Test
    public void createTest() {
        Person shouldReturnPerson = firstPerson;
        shouldReturnPerson.setCreated(new Date().getTime());
        when(personService.getPersonDaoImpl().create(firstPerson)).thenReturn(shouldReturnPerson);
        personService.create(firstDto);
        assertNotEquals(shouldReturnPerson.getCreated(), 0);
    }

    @Test
    public void testAddAsColleague() {
        doNothing().when(personService.getPersonDaoImpl()).addAsColleague(firstPerson, secondPerson);
        personService.addAsColleague(firstDto, secondDto);
    }

    @Test
    public void getAllTest() {
        when(personService.getPersonDaoImpl().getAll()).thenReturn(persons);
        personService.getAll();
        assertEquals(persons.size(), 2);
    }
}
