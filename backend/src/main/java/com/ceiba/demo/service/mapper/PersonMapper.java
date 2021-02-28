package com.ceiba.demo.service.mapper;

import com.ceiba.demo.model.PersonModel;
import com.ceiba.demo.service.dto.PersonDto;
import org.mapstruct.Mapper;

/**
 * The interface Person mapper.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDto, PersonModel> {
}
