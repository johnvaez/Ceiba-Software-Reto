package com.ceiba.demo.api;

import com.ceiba.demo.RetoLaboratorioCeibaApplication;
import com.ceiba.demo.TestUtil;
import com.ceiba.demo.model.PersonsModel;
import com.ceiba.demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = RetoLaboratorioCeibaApplication.class)
@AutoConfigureMockMvc
class PersonApiTest {

    private static final Integer DEFAULT_IDCARD = 1;
    private static final String  DEFAULT_NAME = "AAAA";
    private static final String  DEFAULT_LASTNAME = "AAAA";
    private static final LocalDate DEFAULT_DATEBIRTH = LocalDate.ofEpochDay(0L);
    private static final String TEST_API_URL = "/api/persons";

    private PersonsModel personsModel;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    public static PersonsModel createEntity() {
        return PersonsModel.builder()
                .idCard(DEFAULT_IDCARD)
                .name(DEFAULT_NAME)
                .lastName(DEFAULT_LASTNAME)
                .dateBirth(DEFAULT_DATEBIRTH)
                .build();
    }

    @BeforeEach
    public void init() {
        personsModel = createEntity();
    }

    @Test
    void createPerson() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(personsModel)))
                .andExpect(status().isOk());

        List<PersonsModel> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate + 1);

        PersonsModel personsModel = list.get(list.size() - 1);

        assertThat(personsModel.getIdCard()).isEqualTo(DEFAULT_IDCARD);
        assertThat(personsModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(personsModel.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(personsModel.getDateBirth()).isEqualTo(DEFAULT_DATEBIRTH);
    }

    @Test
    @Transactional
    void createPersonShouldFail() throws Exception {

        int databaseSizeBeforeCreate = repository.findAll().size();

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(null)))
                .andExpect(status().is4xxClientError());

        List<PersonsModel> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRecords() throws Exception {
        repository.saveAndFlush(personsModel);

        mockMvc.perform(get(TEST_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(personsModel.getId().intValue())))
                .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_IDCARD)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.[*].dateBirth").value(hasItem(DEFAULT_DATEBIRTH.toString())));
    }
}
