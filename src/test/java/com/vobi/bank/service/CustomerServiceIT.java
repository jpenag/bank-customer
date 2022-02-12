package com.vobi.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.DocumentType;
import com.vobi.bank.repository.DocumentTypeRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class CustomerServiceIT {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(customerService);
		assertNotNull(documentTypeRepository);
	}
	
	@Order(2)
	@Test
	void debeCrearUnCustomer() throws Exception {
		//Arrange
		Integer idDocumentType = 1;
		Integer idCustomer = 14836554;
		
		Customer customer = null;
		DocumentType documentType=documentTypeRepository.findById(idDocumentType).get();
		
		customer=new Customer();
		customer.setAddress("Avenida siempre viva 123");
		customer.setCustId(idCustomer);
		customer.setDocumentType(documentType);
		customer.setEmail("prueba@prueba.com");
		customer.setEnable("Y");
		customer.setName("Homero J Simpsons");
		customer.setPhone("45647687687");
		customer.setToken("sadsadlkfjaslkdjalsdk");
		
		//Act
		customer = customerService.save(customer);
		
		//Assert
		assertNotNull(customer, "El customer es nulo, no se pudo grabar");
	}
	
	@Order(3)
	@Test
	void debeModifiarUnCustomer() throws Exception {
		//Arrange
		Integer idCustomer = 14836554;
		Customer customer = null;
		
		customer = customerService.findById(idCustomer).get();
		customer.setEnable("N");
		
		//Act
		customer = customerService.update(customer);
		
		//Assert
		assertNotNull(customer, "El customer es nulo, no se pudo modificar");
	}

	@Order(4)
	@Test
	void debeBorrarUnCustomer() throws Exception {
		//Arrange
		Integer idCustomer = 14836554;
		Customer customer = null;
		Optional<Customer> customerOptions=null;
		
		assertTrue(customerService.findById(idCustomer).isPresent(), "No se encontró el customer");
		
		customer = customerService.findById(idCustomer).get();
		
		//Act
		customerService.delete(customer);
		customerOptions = customerService.findById(idCustomer);
		
		//Assert
		assertFalse(customerOptions.isPresent(), "No se pudo borrar el customer");
	}
	
	@Order(5)
	@Test
	void debeConsultarTodosLosCustomer() {
		//Arrange
		List<Customer> customers = null;

		//Act
		customers = customerService.findAll();
		
		customers.forEach(customer->log.info(customer.getName()));
		
		//Assert
		assertFalse(customers.isEmpty(), "No pudo consultar todos los customers");
	}
}
