package com.deloitte.ms;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.ms.model.Employee;
import com.deloitte.ms.repository.FileDataIngestionRepository;
import com.deloitte.ms.service.FileDataIngestionServiceImpl;

@RestController
public class EmployeeController {

	Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private FileDataIngestionServiceImpl fileDataIngestionServiceImpl;
	@Autowired
	private FileDataIngestionRepository employeeDTO;
	
	@RequestMapping(path="/setDataInDB")
	public void setDataInDB() {
		log.debug("Data stored in database");
		fileDataIngestionServiceImpl.LoadFileData();
	}
	
	@GetMapping(path="/getAllEmployees")
    public List < Employee > getAllEmployees(){
       List<Employee> result=employeeDTO.findAll();
       return result;
    }
	
	@GetMapping("/getEmployeeById/{id}")
    public ResponseEntity < Employee > getEmployeeById(@PathVariable(value = "id") String employeeId)
    
    {
		log.debug("Request: {}",employeeId);
		Optional<Employee> EmployeeData = employeeDTO.findById((employeeId));

		if (EmployeeData.isPresent()) {
			log.debug("Response: {}", EmployeeData );
			return new ResponseEntity<>(EmployeeData.get(), HttpStatus.OK);
		} else {
			log.debug("Employee not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
	
	@PostMapping("/addEmployee")
	public ResponseEntity < Employee > addEmployee(@RequestBody Employee employee)
	{
		log.debug("New employee data created");
		Employee employeeObj = employeeDTO.save(employee);
		
		return new ResponseEntity<>(employeeObj,HttpStatus.OK);
	}
	
	@PostMapping("/updateEmployeeById/{id}")
	public ResponseEntity < Employee > updateEmployeeById(@PathVariable(value = "id") String employeeId,@RequestBody Employee newEmployeeData)
	{
		log.debug("Request: {}",employeeId);
		Optional<Employee> oldEmployeeData = employeeDTO.findById(employeeId);
		
		if(oldEmployeeData.isPresent()) {
			
			Employee updatedEmployeeData = oldEmployeeData.get();
			updatedEmployeeData.setFirstName(newEmployeeData.getFirstName());
			updatedEmployeeData.setLastName(newEmployeeData.getLastName());
			updatedEmployeeData.setEmail(newEmployeeData.getEmail());
			updatedEmployeeData.setPhoneNo(newEmployeeData.getPhoneNo());
			updatedEmployeeData.setDoj(newEmployeeData.getDoj());
			updatedEmployeeData.setJobId(newEmployeeData.getJobId());
			updatedEmployeeData.setSalary(newEmployeeData.getSalary());
			
			Employee employeeObj = employeeDTO.save(updatedEmployeeData);
			
			log.debug("Updated employee data");
			return new ResponseEntity<>(employeeObj,HttpStatus.OK);
		}
		
		log.debug("Employee not found");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping("/deleteEmployeeById/{id}")
	public ResponseEntity < Employee > deleteEmployeeById(@PathVariable(value = "id") String employeeId)
	{
		log.debug("Request: {}",employeeId);
		employeeDTO.deleteById(employeeId);
		log.debug("Employee data deleted");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
