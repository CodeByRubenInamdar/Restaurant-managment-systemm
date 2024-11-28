package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}