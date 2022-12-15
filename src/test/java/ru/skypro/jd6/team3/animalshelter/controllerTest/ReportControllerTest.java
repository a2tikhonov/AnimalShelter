package ru.skypro.jd6.team3.animalshelter.controllerTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.skypro.jd6.team3.animalshelter.controller.ReportController;
import ru.skypro.jd6.team3.animalshelter.entity.Report;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ReportController reportController;

    @Test
    public void createReportTest() throws Exception {
        Report report = new Report();
        report.setDescription(110L);

        HttpEntity<Report> entity = new HttpEntity<Report>(report);

        ResponseEntity<Report> response = testRestTemplate.exchange("/report", HttpMethod.POST, entity, Report.class,
                report.getReportId());

        this.testRestTemplate.postForObject("http://localhost:" + port + "/report", report, String.class);
        assertThat(response.getBody().getDescription()).isEqualTo(110L);
        assertThat(response.getBody().getReportId()).isNotNull();

    }

    @Test
    public void getReportTest() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/report", String.class))
                .isNotNull();
    }

    @Test
    public void updateReportTest() throws Exception {
        Report report = new Report();
        report.setDescription(110L);

        HttpEntity<Report> entity = new HttpEntity<Report>(report);

        ResponseEntity<Report> response = testRestTemplate.exchange("/report", HttpMethod.POST, entity, Report.class,
                report.getReportId());
        report.setReportId(response.getBody().getReportId());

        response.getBody().setDescription(115l);

        testRestTemplate.put("/report", HttpMethod.PUT, entity, Report.class, report.getReportId());
        Report reportResponseEntity = testRestTemplate.getForObject("/report",Report.class,report.getReportId());
        assertThat(response.getBody().getDescription()).isNotEqualTo(110L);
        assertThat(response.getBody().getDescription()).isEqualTo(115l);
        assertThat(response.getBody().getReportId()).isNotNull();

    }

    @Test
    public void deleteReportTest() throws Exception {
        Report report = new Report();
        report.setDescription(110L);

        HttpEntity<Report> entity = new HttpEntity<Report>(report);

        ResponseEntity<Report> response = testRestTemplate.exchange("/report", HttpMethod.POST, entity, Report.class,
                report.getReportId());
        report.setReportId(response.getBody().getReportId());

        assertThat(this.testRestTemplate.getForEntity("/report/{id}",Report.class, response.getBody().getReportId()).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        this.testRestTemplate.delete("/report/{id}",report.getReportId());
        assertThat(this.testRestTemplate.getForEntity("/report/{id}",Report.class, response.getBody().getReportId()).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }


}
