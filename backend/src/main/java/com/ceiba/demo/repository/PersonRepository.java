package com.ceiba.demo.repository;

import com.ceiba.demo.model.PersonsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonsModel, Long> {

    Boolean existsPersonsModelByIdCard(Long idCard);
}
