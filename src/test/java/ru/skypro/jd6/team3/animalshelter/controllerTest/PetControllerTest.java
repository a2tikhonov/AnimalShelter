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
        pet.setAge(6.3);
        pet.setSpecies("test");
        pet.setName("test");
        pet.setWeight(4.6);
        pet.setBreed("test");
        pet.isDisabled();
        pet.isAdopted();
        HttpEntity<Pet> entity = new HttpEntity<Pet>(pet);

        ResponseEntity<Pet> response = testRestTemplate.exchange("/pet", HttpMethod.POST, entity, Pet.class,
                pet.getPetId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/pet", pet, String.class);
        assertThat(response.getBody().getName()).isEqualTo("test");
        assertThat(response.getBody().getSpecies()).isEqualTo("test");
        assertThat(response.getBody().getBreed()).isEqualTo("test");
        assertThat(response.getBody().getPetId()).isNotNull();
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
        pet.setAge(6.3);
        pet.setSpecies("test");
        pet.setName("test");
        pet.setWeight(4.6);
        pet.setBreed("test");
        pet.isDisabled();
        pet.isAdopted();
        HttpEntity<Pet> entity = new HttpEntity<Pet>(pet);

        ResponseEntity<Pet> response = testRestTemplate.exchange("/pet", HttpMethod.POST, entity, Pet.class,
                pet.getPetId());
        pet.setPetId(response.getBody().getPetId());

        response.getBody().setAge(6.4);

        testRestTemplate.put("/pet", HttpMethod.PUT, entity, Pet.class, pet.getPetId());
        Pet petResponseEntity = testRestTemplate.getForObject("/pet", Pet.class, pet.getPetId());
        assertThat(response.getBody().getAge()).isNotEqualTo(6.3);
        assertThat(response.getBody().getAge()).isEqualTo(6.4);
        assertThat(response.getBody().getName()).isEqualTo("test");
        assertThat(response.getBody().getSpecies()).isEqualTo("test");
        assertThat(response.getBody().getBreed()).isEqualTo("test");
        assertThat(response.getBody().getPetId()).isNotNull();
        assertThat(response.getBody().getWeight()).isEqualTo(pet.getWeight());
    }

    @Test
    public void deletePetTest() throws Exception {
        Pet pet = new Pet();
        pet.setAge(6.3);
        pet.setSpecies("test");
        pet.setName("test");
        pet.setWeight(4.6);
        pet.setBreed("test");
        pet.isDisabled();
        pet.isAdopted();
        HttpEntity<Pet> entity = new HttpEntity<Pet>(pet);

        ResponseEntity<Pet> response = testRestTemplate.exchange("/pet", HttpMethod.POST, entity, Pet.class,
                pet.getPetId());
        pet.setPetId(response.getBody().getPetId());

        assertThat(this.testRestTemplate.getForEntity("/pet/{id}", Pet.class, response.getBody().getPetId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/pet/{id}",pet.getPetId());
        assertThat(this.testRestTemplate.getForEntity("/pet/{id}", Pet.class, response.getBody().getPetId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
