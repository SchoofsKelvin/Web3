package db;

import java.util.*;

import domain.Person;
import domain.Role;

public class PersonRepositoryInMemory implements PersonRepository {

	private Map<String, Person> persons = new HashMap<String, Person>();

	public PersonRepositoryInMemory(Properties props) {
		add(new Person("admin", "admin@ucll.be", "t", "Ad", "Ministrator", 123,
			Role.ADMINISTRATOR));
		add(new Person("customer", "customer@ucll.be", "t", "Cus", "Tomer", 456,
			Role.CUSTOMER));
	}

	@Override
	public Person get(String personId) {
		if (personId == null) throw new IllegalArgumentException("No id given");
		return persons.get(personId);
	}

	@Override
	public List<Person> getAll() {
		return new ArrayList<Person>(persons.values());
	}

	@Override
	public void add(Person person) {
		if (person == null) throw new IllegalArgumentException("No person given");
		if (persons.containsKey(person.getUserid()))
			throw new IllegalArgumentException("User already exists");
		persons.put(person.getUserid(), person);
	}

	@Override
	public void update(Person person) {
		if (person == null) throw new IllegalArgumentException("No person given");
		if ( !persons.containsKey(person.getUserid()))
			throw new IllegalArgumentException("No person found");
		persons.put(person.getUserid(), person);
	}

	@Override
	public void delete(String personId) {
		if (personId == null) throw new IllegalArgumentException("No id given");
		persons.remove(personId);
	}

	@Override
	public void close() throws Throwable {}
}
