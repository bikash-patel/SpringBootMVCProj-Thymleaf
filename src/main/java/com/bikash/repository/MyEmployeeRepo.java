package com.bikash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikash.entity.Employee;

public interface MyEmployeeRepo extends JpaRepository<Employee,Integer> {

}
