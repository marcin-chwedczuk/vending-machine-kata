package tdd.vendingMachine;

public class MoneyManager {
    private final CoinsCollector userCoins;
    private final CoinsCollector machineCoins;
    private final ChangeCalculator changeCalculator;

    private int productPriceInCents;

    public MoneyManager() {
        userCoins = new CoinsCollector();
        machineCoins = new CoinsCollector();
        changeCalculator = new ChangeCalculator(machineCoins, userCoins);

        productPriceInCents = 0;
    }

    public void supplyCoins(CoinType coinType, int coinsCount) {
        machineCoins.addCoins(coinType, coinsCount);
    }

    public void acceptUserCoin(CoinType coinType) {
        userCoins.addCoin(coinType);
    }

    public void setProductPrice(int productPriceInCents) {
        this.productPriceInCents = productPriceInCents;
    }

    public int productPriceInCents() {
        return this.productPriceInCents;
    }

    public int missingMoneyInCents() {
        int missingMoneyInCents = productPriceInCents - userCoins.totalMoneyInCents();

        // user paid too much
        if (missingMoneyInCents < 0)
            missingMoneyInCents = 0;

        return missingMoneyInCents;
    }

    private int changeInCents() {
        int changeInCents = userCoins.totalMoneyInCents() - productPriceInCents;

        if (changeInCents < 0)
            changeInCents = 0;

        return changeInCents;
    }

    public boolean userCanBuyProduct() {
        if (missingMoneyInCents() > 0)
            return false;

        if (changeInCents() > 0 && !canReturnChange()) {
            return false;
        }

        return true;
    }

    private boolean canReturnChange() {
        return changeCalculator.canReturnChange(changeInCents());
    }
}
