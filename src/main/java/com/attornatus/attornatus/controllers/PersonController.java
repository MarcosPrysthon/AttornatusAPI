package com.attornatus.attornatus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.attornatus.models.Person;
import com.attornatus.attornatus.models.Address;
import com.attornatus.attornatus.repository.PersonRepository;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> index() {
        return personRepository.findAll();
    }

    @GetMapping("/{nome}")
    public Person findByNome(@PathVariable String nome){
        return personRepository.findByNome(nome);
    }

    @GetMapping("id/{id}")
    public Optional<Person> findById(@PathVariable Long id){
        return personRepository.findById(id);
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person){
        return personRepository.save(person);
    }
    
    @GetMapping("/{nome}/enderecos")
    public List<Address> indexAddress(@PathVariable String nome){
        Person person = personRepository.findByNome(nome);

        return person.getEnderecos();
    }

    @GetMapping("/{nome}/enderecos/principal")
    public Address getMainAddress(@PathVariable String nome){
        Person person = personRepository.findByNome(nome);
        Address mainAddress = new Address();

        for(Address add : person.getEnderecos()) {
            if(add.getPrincipal()) mainAddress = add;
        }

        return mainAddress;
    }
}
