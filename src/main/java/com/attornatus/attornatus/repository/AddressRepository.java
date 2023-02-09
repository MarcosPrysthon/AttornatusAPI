package com.attornatus.attornatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.attornatus.attornatus.models.Address;

@Component
public interface AddressRepository extends JpaRepository<Address, Long> {
}
