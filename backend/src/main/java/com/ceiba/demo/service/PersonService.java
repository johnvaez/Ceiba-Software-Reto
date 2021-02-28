package com.ceiba.demo.service;

import com.ceiba.demo.classes.MessageResponse;
import com.ceiba.demo.model.PersonModel;
import com.ceiba.demo.repository.PersonRepository;
import com.ceiba.demo.service.dto.PersonDto;
import com.ceiba.demo.service.mapper.PersonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * The type Person service.
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    /**
     * Instantiates a new Person service.
     *
     * @param personRepository the person repository
     * @param personMapper     the person mapper
     */
    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    /**
     * Create person http status.
     *
     * @param personDto the person dto
     * @return the http status
     */
    public HttpStatus createPerson(PersonDto personDto) {
        try {
            personRepository.save(personMapper.toEntity(personDto));
            return HttpStatus.OK;

        } catch (HttpClientErrorException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Gets persons.
     *
     * @return the persons
     */
    public List<PersonModel> getPersons() {

        List<PersonModel> listPersons = personRepository.findAll();
        return listPersons;
    }

    /**
     * Validateid card response entity.
     *
     * @param idCard the id card
     * @return the response entity
     */
    public ResponseEntity<MessageResponse> validateidCard(Integer idCard) {

        Boolean existsIdCard = personRepository.existsPersonsModelByIdCard(idCard);

        if (existsIdCard) {
            return ResponseEntity.ok(new MessageResponse().status(true).message("No. de cédula "+ idCard +" ya existe!"));
        }

        return null;
    }
}
