package service;

import java.util.List;
import java.util.Properties;

import db.*;
import domain.Person;
import domain.Product;

public class ShopService {

	private PersonRepository	personRepository;
	private ProductRepository	productRepository;

	public ShopService(Properties props) {
		switch (props.getProperty("storage", "")) {
			case "database":
				personRepository = new PersonRepositoryInDatabase(props);
				productRepository = new ProductRepositoryInDatabase(props);
				break;
			case "memory":
				personRepository = new PersonRepositoryInMemory(props);
				productRepository = new ProductRepositoryInMemory(props);
				break;
			default:
				throw new RuntimeException("Invalid 'storage' property");
		}
		// personRepository = new PersonRepositoryInDatabase(props);
		// productRepository = new ProductRepositoryInDatabase(props);
	}

	public void close() {
		try {
			personRepository.close();
		} catch (Throwable e) {}
		try {
			productRepository.close();
		} catch (Throwable e) {}
	}

	public Person getPerson(String personId) {
		return getPersonRepository().get(personId);
	}

	public List<Person> getPersons() {
		return getPersonRepository().getAll();
	}

	public void addPerson(Person person) {
		getPersonRepository().add(person);
	}

	public void updatePersons(Person person) {
		getPersonRepository().update(person);
	}

	public void deletePerson(String id) {
		getPersonRepository().delete(id);
	}

	private PersonRepository getPersonRepository() {
		return personRepository;
	}

	public Product getProduct(String productId) {
		return getProductRepository().get(productId);
	}

	public List<Product> getProducts() {
		return getProductRepository().getAll();
	}

	public List<Product> getProductsSorted() {
		return getProductRepository().getAllSorted();
	}

	public void addProduct(Product product) {
		getProductRepository().add(product);
	}

	public void updateProducts(Product product) {
		getProductRepository().update(product);
	}

	public void deleteProduct(String id) {
		getProductRepository().delete(id);
	}

	private ProductRepository getProductRepository() {
		return productRepository;
	}

	public Person getUserIfAuthenticated(String userid, String password) {
		Person person = personRepository.get(userid);
		if (person != null && person.isCorrectPassword(password)) return person;
		return null;
	}

}
