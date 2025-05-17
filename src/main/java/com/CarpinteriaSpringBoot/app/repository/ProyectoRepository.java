package com.CarpinteriaSpringBoot.app.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.CarpinteriaSpringBoot.app.model.Proyecto;

public interface ProyectoRepository extends MongoRepository<Proyecto, String> {
    List<Proyecto> findAll();
    
    Proyecto findByEstado(String placa);

}
