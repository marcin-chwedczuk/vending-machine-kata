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

    public int shelvesCount() {
        return shelves.size();
    }

    public int numberOfProductInstancesOnShelf(int shelfNumber) {
        Shelf shelf = getShelf(shelfNumber);
        return shelf.numberOfProductInstances();
    }

    public void supplyProductToShelf(int shelfNumber, ProductType productType, int howMany) {
        Shelf shelf = getShelf(shelfNumber);
        shelf.supplyProduct(productType, howMany);
    }

    private Shelf getShelf(int shelfNumber) {
        if (shelfNumber < 1 || shelfNumber > shelves.size())
            throw new IllegalArgumentException(
                "invalid shelf number: " + shelfNumber);

        return shelves.get(shelfNumber-1);
    }

    private boolean isShelfSelected() {
        return (selectedShelf != null);
    }

    public void selectShelf(int shelfNumber) {
        Shelf self = getShelf(shelfNumber);
        if (self.isEmpty())
            return;

        selectedShelf = self;
        moneyManager.setProductPriceInCents(selectedShelf.productPriceInCents());
        updateMachineState();
    }

    private void resetSelectedShelf() {
        selectedShelf = null;
        moneyManager.setProductPriceInCents(0);
        display.showProductNotSelectedMessage();
    }

    public String displayContents() {
        return display.contents();
    }

    public VendingMachine putCoin(CoinType coin) {
        moneyManager.acceptUserCoin(coin);
        updateMachineState();
        return this;
    }

    private void updateMachineState() {
        if (!isShelfSelected())
            return;

        if (moneyManager.userCanBuyProduct()) {
            buyProduct();
        }
        else if (moneyManager.missingMoneyInCents() == 0 && !moneyManager.canReturnChange()) {
            cancelOrder();
            display.showCannotReturnChangeWarning();
        }
        else {
            display.showMissingMoney(moneyManager.missingMoneyInCents());
        }
    }

    private void buyProduct() {
        List<CoinType> change = moneyManager.buyProduct();
        changeDispenser.putAll(change);
        productDispenser.put(selectedShelf.removeProduct());
        resetSelectedShelf();
    }

    public void cancelOrder() {
        List<CoinType> userMoney = moneyManager.giveUserMoneyBack();
        changeDispenser.putAll(userMoney);

        resetSelectedShelf();
    }

    public Product getPurchasedProduct() {
        return productDispenser.get();
    }

    public List<CoinType> returnChange() {
        return changeDispenser.getAll();
    }

    public void supplyCoins(CoinType coinType, int count) {
        moneyManager.supplyCoins(coinType, count);
    }
}
