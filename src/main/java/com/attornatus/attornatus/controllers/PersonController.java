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
import com.attornatus.attornatus.services.PersonService;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PersonController {

    @Autowired
    private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public List<Person> index() {
        return personService.index();
    }

    @GetMapping("/{nome}")
    public Person findByNome(@PathVariable String nome){
        return personService.findByNome(nome);
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping("/{nome}")
    public Person update(@RequestBody Person person, @PathVariable String nome){
        return personService.update(person, nome);
    }
    
    @GetMapping("/{nome}/enderecos")
    public List<Address> indexAddress(@PathVariable String nome){
        return personService.indexAddress(nome);
    }

    @GetMapping("/{nome}/enderecos/principal")
    public Address getMainAddress(@PathVariable String nome){
        return personService.getMainAddress(nome);
    }
}
