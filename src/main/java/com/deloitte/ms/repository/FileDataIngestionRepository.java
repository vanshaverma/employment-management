package com.deloitte.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.deloitte.ms.model.Employee;

@Repository
public interface FileDataIngestionRepository extends JpaRepository<Employee,java.lang.String>{

}
