package com.vobi.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.DocumentType;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerRepositoryIT {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(customerRepository);
		assertNotNull(documentTypeRepository);
	}
	
	@Order(2)
	@Test
	void debeCrearUnCustomer() {
		//Arrange
		Integer idDocumentType=1;
		Integer idCustomer=14836554;
		
		Customer customer=null;
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
		customer=customerRepository.save(customer);
		
		//Assert
		assertNotNull(customer, "El customer es nulo, no se pudo grabar");
	}
	
	@Order(3)
	@Test
	void debeModifiarUnCustomer() {
		//Arrange
		Integer idCustomer=14836554;
		Customer customer=null;
		
		customer=customerRepository.findById(idCustomer).get();
		customer.setEnable("N");
		
		//Act
		customer=customerRepository.save(customer);
		
		//Assert
		assertNotNull(customer, "El customer es nulo, no se pudo modificar");
	}

	@Order(4)
	@Test
	void debeBorrarUnCustomer() {
		//Arrange
		Integer idCustomer=14836554;
		Customer customer=null;
		Optional<Customer> customerOptions=null;
		
		assertTrue(customerRepository.findById(idCustomer).isPresent(), "No se encontró el customer");
		
		customer=customerRepository.findById(idCustomer).get();
		//Act
		customerRepository.delete(customer);
		customerOptions=customerRepository.findById(idCustomer);
		
		//Assert
		assertFalse(customerOptions.isPresent(), "No se pudo borrar el customer");
	}
}
