package com.assecor.assessment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AssessmentApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllPersons_ShouldReturnPersonsFromCsv() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/persons", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Hans"));
        assertTrue(response.getBody().contains("Müller"));
        assertTrue(response.getBody().contains("blau"));
    }

    @Test
    void getPersonById_ShouldReturnSpecificPerson() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/persons/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"id\" : 1"));
        assertTrue(response.getBody().contains("Hans"));
        assertTrue(response.getBody().contains("Müller"));
        assertTrue(response.getBody().contains("blau"));
    }

    @Test
    void getPersonsByColor_ShouldReturnPersonsWithSpecificColor() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/persons/color/grün", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("grün"));
    }

    @Test
    void getPersonById_WhenPersonDoesNotExist_ShouldReturn404() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/persons/999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}