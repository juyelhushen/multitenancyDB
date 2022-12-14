package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.City;


public interface CityRepository extends JpaRepository<City,Long> {

   // City findById(Long id);

    City findByName(String name);

    void deleteByName(String name);
}
