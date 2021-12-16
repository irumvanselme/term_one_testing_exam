package rw.ac.rca.termOneExam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

@Service
public class CityService {

    @Autowired
    private ICityRepository cityRepository;

    public Optional<City> getById(long id) {

        Optional<City> city = cityRepository.findById(id);

        // add a line to convert to set the degrees to farneheit
        city.ifPresent(value -> value.setFahrenheit(converter(value.getWeather())));

        return city;
    }

    public List<City> getAll() {

        List<City> cities = cityRepository.findAll();

        // add a for loop to set the degrees to farneheit
        for (City city : cities)
            city.setFahrenheit(converter(city.getWeather()));

        return cities;
    }

    public boolean existsByName(String name) {

        return cityRepository.existsByName(name);
    }

    public City save(CreateCityDTO dto) {
        City city = new City(dto.getName(), dto.getWeather());
        return cityRepository.save(city);
    }


    public double converter(double celius) {
        return (celius * 9 / 5) + 32;
    }
}
