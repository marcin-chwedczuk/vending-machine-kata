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

        resetSelectedShelf();
    }

    private void resetSelectedShelf() {
        selectedShelf = null;
        moneyManager.setProductPrice(0);
        display.displayProductNotSelectedMessage();
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

    public String displayContents() {
        if (isShelfSelected()) {
            int missingMoneyInCents = moneyManager.missingMoneyInCents();
            display.displayMissingMoney(missingMoneyInCents);
        }

        return display.contents();
    }

    private boolean isShelfSelected() {
        return (selectedShelf != null);
    }

    public void selectShelf(int shelfNumber) {
        selectedShelf = getShelf(shelfNumber);
        moneyManager.setProductPrice(selectedShelf.productPriceInCents());
        display.showProductPrice(selectedShelf.productPriceInCents());
    }

    public void putCoin(CoinType coin) {
        moneyManager.acceptUserCoin(coin);

        if (isShelfSelected()) {
            if (moneyManager.userCanBuyProduct()) {
                buyProduct();
            }
            else if (moneyManager.missingMoneyInCents() == 0 && !moneyManager.canReturnChange()) {
                cancelOrder();
                display.displayCannotReturnChangeWarning();
            }
            else {
                display.displayMissingMoney(moneyManager.missingMoneyInCents());
            }
        }
    }

    private void buyProduct() {
        List<CoinType> change = moneyManager.buyProduct();
        changeDispenser.putAll(change);
        productDispenser.put(selectedShelf.removeProduct());
        resetSelectedShelf();
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

    public void cancelOrder() {
        List<CoinType> userMoney = moneyManager.giveUserMoneyBack();
        changeDispenser.putAll(userMoney);

        resetSelectedShelf();
    }
}
