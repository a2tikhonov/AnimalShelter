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
import ru.skypro.jd6.team3.animalshelter.controller.NewUserMenuController;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.Report;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewUserMenuControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    NewUserMenuController newUserMenuController;

    @Test
    public void createButtonTest() throws Exception{
        NewUserMenuButton button = new NewUserMenuButton();
        button.setButton("test");
        button.setCallBack("test");

        HttpEntity<NewUserMenuButton> entity = new HttpEntity<NewUserMenuButton>(button);

        ResponseEntity<NewUserMenuButton> response = testRestTemplate.exchange("/newUserMenu", HttpMethod.POST, entity, NewUserMenuButton.class,
                button.getId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/newUserMenu", button, String.class);
        assertThat(response.getBody().getButton()).isEqualTo("test");
        assertThat(response.getBody().getCallBack()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void getMenuButtonTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/newUserMenu", String.class))
                .isNotNull();
    }

    @Test
    public void updateButtonTest() throws Exception {
        NewUserMenuButton button = new NewUserMenuButton();
        button.setButton("test");
        button.setCallBack("test");

        HttpEntity<NewUserMenuButton> entity = new HttpEntity<NewUserMenuButton>(button);

        ResponseEntity<NewUserMenuButton> response = testRestTemplate.exchange("/newUserMenu", HttpMethod.POST, entity, NewUserMenuButton.class,
                button.getId());
        button.setId(response.getBody().getId());

        button.setButton("test1");

        testRestTemplate.put( "/newUserMenu", HttpMethod.PUT, entity, NewUserMenuButton.class, button.getId());
        assertThat(response.getBody().getButton()).isNotEqualTo("test");
        assertThat(response.getBody().getButton()).isEqualTo("test1");
        assertThat(response.getBody().getCallBack()).isEqualTo("test");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void deleteButtonTest() throws Exception {
        NewUserMenuButton button = new NewUserMenuButton();
        button.setButton("test");
        button.setCallBack("test");

        HttpEntity<NewUserMenuButton> entity = new HttpEntity<NewUserMenuButton>(button);

        ResponseEntity<NewUserMenuButton> response = testRestTemplate.exchange("/newUserMenu", HttpMethod.POST, entity, NewUserMenuButton.class,
                button.getId());
        button.setId(response.getBody().getId());

        assertThat(this.testRestTemplate.getForEntity("/newUserMenu/{id}", Report.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/newUserMenu/{id}",button.getId());
        assertThat(this.testRestTemplate.getForEntity("/newUserMenu/{id}",Report.class, response.getBody().getId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

    }
}
