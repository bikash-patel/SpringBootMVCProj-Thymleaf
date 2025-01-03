package com.bikash.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikash.entity.Employee;
import com.bikash.repository.MyEmployeeRepo;

@Service
public class EmployeeMgmtServiceImpl implements IEmployeeMgmtService {

	@Autowired
	private MyEmployeeRepo employeeRepo;
	
	@Override
	public List<Employee> getAllEmployee() {
		//List<Employee> list=employeeRepo.findAll(Sort.by(Direction.ASC,"empname"));
		List<Employee> list=employeeRepo.findAll().stream().sorted((emp1,emp2) -> emp1.getEmpname().compareTo(emp2.getEmpname())).toList();
		//Above both is used for sorting only
		return list;
	}
	
	@Override
	public String registerEmployee(Employee emp) {
		int id=employeeRepo.save(emp).getEmpid();
		return "New Employee registered with Employee ID : "+id;
	}
	
	@Override
	public Employee fetchEmployee(int id) {
		// TODO Auto-generated method stub
		return employeeRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid ID"));
	}

	@Override
	public String editEmployee(Employee emp) {
		Optional<Employee> opt=employeeRepo.findById(emp.getEmpid());
		if(opt.isPresent())
		{
			employeeRepo.save(emp);
			return "Employee having id "+emp.getEmpid()+" updated!!";
		}
		return "Employee having id "+emp.getEmpid()+" not found!!";
	}
	
	@Override
	public String deleteEmployee(int id) {
		Optional<Employee> opt=employeeRepo.findById(id);
		if(opt.isPresent())
		{
			employeeRepo.deleteById(id);
			return "Employee having id "+id+" removed!!";
		}
		return "Employee having id "+id+" not found!!";
	}
}
