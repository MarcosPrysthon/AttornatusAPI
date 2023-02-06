package com.attornatus.attornatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attornatus.attornatus.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
