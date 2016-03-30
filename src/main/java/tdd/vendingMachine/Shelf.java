package tdd.vendingMachine;

public class Shelf {
    private Product product;
    private int numberOfProductInstances;

    public void supplyProduct(Product product, int howMany) {
        if (isEmpty()) {
            this.product = product;
            this.numberOfProductInstances = howMany;
        }
        else {
            if (!this.product.equals(product))
                throw new VendingMachineException(
                    "shelf can contain only single type of products, " +
                    "product on shelf: " + this.product + ", " +
                    "attempt to add product: " + product);

            this.numberOfProductInstances += howMany;
        }
    }

    public boolean isEmpty() {
        return (numberOfProductInstances == 0);
    }

    public int numberOfProductInstances() {
        return this.numberOfProductInstances;
    }
}
