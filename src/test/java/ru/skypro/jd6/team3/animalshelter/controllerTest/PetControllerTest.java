package ru.skypro.jd6.team3.animalshelter.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.jd6.team3.animalshelter.controller.PetController;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PetController petController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createPetTest() throws Exception {
        Pet pet = new Pet();
        pet.setAge(6);
        pet.setName("test");
        pet.setWeight(4.6);
        pet.setBreed("test");
        HttpEntity<Pet> entity = new HttpEntity<Pet>(pet);

        ResponseEntity<Pet> response = testRestTemplate.exchange("/pet", HttpMethod.POST, entity, Pet.class,
                pet.getId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/pet", pet, String.class);
        assertThat(response.getBody().getName()).isEqualTo("test");
        assertThat(response.getBody().getBreed()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(pet.getAge());
        assertThat(response.getBody().getWeight()).isEqualTo(pet.getWeight());
    }

    @Test
    public void getPetTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/pet", String.class))
                .isNotNull();
    }

    @Test
    public void updatePetTest() throws Exception {
        Pet pet = new Pet();
        pet.setAge(6);
        pet.setName("test");
        pet.setWeight(4.6);
        pet.setBreed("test");
        HttpEntity<Pet> entity = new HttpEntity<Pet>(pet);

        ResponseEntity<Pet> response = testRestTemplate.exchange("/pet", HttpMethod.POST, entity, Pet.class,
                pet.getId());
        pet.setId(response.getBody().getId());

        response.getBody().setAge(7);

        testRestTemplate.put("/pet", HttpMethod.PUT, entity, Pet.class, pet.getId());
        Pet petResponseEntity = testRestTemplate.getForObject("/pet", Pet.class, pet.getId());
        assertThat(response.getBody().getAge()).isNotEqualTo(6);
        assertThat(response.getBody().getAge()).isEqualTo(7);
        assertThat(response.getBody().getName()).isEqualTo("test");
        assertThat(response.getBody().getBreed()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getWeight()).isEqualTo(pet.getWeight());
    }

    @Test
    public void deletePetTest() throws Exception {
        Pet pet = new Pet();
        pet.setAge(6);
        pet.setName("test");
        pet.setWeight(4.6);
        pet.setBreed("test");
        HttpEntity<Pet> entity = new HttpEntity<Pet>(pet);

        ResponseEntity<Pet> response = testRestTemplate.exchange("/pet", HttpMethod.POST, entity, Pet.class,
                pet.getId());
        pet.setId(response.getBody().getId());

        assertThat(this.testRestTemplate.getForEntity("/pet/{id}", Pet.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/pet/{id}",pet.getId());
        assertThat(this.testRestTemplate.getForEntity("/pet/{id}", Pet.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
