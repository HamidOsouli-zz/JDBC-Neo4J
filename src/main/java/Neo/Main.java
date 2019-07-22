package Neo;
import java.util.*;

import Neo.dto.person.PersonDto;
import Neo.helpers.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Neo.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        PropertiesHelper propertiesHelper = PropertiesHelper.getInstance();
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Please Enter how many you want to create: ");
            Scanner scanner = new Scanner(System.in);
            String maxUser = scanner.nextLine();
            if (isParsableToInt(maxUser)) {
                isValid = true;
                PersonService personService = new PersonService();
                for (int i = 0; i < Integer.parseInt(maxUser); i++) {
                    ObjectMapper mapper = new ObjectMapper();
                    int uid = new Random().nextInt(100) + 1;
                    PersonDto firstPersonToCreate = new PersonDto(uid, "hamid", "osouli", "hamid@gmail.com");
                    PersonDto firstPersonCreated = personService.create(firstPersonToCreate);
                    PersonDto secondPersonToCreate = new PersonDto(66, "Arian", "rahmani", "rahmani@gmail.com");
                    PersonDto secondPersonCreated = personService.create(secondPersonToCreate);
                    personService.addAsColleague(firstPersonCreated, secondPersonCreated);
                }
                // get all
                List<PersonDto> persons = personService.getAll();
                for(PersonDto person: persons) {
                    logger.info(person.toString());
                }
                // delete all
                //personService.deleteAll();

                // update
                /*PersonDto editedPerson = firstPersonCreated;
                editedPerson.setFirstName("Hamidreza");
                editedPerson = personService.update(firstPersonCreated.getUId(), editedPerson);
                logger.info(editedPerson.toString());
                logger.info(personService.get(firstPersonCreated.getUId()).toString());*/
                // single delete
//                personService.delete(firstPersonCreated);
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
