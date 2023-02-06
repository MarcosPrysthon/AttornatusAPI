package com.attornatus.attornatus.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import com.attornatus.attornatus.models.Address;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date dataDeNascimento;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_person_id", referencedColumnName = "person_id")
    private List<Address> enderecos;
}
