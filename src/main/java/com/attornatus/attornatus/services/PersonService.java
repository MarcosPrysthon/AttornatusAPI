package com.attornatus.attornatus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.attornatus.attornatus.models.Person;
import com.attornatus.attornatus.models.Address;
import com.attornatus.attornatus.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> index() {
        return personRepository.findAll();
    }

    public Person findByNome(@PathVariable String nome){
        return personRepository.findByNome(nome);
    }

    public Person create(@RequestBody Person person){
        Person existsPerson = personRepository.findByNome(person.getNome());
        if(existsPerson == null) return personRepository.save(person);

        return null;
    }

    public Person update(@RequestBody Person person, @PathVariable String nome){
        Person updatePerson = personRepository.findByNome(nome);

        if(updatePerson != null) {
            return personRepository.save(person);
        }

        return null;
    }

    public List<Address> indexAddress(@PathVariable String nome){
        Person person = personRepository.findByNome(nome);

        return person.getEnderecos();
    }

    public Address getMainAddress(@PathVariable String nome){
        Person person = personRepository.findByNome(nome);
        Address mainAddress = new Address();

        for(Address add : person.getEnderecos()) {
            if(add.getPrincipal()) mainAddress = add;
        }

        return mainAddress;
    }
}
