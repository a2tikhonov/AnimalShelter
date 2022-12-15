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
import ru.skypro.jd6.team3.animalshelter.controller.PotentialOwnerMenuController;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwnerMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.Report;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PotentialOwnerMenuControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PotentialOwnerMenuController potentialOwnerMenuController;

    @Test
    public void createTest() throws Exception {
        PotentialOwnerMenuButton potentialOwnerMenuButton = new PotentialOwnerMenuButton();
        potentialOwnerMenuButton.setButton("test");
        potentialOwnerMenuButton.setCallBack("test");

        HttpEntity<PotentialOwnerMenuButton> entity = new HttpEntity<PotentialOwnerMenuButton>(potentialOwnerMenuButton);

        ResponseEntity<PotentialOwnerMenuButton> response = testRestTemplate.exchange("/consultMenu", HttpMethod.POST, entity, PotentialOwnerMenuButton.class,
                potentialOwnerMenuButton.getId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/consultMenu", potentialOwnerMenuButton, String.class);
        assertThat(response.getBody().getButton()).isEqualTo("test");
        assertThat(response.getBody().getCallBack()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void getTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/consultMenu", String.class))
                .isNotNull();
    }

    @Test
    public void updateTest() throws Exception {
        PotentialOwnerMenuButton potentialOwnerMenuButton = new PotentialOwnerMenuButton();
        potentialOwnerMenuButton.setButton("test");
        potentialOwnerMenuButton.setCallBack("test");

        HttpEntity<PotentialOwnerMenuButton> entity = new HttpEntity<PotentialOwnerMenuButton>(potentialOwnerMenuButton);

        ResponseEntity<PotentialOwnerMenuButton> response = testRestTemplate.exchange("/consultMenu", HttpMethod.POST, entity, PotentialOwnerMenuButton.class,
                potentialOwnerMenuButton.getId());
        potentialOwnerMenuButton.setId(response.getBody().getId());

        potentialOwnerMenuButton.setCallBack("test1");

      testRestTemplate.put("/consultMenu", HttpMethod.PUT, entity, PotentialOwnerMenuButton.class, potentialOwnerMenuButton.getId());
        assertThat(response.getBody().getCallBack()).isNotEqualTo("test");
        assertThat(response.getBody().getCallBack()).isEqualTo("test1");
        assertThat(response.getBody().getButton()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void deleteTest() throws Exception {
        PotentialOwnerMenuButton potentialOwnerMenuButton = new PotentialOwnerMenuButton();
        potentialOwnerMenuButton.setButton("test");
        potentialOwnerMenuButton.setCallBack("test");

        HttpEntity<PotentialOwnerMenuButton> entity = new HttpEntity<PotentialOwnerMenuButton>(potentialOwnerMenuButton);

        ResponseEntity<PotentialOwnerMenuButton> response = testRestTemplate.exchange("/consultMenu", HttpMethod.POST, entity, PotentialOwnerMenuButton.class,
                potentialOwnerMenuButton.getId());
        potentialOwnerMenuButton.setId(response.getBody().getId());

        assertThat(this.testRestTemplate.getForEntity("/consultMenu/{id}", Report.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/consultMenu/{id}", potentialOwnerMenuButton.getId());
        assertThat(this.testRestTemplate.getForEntity("/consultMenu/{id}",Report.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

    }
}
