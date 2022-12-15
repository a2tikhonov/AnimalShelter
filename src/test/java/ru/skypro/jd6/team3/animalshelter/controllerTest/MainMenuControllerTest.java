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
import ru.skypro.jd6.team3.animalshelter.controller.MainMenuController;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.Report;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainMenuControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MainMenuController mainMenuController;

    @Test
    public void createMenuButtonTest() throws Exception {
        MainMenuButton menuButton = new MainMenuButton();
        menuButton.setButton("test");
        menuButton.setCallBack("test");

        HttpEntity<MainMenuButton> entity = new HttpEntity<MainMenuButton>(menuButton);

        ResponseEntity<MainMenuButton> response = testRestTemplate.exchange("/mainMenu", HttpMethod.POST, entity, MainMenuButton.class,
                menuButton.getId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/mainMenu", menuButton, String.class);
        assertThat(response.getBody().getButton()).isEqualTo("test");
        assertThat(response.getBody().getCallBack()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void getMenuButtonTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/mainMenu", String.class))
                .isNotNull();
    }

    @Test
    public void updateMenuButtonTest() throws Exception {
        MainMenuButton menuButton = new MainMenuButton();
        menuButton.setButton("test");
        menuButton.setCallBack("test");

        HttpEntity<MainMenuButton> entity = new HttpEntity<MainMenuButton>(menuButton);

        ResponseEntity<MainMenuButton> response = testRestTemplate.exchange("/mainMenu", HttpMethod.POST, entity, MainMenuButton.class,
                menuButton.getId());
        menuButton.setId(response.getBody().getId());

        menuButton.setCallBack("test1");

      testRestTemplate.put( "/mainMenu", HttpMethod.PUT, entity, MainMenuButton.class, menuButton.getId());
        assertThat(response.getBody().getButton()).isEqualTo("test");
        assertThat(response.getBody().getCallBack()).isNotEqualTo("test");
        assertThat(response.getBody().getCallBack()).isEqualTo("test1");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void deleteMenuButtonTest() throws Exception {
        MainMenuButton menuButton = new MainMenuButton();
        menuButton.setButton("test");
        menuButton.setCallBack("test");

        HttpEntity<MainMenuButton> entity = new HttpEntity<MainMenuButton>(menuButton);

        ResponseEntity<MainMenuButton> response = testRestTemplate.exchange("/mainMenu", HttpMethod.POST, entity, MainMenuButton.class,
                menuButton.getId());
        menuButton.setId(response.getBody().getId());

        assertThat(this.testRestTemplate.getForEntity("/mainMenu/{id}", Report.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/mainMenu/{id}",menuButton.getId());
        assertThat(this.testRestTemplate.getForEntity("/mainMenu/{id}",Report.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
