package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private CoinsCollector coinsCollector;
    private Dispenser<Product> productDispenser;
    private Dispenser<Coin> coinDispenser;

    private Display display;

    private List<Shelf> shelves;

    private Shelf selectedShelf;

    public VendingMachine(int shelvesCount) {
        coinsCollector = new CoinsCollector();
        productDispenser = new Dispenser<>();
        coinDispenser = new Dispenser<>();
        display = new Display();

        shelves = new ArrayList<>();

        for (int i = 0; i < shelvesCount; i++)
            shelves.add(new Shelf());
    }

    public void supplyProductToShelf(int shelfNumber, ProductType productType, int howMany) {
        Shelf shelf = getShelf(shelfNumber);
        shelf.supplyProduct(productType, howMany);
    }

    public int shelvesCount() {
        return shelves.size();
    }

    public int numberOfProductInstancesOnShelf(int shelfNumber) {
        Shelf shelf = getShelf(shelfNumber);
        return shelf.numberOfProductInstances();
    }

    private Shelf getShelf(int shelfNumber) {
        if (shelfNumber < 1 || shelfNumber > shelves.size())
            throw new IllegalArgumentException(
                "invalid shelf number: " + shelfNumber);

        return shelves.get(shelfNumber-1);
    }

    public String display() {
        if (selectedShelf != null) {
            int missingMoneyInCents =
                selectedShelf.productPriceInCents() - coinsCollector.totalMoneyInCents();

            // user paid too much
            if (missingMoneyInCents < 0)
                missingMoneyInCents = 0;

            return display.displayMissingMoney(missingMoneyInCents);
        }

        return display.displayProductNotSelectedMessage();
    }

    public void selectShelf(int shelfNumber) {
        selectedShelf = getShelf(shelfNumber);
    }

    public void putCoin(Coin coin) {
        coinsCollector.addCoin(coin);

        if (coinsCollector.totalMoneyInCents() >= selectedShelf.productPriceInCents()) {
            productDispenser.put(selectedShelf.removeProduct());
        }
    }

    public Product getProduct() {
        return productDispenser.get();
    }

    public void supplyCoins(Coin coin, int count) {

    }
}
