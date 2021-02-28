package com.ceiba.demo.api;

import com.ceiba.demo.RetoLaboratorioCeibaApplication;
import com.ceiba.demo.TestUtil;
import com.ceiba.demo.model.PersonModel;
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
import java.time.LocalDate;
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
    private static final String DEFAULT_NAME = "AAAA";
    private static final String DEFAULT_LASTNAME = "AAAA";
    private static final LocalDate DEFAULT_DATEBIRTH = LocalDate.ofEpochDay(0L);
    private static final String TEST_API_URL = "/api/persons";

    private PersonModel personModel;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    public static PersonModel createEntity() {
        return PersonModel.builder()
                .idCard(DEFAULT_IDCARD)
                .name(DEFAULT_NAME)
                .lastName(DEFAULT_LASTNAME)
                .dateBirth(DEFAULT_DATEBIRTH)
                .build();
    }

    @BeforeEach
    public void init() {
        personModel = createEntity();
    }

    @Test
    void createPerson() throws Exception {

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(this.personModel)))
                .andExpect(status().isOk());

        List<PersonModel> list = personRepository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate + 1);

        PersonModel personModel = list.get(list.size() - 1);

        assertThat(personModel.getIdCard()).isEqualTo(DEFAULT_IDCARD);
        assertThat(personModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(personModel.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(personModel.getDateBirth()).isEqualTo(DEFAULT_DATEBIRTH);
    }

    @Test
    @Transactional
    void createPersonShouldFail() throws Exception {

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        mockMvc.perform(post(TEST_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(null)))
                .andExpect(status().is4xxClientError());

        List<PersonModel> list = personRepository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRecords() throws Exception {
        personRepository.saveAndFlush(personModel);

        mockMvc.perform(get(TEST_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(personModel.getId().intValue())))
                .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_IDCARD)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.[*].dateBirth").value(hasItem(DEFAULT_DATEBIRTH.toString())));
    }
}
