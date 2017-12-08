package db;

import java.util.List;

import domain.Product;

public interface ProductRepository {

	public Product get(String productId);

	public List<Product> getAll();

	public List<Product> getAllSorted();

	public void add(Product product);

	public void update(Product product);

	public void delete(String productId);

	public void close() throws Throwable;

}
