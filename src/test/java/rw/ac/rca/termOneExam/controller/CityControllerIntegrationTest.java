package rw.ac.rca.termOneExam.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_test() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/all", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getById_testSuccess() {
        ResponseEntity<City> response = restTemplate.getForEntity("/api/cities//id/101", City.class);

        assertEquals("Kigali", response.getBody().getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getById_testIdNotFound() {
        ResponseEntity<APICustomResponse> response = restTemplate.getForEntity("/api/cities//id/200", APICustomResponse.class);

        assertFalse(response.getBody().isStatus());
        assertEquals("City not found with id 200", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void create_testSuccess() {
        CreateCityDTO dto = new CreateCityDTO();
        dto.setName("Bogota");
        dto.setWeather(43);

        ResponseEntity<City> response = restTemplate.postForEntity("/api/cities/add", dto, City.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Bogota", response.getBody().getName());
    }

    @Test
    public void create_testNameAlreadyRegistered() {
        CreateCityDTO dto = new CreateCityDTO();
        dto.setName("Musanze");
        dto.setWeather(43);

        ResponseEntity<APICustomResponse> response = restTemplate.postForEntity("/api/cities/add", dto, APICustomResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("City name " + dto.getName() + " is registered already", response.getBody().getMessage());
    }
}
