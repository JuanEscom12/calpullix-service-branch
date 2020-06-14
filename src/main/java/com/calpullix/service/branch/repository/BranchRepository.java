package com.calpullix.service.branch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calpullix.service.branch.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

	@Query("SELECT count(b) FROM Branch b")
	int getBranchCount();
	
	Page<Branch> findAllByOrderByStatevalue(Pageable paginations);
	
}
