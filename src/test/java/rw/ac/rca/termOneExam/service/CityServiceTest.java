package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private ICityRepository iCityRepository;

    @InjectMocks
    private CityService cityService;


    @Test
    public void getAll_test() {
        when(iCityRepository.findAll()).thenReturn(Arrays.asList(new City("Rio de Janeiro", 32), new City("Tokyo", 54), new City("Oslo", -3)));

        assertEquals(3, cityService.getAll().size());
        assertEquals(32, cityService.getAll().get(0).getWeather());
    }

    @Test
    public void getById_testSuccess() {
        when(iCityRepository.findById(anyLong())).thenReturn(Optional.of(new City("Berlin", 43)));

        assertTrue(cityService.getById(1L).isPresent());
        assertEquals("Berlin", cityService.getById(1L).get().getName());
        assertEquals(43, cityService.getById(1L).get().getWeather());
    }

    @Test
    public void getById_testIdNotFound() {
        when(iCityRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(cityService.getById(1L).isPresent());
    }


    @Test
    public void create_testSuccess() {
        when(iCityRepository.save(any(City.class))).thenReturn(new City("Denver", 21));

        CreateCityDTO dto = new CreateCityDTO();
        dto.setName("Nairobi");
        dto.setWeather(12);

        assertEquals("Denver", cityService.save(dto).getName());
        assertEquals(21, cityService.save(dto).getWeather());
    }

    @Test
    public void existsByName_test() {
        when(iCityRepository.existsByName(anyString())).thenReturn(true);

        assertTrue(cityService.existsByName("Mumbai"));
    }

    @Test
    public void converterTest_Zero(){
        assertEquals(32, cityService.converter(0));
    }

    @Test
    public void converterTest_Any(){
        assertEquals(93.2, cityService.converter(34));
    }

    @Test
    public void converterTest_100(){
        assertEquals(212, cityService.converter(100));
    }

}
