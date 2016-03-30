package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private CoinsCollector coinsCollector;
    private List<Shelf> shelves;

    private Shelf selectedShelf;

    public VendingMachine(int shelvesCount) {
        coinsCollector = new CoinsCollector();

        shelves = new ArrayList<>();

        for (int i = 0; i < shelvesCount; i++)
            shelves.add(new Shelf());
    }

    public void supplyProductToShelf(int shelfNumber, Product product, int howMany) {
        Shelf shelf = getShelf(shelfNumber);
        shelf.supplyProduct(product, howMany);
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

            return formatPrice(missingMoneyInCents);
        }

        return "";
    }

    private static String formatPrice(int totalCents) {
        int dollars = totalCents / 100;
        int cents = totalCents - dollars*100;
        return String.format("$%d.%02d", dollars, cents);
    }

    public void selectShelf(int shelfNumber) {
        selectedShelf = getShelf(shelfNumber);
    }

    public void putCoin(Coin coin) {
        coinsCollector.addCoin(coin);
    }
}
