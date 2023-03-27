package com.deloitte.ms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.deloitte.ms.model.Employee;
import com.deloitte.ms.repository.FileDataIngestionRepository;

@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
class MyProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	FileDataIngestionRepository pRepo;
	
	@Test
	@Order(1)
	public void testSetDataInDB () {
		Employee employee = new Employee();
		employee.setId("1");
		employee.setFirstName("Martin");
		employee.setLastName(" Bingel");
		employee.setEmail("martin.s2017@gmail.com");
		employee.setPhoneNo("942506011");
		employee.setDoj("7-Sept-26");
		employee.setJobId("test_job");
		employee.setSalary("9020");
		pRepo.save(employee);
		assertNotNull(pRepo.findById("1").get());
	}
	
	@Test
	@Order(2)
	public void testGetEmployeeById () {
		Employee employee = pRepo.findById("1").get();
		assertEquals("Martin", employee.getFirstName());
	}
	
	@Test
	@Order(3)
	public void testAddEmployee () {
		Employee employee = new Employee();
		employee.setId("2");
		employee.setFirstName("Martin");
		employee.setLastName("Thomson");
		employee.setEmail("mart2017@gmail.com");
		employee.setPhoneNo("942506011");
		employee.setDoj("7-Sept-26");
		employee.setJobId("test_job");
		employee.setSalary("9020");
		pRepo.save(employee);
		assertNotNull(pRepo.findById("2").get());
	}
	
	@Order(4)
	public void testUpdateEmployeeById () {
		Employee employee = pRepo.findById("1").get();
		employee.setSalary("8000");
		pRepo.save(employee);
		assertNotEquals("7000", pRepo.findById("1").get().getSalary());
	}
		
	@Test
	@Order(5)
	public void testDeleteEmployeeById () {
		pRepo.deleteById("2");
		assertThat(pRepo.existsById("2")).isFalse();
	}
}
