package tdd.vendingMachine;

public class ProductType {
    private final String productName;
    private final int priceInCents;

    public ProductType(String productName, int productPriceInCents) {
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException("productName cannot be null or empty.");

        if (productPriceInCents <= 0)
            throw new IllegalArgumentException("productPriceInCents must be positive number.");

        this.productName = productName;
        this.priceInCents = productPriceInCents;
    }

    public String productName() {
        return productName;
    }

    public int productPriceInCents() {
        return priceInCents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductType that = (ProductType) o;

        return priceInCents == that.priceInCents && productName.equals(that.productName);

    }

    @Override
    public int hashCode() {
        int result = productName.hashCode();
        result = 31 * result + priceInCents;
        return result;
    }

    @Override
    public String toString() {
        return "ProductType{" +
            "productName='" + productName + '\'' +
            ", priceInCents=" + priceInCents +
            '}';
    }

    public Product createProduct() {
        return new Product(this);
    }
}
