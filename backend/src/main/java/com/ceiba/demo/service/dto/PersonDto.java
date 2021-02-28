package com.ceiba.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * The type Person dto.
 */
@Data
public class PersonDto {

    private Long id;
    @NotNull
    private Integer idCard;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateBirth;
}
