package com.deloitte.ms.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.ListUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.ms.model.Employee;
import com.deloitte.ms.repository.FileDataIngestionRepository;

@Service
public class FileDataIngestionServiceImpl implements FileDataIngestionService {

	@Autowired
	private FileDataIngestionRepository employeeDTO;
	
	String line="";
	List<List<Employee>> ll=new ArrayList<>();
	List<Employee> l=new ArrayList<>();
	
	
	@Override
	public void LoadFileData() {
		ParseFileData();	
	}
	
	public void ParseFileData() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/SampleData.csv"));
			while((line=br.readLine())!=null) {
				String [] data = line.split(",");
				Employee employee = new Employee();
				employee.setId(data[0]);
				employee.setFirstName(data[1]);
				employee.setLastName(data[2]);
				employee.setEmail(data[3]);
				employee.setPhoneNo(data[4]);
				employee.setDoj(data[5]);
				employee.setJobId(data[6]);
				employee.setSalary(data[7]);
				
				//employeeDTO.save(employee);
				l.add(employee);
				
				
			}
			
			ll = ListUtils.partition(l, 100);
			
			for (List<Employee> sublist: ll)
				employeeDTO.saveAll(sublist);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
