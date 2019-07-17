package Neo.services;

import Neo.dao.person.PersonDaoImpl;
import Neo.dto.person.PersonDto;
import Neo.entity.Person;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonService {
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    public PersonDto create(PersonDto personDto) {
        // convert dto to entity object
        Person person = mapper.convertValue(personDto, Person.class);
        person.setCreated(new Date().getTime());
        person = personDaoImpl.create(person);
        return mapper.convertValue(person, PersonDto.class);
    }

    public void addAsColleague(PersonDto first, PersonDto second) {
        // convert dto to entity object
        Person person = mapper.convertValue(first, Person.class);
        Person person2 = mapper.convertValue(second, Person.class);
        personDaoImpl.addAsColleague(person, person2);
    }

    public List<PersonDto> getAll() {
        List<Person> persons = personDaoImpl.getAll();
        try {
            String personJsonArray = mapper.writeValueAsString(persons);
            return mapper.readValue(personJsonArray, mapper.getTypeFactory().constructCollectionType(List.class, PersonDto.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public PersonDto update(PersonDto personDto) {
        Person person = mapper.convertValue(personDto, Person.class);
        person.setEdited(new Date().getTime());
        person = personDaoImpl.update(person);
        return mapper.convertValue(person, PersonDto.class);
    }

    public PersonDto get(PersonDto personDto) {
        Person person = mapper.convertValue(personDto, Person.class);
        person = personDaoImpl.get(person);
        return mapper.convertValue(person, PersonDto.class);
    }

    public void delete(PersonDto personDto) {
        Person person = mapper.convertValue(personDto, Person.class);
        personDaoImpl.delete(person);
    }

    public void deleteAll() {
        personDaoImpl.deleteAll();
    }
}
