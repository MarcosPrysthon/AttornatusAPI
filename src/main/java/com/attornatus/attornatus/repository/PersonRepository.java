package com.attornatus.attornatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attornatus.attornatus.models.Person;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    @Override
    Optional<Person> findById(Long id);

    Person findByNome(String nome);
}
