package tdd.vendingMachine;

public class Product {
    private String name;
    private int price;

    public Product(String name, int price) {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null.");

        if (price <= 0)
            throw new IllegalArgumentException("price must be a positive number.");

        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return (price == product.price) && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price;
        return result;
    }
}
