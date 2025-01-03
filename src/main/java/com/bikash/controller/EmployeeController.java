package com.bikash.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bikash.entity.Employee;
import com.bikash.service.IEmployeeMgmtService;


@Controller
@RequestMapping("employee")
public class EmployeeController {
	
	@Autowired
	private IEmployeeMgmtService service;
	
	@GetMapping("/home")
	public String homePage()
	{
		return "home";
	}
	
	@GetMapping("/report")
	public String reportPage(Map<String,Object> map) 
	{
		List<Employee> list=service.getAllEmployee();
		map.put("employee",list);
		return "report";
	}
	
	@GetMapping("/register")
	public String registerFormPage(@ModelAttribute Employee employee) //Not providing name in ModelAttribute it will take the parameter name by default (employee) 
	{
		return "register_form";
	}
	
	//Double Posting Problem and no code re-usability
	
	/*@PostMapping("/register") 
	public String resultFormPage(Map<String,Object> map,@ModelAttribute Employee employee) //Not providing name in ModelAttribute it will take the parameter name by default (employee) 
	{
		try {
			String msg=service.registerEmployee(employee);
			map.put("result", msg);
			List<Employee> list=service.getAllEmployee();
			map.put("employee",list);
			return "report";
		} catch (Exception e) {
			e.printStackTrace();
			String msg="Something went wrong!! Please try again..";
			map.put("error",msg);
			return "error";
		}
	}*/
	
	
	/*Double Posting problem solved but we are not getting register confirmation message
	because map shared memory is applicable only for current request (redirect means new req)
	will come and because of that provided msg in shared memory is not available for other req*/

	/*@PostMapping("/register") 
	public String resultFormPage(Map<String,Object> map,@ModelAttribute Employee employee) //Not providing name in ModelAttribute it will take the parameter name by default (employee) 
	{
		try {
			String msg=service.registerEmployee(employee);
			map.put("result", msg);
			return "redirect:report";
		} catch (Exception e) {
			e.printStackTrace();
			String msg="Something went wrong!! Please try again..";
			map.put("error",msg);
			return "error";
		}
	}*/
	

	/*This is the best way but one small problem in this also , if you once click on "refresh"
	button the register  message will not available , but some of the user want it to display
	still browser is not closed for that case we need to use HttpSession*/
	
	@PostMapping("/register") 
	public String resultFormPage(RedirectAttributes redAttr,@ModelAttribute Employee employee) //Not providing name in ModelAttribute it will take the parameter name by default (employee) 
	{
		try {
			String msg=service.registerEmployee(employee);
			System.out.println(msg);
			redAttr.addFlashAttribute("result", msg);  //For redirect scope (for next req only it will be available)
			return "redirect:/employee/report";
		} catch (Exception e) {
			e.printStackTrace();
			redAttr.addFlashAttribute("exception",e.getMessage());
			return "error";
		}
	}
	
	/*@PostMapping("/register") 
	public String resultFormPage(HttpSession session,@ModelAttribute Employee employee) //Not providing name in ModelAttribute it will take the parameter name by default (employee) 
	{
		try {
			String msg=service.registerEmployee(employee);
			session.setAttribute("result", msg);  //For redirect scope
			return "redirect:report";
		} catch (Exception e) {
			e.printStackTrace();
			String msg="Something went wrong!! Please try again..";
			session.setAttribute("error",msg);  //Only for current req scope
			return "error";
		}
	}*/
	
	@GetMapping("/edit")
	public String editPage(@RequestParam() int id,@ModelAttribute Employee employee) 
	//Here RequestParam because we are passing id value with edit path so to get that value here
	//Model attribute to bind model class data to view comp
	{
		Employee emp=service.fetchEmployee(id);
		BeanUtils.copyProperties(emp,employee); //Method to copy detail getting from database to
												//ModelAttribute
		System.out.println(emp);
		return "edit_page";
	}
	
	@PostMapping("/edit")
	public String updatedPage(@ModelAttribute Employee employee,RedirectAttributes reat) 
	{
		String msg=service.editEmployee(employee);
		reat.addFlashAttribute("result", msg);
		return "redirect:/employee/report"; 
	}
	
	@GetMapping("/delete")
	public String deleteOperation(@RequestParam() int id,Map<String,Object> map) 
	{
		String msg=service.deleteEmployee(id);
		map.put("result",msg);
		return "forward:/employee/report"; //Here source and destination both request having save mode GET so
					// we can use forward (Map shared memory work in this case) ,but if its get to post and any n/w trip required 
					// then we use redirect (Map shared memory wont work in this case)
	}
}
