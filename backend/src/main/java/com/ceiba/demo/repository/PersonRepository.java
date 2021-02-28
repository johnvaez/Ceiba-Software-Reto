package com.ceiba.demo.repository;

import com.ceiba.demo.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Person repository.
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {

    /**
     * Exists persons model by id card boolean.
     *
     * @param idCard the id card
     * @return the boolean
     */
    Boolean existsPersonsModelByIdCard(Integer idCard);
}
