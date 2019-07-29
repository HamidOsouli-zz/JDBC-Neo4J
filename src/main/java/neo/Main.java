package neo;

import com.fasterxml.jackson.databind.ObjectMapper;
import neo.dto.person.PersonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import neo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
@Component
public class Main {
    private static PersonService personService;
    static Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    public void setPersonService(PersonService personService) {
        Main.personService = personService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Please Enter how many you want to create: ");
            Scanner scanner = new Scanner(System.in);
            String maxUser = scanner.nextLine();
            if (isParsableToInt(maxUser)) {
                isValid = true;
//                for (int i = 0; i < Integer.parseInt(maxUser); i++) {
//                    PersonDto firstPersonToCreate = new PersonDto("james", "Blunt", "hamid@gmail.com");
//                    PersonDto firstPersonCreated = personService.create(firstPersonToCreate);
//
//                    PersonDto secondPersonToCreate = new PersonDto("Arian", "rahmani", "rahmani@gmail.com");
//                    PersonDto secondPersonCreated = personService.create(secondPersonToCreate);
//                    personService.addAsColleague(firstPersonCreated, secondPersonCreated, 2018);
//                }
                // get all
                List<PersonDto> persons = personService.getAll();
                for(PersonDto person: persons) {
                    logger.info(person.toString());
                }
                // delete all
//                personService.deleteAll();

                // update
                PersonDto editedPerson = persons.get(persons.size()-1);
                editedPerson.setFirstName("Hamidreza");
                editedPerson = personService.update(editedPerson);
                logger.info(personService.get(editedPerson).toString());
                // single delete
//                personService.delete(persons.get(persons.size()-1));
            } else {
                System.out.println("try again");
            }
        }
    }
    public static boolean isParsableToInt(String input) {
        try {
            Integer.parseInt(input);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
