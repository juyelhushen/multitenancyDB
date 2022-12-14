package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.City;
import com.example.demo.repository.CityRepository;
import com.example.demo.service.CityService;

@RestController
public class CityController {
	
	@Autowired
	CityRepository repo;

    @Autowired
    private CityService cityService;
    
    @RequestMapping(value = "/city", method = RequestMethod.GET)
    @Cacheable(value = "CityCache")
    public List<City> fetchAll()
    {
    	return repo.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody City city) {
        cityService.save(city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getAll() throws SQLException {
        List<City> cities = cityService.getAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<City> get(@PathVariable(value = "id") Long id) {
//        City city = cityService.get(id);
//        return new ResponseEntity<>(city, HttpStatus.OK);
//    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<City> get(@PathVariable(value = "name") String name) {
        City city = cityService.getByName(name);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<City> delete(@PathVariable(value = "name") String name) {
        cityService.delete(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
