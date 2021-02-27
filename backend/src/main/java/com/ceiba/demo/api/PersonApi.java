package com.ceiba.demo.api;

import com.ceiba.demo.model.PersonsModel;
import com.ceiba.demo.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PersonApi {

    private final PersonService personService;

    public PersonApi(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/persons")
    public HttpStatus postPerson(@RequestBody PersonsModel personsModel) {
        return personService.createPerson(personsModel);
    }

    @GetMapping("/persons")
    public List<PersonsModel> getEpisode() {
       return personService.getPersons();
    }

    @GetMapping("/persons/{idCard}")
    public Object validateidCard(@PathVariable("idCard") Long idCard) {
        return personService.validateidCard(idCard);
    }

}
