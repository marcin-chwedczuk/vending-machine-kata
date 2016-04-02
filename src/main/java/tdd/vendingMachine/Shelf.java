package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private final List<Product> products = new ArrayList<>();

    public void supplyProduct(ProductType productType, int howMany) {
        if (productType == null)
            throw new NullPointerException("productType must be provided.");

        if (howMany < 0)
            throw new IllegalArgumentException("howMany cannot be negative.");

        if (!isEmpty() && !productType.equals(getProductType())) {
            throw new VendingMachineException(
                "shelf can contain only single type of products, " +
                "product on shelf: " + getProductType() + ", " +
                "attempt to add product: " + productType);
        }

        for (int i = 0; i < howMany; i++) {
            products.add(productType.createProduct());
        }
    }

    private ProductType getProductType() {
        if (isEmpty())
            return null;

        return products.get(0).productType();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public int numberOfProductInstances() {
        return products.size();
    }

    public int productPriceInCents() {
        if (this.isEmpty())
            throw new IllegalStateException("shelf has no product on it");

        return products.get(0).priceInCents();
    }

    public Product removeProduct() {
        Product product = products.get(0);
        products.remove(0);
        return product;
    }
}
