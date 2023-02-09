package com.attornatus.attornatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.attornatus.attornatus.models.Person;

@Component
public interface PersonRepository extends JpaRepository<Person, Long>{
    Person findByNome(String nome);
}
