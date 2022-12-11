package ru.skypro.jd6.team3.animalshelter.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.jd6.team3.animalshelter.controller.CynologistController;
import ru.skypro.jd6.team3.animalshelter.entity.Cynologist;
import ru.skypro.jd6.team3.animalshelter.repository.CynologistRepository;
import ru.skypro.jd6.team3.animalshelter.service.CynologistService;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CynologistController.class)
public class CynologistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CynologistRepository cynologistRepository;

    @SpyBean
    private CynologistService cynologistService;

    @InjectMocks
    private CynologistController cynologistController;

    @Test
    public void createCynologistTest() throws Exception {
        final String name = "test";
        final String specialties = "test";
        final double yearsOfPractice = 2.3;
        final long phoneNumber = 89000000000L;
        final long id = 1L;

        Cynologist cynologist = new Cynologist();
        cynologist.setName(name);
        cynologist.setSpecialties(specialties);
        cynologist.setId(id);
        cynologist.setYearsOfPractice(yearsOfPractice);
        cynologist.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(cynologistRepository.save(ArgumentMatchers.any(Cynologist.class))).thenReturn(cynologist);
        when(cynologistRepository.findById(eq(id))).thenReturn(Optional.of(cynologist));

        mockMvc.perform(post("/cynologist")
                        .content(objectMapper.writeValueAsString(cynologist))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    cynologist.setId(cynologistResult.getId());
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                    assertThat(cynologistResult.getId()).isEqualTo(cynologist.getId());
                });
    }

    @Test
    public void getCynologistTest() throws Exception {
        final String name = "test";
        final String specialties = "test";
        final double yearsOfPractice = 2.3;
        final long phoneNumber = 89000000000L;
        final long id = 1L;

        Cynologist cynologist = new Cynologist();
        cynologist.setName(name);
        cynologist.setSpecialties(specialties);
        cynologist.setId(id);
        cynologist.setYearsOfPractice(yearsOfPractice);
        cynologist.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(cynologistRepository.save(ArgumentMatchers.any(Cynologist.class))).thenReturn(cynologist);
        when(cynologistRepository.findById(eq(id))).thenReturn(Optional.of(cynologist));

        mockMvc.perform(post("/cynologist")
                        .content(objectMapper.writeValueAsString(cynologist))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    cynologist.setId(cynologistResult.getId());
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                    assertThat(cynologistResult.getId()).isEqualTo(cynologist.getId());
                });

        when(cynologistRepository.getById(eq(id))).thenReturn(cynologist);

        mockMvc.perform(MockMvcRequestBuilders.get("/cynologist/" + cynologist.getId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                });
    }

    @Test
    public void updateCynologistTest() throws Exception {
        final String name = "test";
        final String specialties = "test";
        final double yearsOfPractice = 2.3;
        final long phoneNumber = 89000000000L;
        final long id = 1L;

        Cynologist cynologist = new Cynologist();
        cynologist.setName(name);
        cynologist.setSpecialties(specialties);
        cynologist.setId(id);
        cynologist.setYearsOfPractice(yearsOfPractice);
        cynologist.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(cynologistRepository.save(ArgumentMatchers.any(Cynologist.class))).thenReturn(cynologist);
        when(cynologistRepository.findById(eq(id))).thenReturn(Optional.of(cynologist));

        mockMvc.perform(post("/cynologist")
                        .content(objectMapper.writeValueAsString(cynologist))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    cynologist.setId(cynologistResult.getId());
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                    assertThat(cynologistResult.getId()).isEqualTo(cynologist.getId());
                });

        when(cynologistRepository.getById(eq(id))).thenReturn(cynologist);

        mockMvc.perform(MockMvcRequestBuilders.get("/cynologist/" + cynologist.getId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                });

        when(cynologistRepository.getById(eq(id))).thenReturn(cynologist);

        mockMvc.perform(MockMvcRequestBuilders.get("/cynologist/" + cynologist.getId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(cynologistResult.getId()).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                    cynologist.setYearsOfPractice(3.2);
                });

        mockMvc.perform(MockMvcRequestBuilders.put("/cynologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cynologist)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().ignoringFields("id", "name","specialties","phoneNumber").isEqualTo(cynologist);
                    assertThat(cynologistResult.getId()).isEqualTo(cynologist.getId());
                });

        when(cynologistRepository.getById(eq(id))).thenReturn(cynologist);

        mockMvc.perform(MockMvcRequestBuilders.get("/cynologist/" + cynologist.getId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult.getYearsOfPractice()).isEqualTo(3.2);
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                });
    }

    @Test
    public void deleteCynologistTest() throws Exception {
        final String name = "delete";
        final String specialties = "test";
        final double yearsOfPractice = 2.3;
        final long phoneNumber = 89000000000L;
        final long id = 1L;

        Cynologist cynologist = new Cynologist();
        cynologist.setName(name);
        cynologist.setSpecialties(specialties);
        cynologist.setId(id);
        cynologist.setYearsOfPractice(yearsOfPractice);
        cynologist.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(cynologistRepository.save(ArgumentMatchers.any(Cynologist.class))).thenReturn(cynologist);
        when(cynologistRepository.findById(eq(id))).thenReturn(Optional.of(cynologist));

        mockMvc.perform(post("/cynologist")
                        .content(objectMapper.writeValueAsString(cynologist))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Cynologist cynologistResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Cynologist.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    cynologist.setId(cynologistResult.getId());
                    assertThat(cynologistResult).isNotNull();
                    assertThat(cynologistResult).usingRecursiveComparison().isEqualTo(cynologist);
                    assertThat(cynologistResult.getId()).isEqualTo(cynologist.getId());
                });

        mockMvc.perform(MockMvcRequestBuilders.delete("/cynologist/" + cynologist.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cynologist)))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/cynologist/" + cynologist.getId()))
                .andExpect(status().is(404));
    }
}
