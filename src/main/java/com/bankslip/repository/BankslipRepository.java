package com.bankslip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankslip.entity.Bankslip;

@Repository
public interface BankslipRepository extends JpaRepository<Bankslip, String>{

}
