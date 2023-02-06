package com.attornatus.attornatus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.attornatus.repository.AddressRepository;

import java.util.List;
import com.attornatus.attornatus.models.Address;

@RestController
@RequestMapping("/enderecos")
public class AddressController {
    
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> index(){
        return addressRepository.findAll();
    }

    @PostMapping
    public Address create(@RequestBody Address address){
        return addressRepository.save(address);
    }
}
