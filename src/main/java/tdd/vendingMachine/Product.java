package tdd.vendingMachine;

public class Product {
    private String name;
    private int priceInCents;

    public Product(String name, int priceInCents) {
        if (name == null)
            throw new IllegalArgumentException("name cannot be null.");

        if (priceInCents <= 0)
            throw new IllegalArgumentException("priceInCents must be a positive number.");

        this.priceInCents = priceInCents;
        this.name = name;
    }

    public String name() {
        return name;
    }

    public int priceInCents() {
        return priceInCents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return (priceInCents == product.priceInCents) && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + priceInCents;
        return result;
    }
}
