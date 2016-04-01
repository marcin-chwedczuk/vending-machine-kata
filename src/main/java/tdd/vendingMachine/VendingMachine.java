package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private final MoneyManager moneyManager;
    private final Dispenser<Product> productDispenser;
    private final Dispenser<CoinType> changeDispenser;
    private final Display display;
    private final List<Shelf> shelves;

    private Shelf selectedShelf;

    public VendingMachine(int shelvesCount) {
        moneyManager = new MoneyManager();
        productDispenser = new Dispenser<>();
        changeDispenser = new Dispenser<>();
        display = new Display();

        shelves = new ArrayList<>();

        for (int i = 0; i < shelvesCount; i++)
            shelves.add(new Shelf());
    }

    private void resetSelectedShelf() {
        selectedShelf = null;
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
        if (isShelfSelected()) {
            int missingMoneyInCents = moneyManager.missingMoneyInCents();
            return display.displayMissingMoney(missingMoneyInCents);
        }

        return display.displayProductNotSelectedMessage();
    }

    private boolean isShelfSelected() {
        return (selectedShelf != null);
    }

    public void selectShelf(int shelfNumber) {
        selectedShelf = getShelf(shelfNumber);
        moneyManager.setProductPrice(selectedShelf.productPriceInCents());
    }

    public void putCoin(CoinType coin) {
        moneyManager.acceptUserCoin(coin);

        if (isShelfSelected() && moneyManager.userCanBuyProduct()) {
            List<CoinType> change = moneyManager.buyProduct();
            changeDispenser.putAll(change);
            productDispenser.put(selectedShelf.removeProduct());
            resetSelectedShelf();
        }
    }

    public Product releaseProduct() {
        return productDispenser.get();
    }

    public void supplyCoins(CoinType coinType, int count) {
        moneyManager.supplyCoins(coinType, count);
    }

    public List<CoinType> releaseChange() {
        return changeDispenser.getAll();
    }
}
