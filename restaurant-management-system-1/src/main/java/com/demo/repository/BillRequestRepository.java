package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.BillRequest;

public interface BillRequestRepository extends JpaRepository<BillRequest, Long>{
	List<BillRequest> findByApprovedFalse();
}
