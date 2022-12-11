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
import ru.skypro.jd6.team3.animalshelter.controller.VolunteerController;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.repository.VolunteerRepository;
import ru.skypro.jd6.team3.animalshelter.service.VolunteerService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = VolunteerController.class)
public class VolunteerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    public void createVolunteerTest() throws Exception {
        final String name = "test";
        final String email = "test";
        final long phoneNumber = 89000000000L;
        final long volunteerId = 1L;

        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setVolunteerId(volunteerId);
        volunteer.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(volunteerRepository.save(ArgumentMatchers.any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findById(eq(volunteerId))).thenReturn(Optional.of(volunteer));

        mockMvc.perform(post("/volunteer")
                        .content(objectMapper.writeValueAsString(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    volunteer.setVolunteerId(volunteerResult.getVolunteerId());
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                    assertThat(volunteerResult.getVolunteerId()).isEqualTo(volunteer.getVolunteerId());
                });
    }

    @Test
    public void getVolunteerTest() throws Exception {
        final String name = "test";
        final String email = "test";
        final long phoneNumber = 89000000000L;
        final long volunteerId = 1L;

        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setVolunteerId(volunteerId);
        volunteer.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(volunteerRepository.save(ArgumentMatchers.any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findById(eq(volunteerId))).thenReturn(Optional.of(volunteer));

        mockMvc.perform(post("/volunteer")
                        .content(objectMapper.writeValueAsString(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    volunteer.setVolunteerId(volunteerResult.getVolunteerId());
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                    assertThat(volunteerResult.getVolunteerId()).isEqualTo(volunteer.getVolunteerId());
                });

        when(volunteerRepository.getById(eq(volunteerId))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders.get("/volunteer/" + volunteer.getVolunteerId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                });
    }

    @Test
    public void updateVolunteerTest() throws Exception {
        final String name = "test";
        final String email = "test";
        final long phoneNumber = 89000000000L;
        final long volunteerId = 1L;

        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setVolunteerId(volunteerId);
        volunteer.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(volunteerRepository.save(ArgumentMatchers.any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findById(eq(volunteerId))).thenReturn(Optional.of(volunteer));

        mockMvc.perform(post("/volunteer")
                        .content(objectMapper.writeValueAsString(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    volunteer.setVolunteerId(volunteerResult.getVolunteerId());
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                    assertThat(volunteerResult.getVolunteerId()).isEqualTo(volunteer.getVolunteerId());
                });

        when(volunteerRepository.getById(eq(volunteerId))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders.get("/volunteer/" + volunteer.getVolunteerId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                });

        when(volunteerRepository.getById(eq(volunteerId))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders.get("/volunteer/" + volunteer.getVolunteerId()))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                    volunteer.setName("New name");
                });

        mockMvc.perform(put("/volunteer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(volunteer)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult.getName()).isEqualTo("New name");
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                });
    }

    @Test
    public void deleteVolunteerTest() throws Exception {
        final String name = "test";
        final String email = "test";
        final long phoneNumber = 89000000000L;
        final long volunteerId = 1L;

        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setVolunteerId(volunteerId);
        volunteer.setPhoneNumber(phoneNumber);


        ObjectMapper objectMapper = new ObjectMapper();

        when(volunteerRepository.save(ArgumentMatchers.any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findById(eq(volunteerId))).thenReturn(Optional.of(volunteer));

        mockMvc.perform(post("/volunteer")
                        .content(objectMapper.writeValueAsString(volunteer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    Volunteer volunteerResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), Volunteer.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    volunteer.setVolunteerId(volunteerResult.getVolunteerId());
                    assertThat(volunteerResult).isNotNull();
                    assertThat(volunteerResult).usingRecursiveComparison().isEqualTo(volunteer);
                    assertThat(volunteerResult.getVolunteerId()).isEqualTo(volunteer.getVolunteerId());
                });

        mockMvc.perform(MockMvcRequestBuilders.delete("/volunteer/" + volunteer.getVolunteerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(volunteer)))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/volunteer/" + volunteer.getVolunteerId()))
                .andExpect(status().is(404));
    }
}
