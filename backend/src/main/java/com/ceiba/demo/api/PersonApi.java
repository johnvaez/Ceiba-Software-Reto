package com.ceiba.demo.api;

import com.ceiba.demo.model.PersonModel;
import com.ceiba.demo.service.PersonService;
import com.ceiba.demo.service.dto.PersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Person api.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PersonApi {

    private final PersonService personService;

    /**
     * Instantiates a new Person api.
     *
     * @param personService the person service
     */
    public PersonApi(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Post person http status.
     *
     * @param personDto the person dto
     * @return the http status
     */
    @PostMapping("/persons")
    public HttpStatus postPerson(@RequestBody PersonDto personDto) {
        return personService.createPerson(personDto);
    }

    /**
     * Gets persons.
     *
     * @return the persons
     */
    @GetMapping("/persons")
    public List<PersonModel> getPersons() {
        return personService.getPersons();
    }

    /**
     * Validateid card object.
     *
     * @param idCard the id card
     * @return the object
     */
    @GetMapping("/persons/{idCard}")
    public Object validateidCard(@PathVariable("idCard") Integer idCard) {
        return personService.validateidCard(idCard);
    }

}
