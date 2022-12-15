package ru.skypro.jd6.team3.animalshelter.controllerTest;

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
import ru.skypro.jd6.team3.animalshelter.controller.OwnerController;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    OwnerController ownerController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createOwnerTest() throws Exception {
        Owner owner = new Owner();
        owner.setEmail("test6");
        owner.setName("test76");
        owner.setPhoneNumber("89123456789");
        owner.isPetOwner();
        HttpEntity<Owner> entity = new HttpEntity<Owner>(owner);

        ResponseEntity<Owner> response = testRestTemplate.exchange("/owner", HttpMethod.POST, entity, Owner.class,
                owner.getOwnerId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/owner", owner, String.class);
        assertThat(response.getBody().getName()).isEqualTo("test6");
        assertThat(response.getBody().getEmail()).isEqualTo("test76");
        assertThat(response.getBody().getPhoneNumber()).isEqualTo("89000000000");
        assertThat(response.getBody().getOwnerId()).isNotNull();
    }

    @Test
    public void getOwnerTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/owner", String.class))
                .isNotNull();
    }

    @Test
    public void updateOwnerTest() throws Exception {
        Owner owner = new Owner();
        owner.setEmail("test4");
        owner.setName("test");
        owner.setPhoneNumber("89000000001");
        owner.isPetOwner();
        HttpEntity<Owner> entity = new HttpEntity<Owner>(owner);

        ResponseEntity<Owner> response = testRestTemplate.exchange("/owner", HttpMethod.POST, entity, Owner.class,
                owner.getOwnerId());
        owner.setOwnerId(response.getBody().getOwnerId());

        response.getBody().setPhoneNumber("89000000001");

        testRestTemplate.put("/owner", HttpMethod.PUT, entity, Owner.class, owner.getOwnerId());
        Owner ownerResponseEntity = testRestTemplate.getForObject("/owner", Owner.class, owner.getOwnerId());
        assertThat(response.getBody().getPhoneNumber()).isNotEqualTo("89000000000");
        assertThat(response.getBody().getPhoneNumber()).isEqualTo("89000000001");
        assertThat(response.getBody().getName()).isEqualTo("test");
        assertThat(response.getBody().getEmail()).isEqualTo("test1");
        assertThat(response.getBody().getOwnerId()).isNotNull();
    }

    @Test
    public void deleteOwnerTest() throws Exception {
        Owner owner = new Owner();
        owner.setEmail("test2");
        owner.setName("test");
        owner.setPhoneNumber("89000000003");
        owner.isPetOwner();
        HttpEntity<Owner> entity = new HttpEntity<Owner>(owner);

        ResponseEntity<Owner> response = testRestTemplate.exchange("/owner", HttpMethod.POST, entity, Owner.class,
                owner.getOwnerId());
        owner.setOwnerId(response.getBody().getOwnerId());

        assertThat(this.testRestTemplate.getForEntity("/owner/{id}", Owner.class, response.getBody().getOwnerId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/owner/{id}", owner.getOwnerId());
        assertThat(this.testRestTemplate.getForEntity("/owner/{id}", Owner.class, response.getBody().getOwnerId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
