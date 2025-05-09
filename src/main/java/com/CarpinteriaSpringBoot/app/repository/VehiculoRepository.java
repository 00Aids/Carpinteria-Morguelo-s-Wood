package com.CarpinteriaSpringBoot.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.CarpinteriaSpringBoot.app.model.Vehiculo;

public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
    List<Vehiculo> findAll();
    
    Vehiculo findByPlaca(String placa);
}
