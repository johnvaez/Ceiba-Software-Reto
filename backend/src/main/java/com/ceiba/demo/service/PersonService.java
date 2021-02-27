package com.ceiba.demo.service;

import com.ceiba.demo.classes.MessageResponse;
import com.ceiba.demo.model.PersonsModel;
import com.ceiba.demo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public HttpStatus createPerson(PersonsModel personsModel) {

        try {
            personRepository.save(personsModel);
            return HttpStatus.OK;

        }catch (HttpClientErrorException e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    public List<PersonsModel> getPersons() {

        List<PersonsModel> listPersons = personRepository.findAll();
        return listPersons;
    }

    public ResponseEntity<MessageResponse> validateidCard(Long idCard) {

        Boolean existsIdCard = personRepository.existsPersonsModelByIdCard(idCard);

        if (existsIdCard) {
            return ResponseEntity.ok(new MessageResponse().status(true).message("No. de cédula ya existe!"));
        }

        return null;
    }
}
