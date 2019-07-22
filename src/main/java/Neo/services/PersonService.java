package Neo.services;

import Neo.dao.person.PersonDao;
import Neo.dao.person.PersonDaoImpl;
import Neo.dto.person.PersonDto;
import Neo.entity.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonService {
    private PersonDao personDaoImpl = new PersonDaoImpl();
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
        List<PersonDto> personDtos = new ArrayList<>();
        for (Person person : persons) {
            PersonDto personDto = mapper.convertValue(person, PersonDto.class);
            personDtos.add(personDto);
        }
        return personDtos;
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

    public void setPersonDaoImpl(PersonDao personDaoImpl) {
        this.personDaoImpl = personDaoImpl;
    }
    public PersonDao getPersonDaoImpl() {
        return this.personDaoImpl;
    }
    public void setObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }
}
