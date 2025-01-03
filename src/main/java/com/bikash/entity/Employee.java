package com.bikash.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Employee")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Employee {
	
	@Id
	@SequenceGenerator(name = "seq1",sequenceName = "emp_seq1",initialValue = 10000,allocationSize = 1)
	@GeneratedValue(generator = "seq1",strategy = GenerationType.SEQUENCE)
	private Integer empid;
	
	@NonNull
	@Column(length = 30)
	private String empname;
	
	@NonNull
	@Column(length = 30)
	private String empdeg;
	
	@NonNull
	private Double empsalary;
	
	@NonNull
	private Integer deptno=10;
}
