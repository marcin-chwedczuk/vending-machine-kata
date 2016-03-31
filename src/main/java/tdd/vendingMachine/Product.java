package tdd.vendingMachine;

public class Product {
    private final ProductType productType;

    public Product(ProductType productType) {
        if (productType == null)
            throw new NullPointerException("productType must be provided.");

        this.productType = productType;
    }

    public String name() {
        return productType.productName();
    }

    public int priceInCents() {
        return productType.productPriceInCents();
    }

    public ProductType productType() {
        return productType;
    }
}
