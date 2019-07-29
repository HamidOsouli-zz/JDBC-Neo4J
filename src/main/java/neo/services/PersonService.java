package neo.services;

import neo.dto.person.PersonDto;
import neo.entity.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import neo.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public PersonDto create(PersonDto personDto) {
        // convert dto to entity object
        Person person = mapper.convertValue(personDto, Person.class);
        person.setCreated(new Date().getTime());
        person = personRepository.save(person);
        return mapper.convertValue(person, PersonDto.class);
    }

    @Transactional
    public void addAsColleague(PersonDto first, PersonDto second, long since) {
        // convert dto to entity object
        Person person = mapper.convertValue(first, Person.class);
        Person person2 = mapper.convertValue(second, Person.class);
        personRepository.addAsColleague(person.getId(), person2.getId(), since);
    }

    @Transactional
    public List<PersonDto> getAll() {
        List<Person> persons = (ArrayList<Person>) personRepository.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        for (Person person : persons) {
            PersonDto personDto = mapper.convertValue(person, PersonDto.class);
            personDtos.add(personDto);
        }
        return personDtos;
    }

    @Transactional
    public PersonDto update(PersonDto personDto) {
        Person person = mapper.convertValue(personDto, Person.class);
        person.setEdited(new Date().getTime());
        person = personRepository.save(person);
        return mapper.convertValue(person, PersonDto.class);
    }

    @Transactional
    public PersonDto get(PersonDto personDto) {
        Person person = mapper.convertValue(personDto, Person.class);
        Optional<Person> found = personRepository.findById(person.getId());
        return mapper.convertValue(found.get(), PersonDto.class);
    }

    @Transactional
    public void delete(PersonDto personDto) {
        Person person = mapper.convertValue(personDto, Person.class);
        personRepository.delete(person);
    }

    @Transactional
    public void deleteAll() {
        personRepository.deleteAll();
    }

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonRepository getPersonRepository() {
        return this.personRepository;
    }

    public void setObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }
}
