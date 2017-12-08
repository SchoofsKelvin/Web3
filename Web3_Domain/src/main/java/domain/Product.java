package domain;

public class Product {

	private String	id;
	private String	description;
	private double	price;

	public Product(String id, String description, double price) {
		setProductid(id);
		setDescription(description);
		setPrice(price);
	}

	public Product() {}

	public String getProductid() {
		return id;
	}

	public void setProductid(String id) {
		if (id.isEmpty()) throw new IllegalArgumentException("No id given");
		this.id = id;
	}

	public void setDescription(String description) {
		if (description.isEmpty()) throw new IllegalArgumentException("No description given");
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public boolean isCorrectPrice(double price) {
		if (price < 0) throw new IllegalArgumentException("Price can't be negative");
		return getPrice() == price;
	}

	public void setPrice(double price) {
		if (price < 0) throw new IllegalArgumentException("Price can't be negative");
		this.price = price;
	}
}
