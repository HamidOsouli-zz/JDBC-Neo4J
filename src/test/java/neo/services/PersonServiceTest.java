package neo.services;

import neo.dto.person.PersonDto;
import neo.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;

import neo.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;


public class PersonServiceTest {

    @Mock
    @Autowired
    private PersonRepository personRepository = mock(PersonRepository.class);
    @Mock
    private ObjectMapper mapper = mock(ObjectMapper.class);
    @Autowired
    private PersonService personService;
    private Person firstPerson;
    private Person secondPerson;
    private PersonDto firstDto;
    private PersonDto secondDto;
    private Long since = Long.valueOf(2018);
    private Optional<Person> found;
    private ArrayList<Person> persons = new ArrayList<>();
    @Before
    public void setup() {
        persons.clear();
        personService.setPersonRepository(personRepository);
        personService.setObjectMapper(mapper);
        firstDto = new PersonDto("Hamid", "Osouli", "hamid@gmail.com");
        secondDto = new PersonDto("Arian", "Rahmani", "arian@gmail.com");
        firstPerson = new Person("Hamid", "Osouli", "hamid@gmail.com");
        secondPerson = new Person("Arian", "Rahmani", "arian@gmail.com");
        found = Optional.of(new Person("Hamid", "Osouli", "hamid@gmail.com"));
        when(personService.getObjectMapper().convertValue(firstDto, Person.class)).thenReturn(firstPerson);
        when(personService.getObjectMapper().convertValue(secondDto, Person.class)).thenReturn(secondPerson);
        when(personService.getObjectMapper().convertValue(firstPerson, PersonDto.class)).thenReturn(firstDto);
        when(personService.getObjectMapper().convertValue(secondPerson, PersonDto.class)).thenReturn(secondDto);
        persons.add(firstPerson);
        persons.add(secondPerson);
    }


    @Test
    public void deleteAllTest() {
        doNothing().when(personService.getPersonRepository()).deleteAll();
        personService.deleteAll();
    }

    @Test
    public void deleteTest() {
        doNothing().when(personService.getPersonRepository()).delete(firstPerson);
        personService.delete(firstDto);
    }

    @Test
    public void getTest() {
        when(personService.getPersonRepository().findById(firstPerson.getId())).thenReturn(found);
        personService.get(firstDto);
        assertEquals(found.get().getId(), firstDto.getId());
    }
    @Test
    public void updateTest() {
        // clone object
        Person shouldReturnPerson = new Person(firstPerson.getFirstName(), firstPerson.getLastName(), firstPerson.getEmail());
        shouldReturnPerson.setEdited(new Date().getTime());
        when(personService.getPersonRepository().save(firstPerson)).thenReturn(shouldReturnPerson);
        personService.update(firstDto);
        assertNotEquals(shouldReturnPerson.getEdited(), 0);
        assertEquals(shouldReturnPerson.getId(), firstDto.getId());
    }
    @Test
    public void createTest() {
        Person shouldReturnPerson = firstPerson;
        shouldReturnPerson.setCreated(new Date().getTime());
        when(personService.getPersonRepository().save(firstPerson)).thenReturn(shouldReturnPerson);
        personService.create(firstDto);
        assertNotEquals(shouldReturnPerson.getCreated(), 0);
    }

    @Test
    public void testAddAsColleague() {
        doNothing().when(personService.getPersonRepository()).addAsColleague(firstPerson.getId(), secondPerson.getId(),since);
        personService.addAsColleague(firstDto, secondDto, since);
    }

    @Test
    public void getAllTest() {
        when(personService.getPersonRepository().findAll()).thenReturn(persons);
        personService.getAll();
        assertEquals(persons.size(), 2);
    }
}
