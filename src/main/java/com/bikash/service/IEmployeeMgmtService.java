package com.bikash.service;

import java.util.List;

import com.bikash.entity.Employee;

public interface IEmployeeMgmtService {
	public List<Employee> getAllEmployee();
	public String registerEmployee(Employee emp);
	public Employee fetchEmployee(int id);
	public String editEmployee(Employee emp);
	public String deleteEmployee(int id);
}
