package db;

import java.util.*;

import domain.Product;

public class ProductRepositoryInMemory implements ProductRepository {

	private Map<String, Product> products = new HashMap<String, Product>();

	public ProductRepositoryInMemory(Properties props) {
		Product testA = new Product("TestA", "Test product 1", 0);
		Product testB = new Product("TestB", "Test product 2", 5.0);
		Product testC = new Product("TestC", "Test product 3", 12.34);
		add(testA);
		add(testB);
		add(testC);
	}

	public Product get(String productId) {
		if (productId == null) throw new IllegalArgumentException("No id given");
		return products.get(productId);
	}

	public List<Product> getAll() {
		return new ArrayList<Product>(products.values());
	}

	public List<Product> getAllSorted() {
		ArrayList<Product> res = new ArrayList<>();
		res.sort(new Comparator<Product>() {

			public int compare(Product o1, Product o2) {
				return o1.getProductid().compareTo(o2.getProductid());
			}
		});
		return res;
	}

	public void add(Product product) {
		if (product == null) throw new IllegalArgumentException("No product given");
		if (products.containsKey(product.getProductid()))
			throw new IllegalArgumentException("User already exists");
		products.put(product.getProductid(), product);
	}

	public void update(Product product) {
		if (product == null) throw new IllegalArgumentException("No product given");
		if ( !products.containsKey(product.getProductid()))
			throw new IllegalArgumentException("No product found");
		products.put(product.getProductid(), product);
	}

	public void delete(String productId) {
		if (productId == null) throw new IllegalArgumentException("No id given");
		products.remove(productId);
	}

	public void close() throws Throwable {}
}
