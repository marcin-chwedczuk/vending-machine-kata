package tdd.vendingMachine;

import java.util.List;

public class MoneyManager {
    private final CoinStore userCoinStore;
    private final CoinStore machineCoinStore;
    private final ChangeCalculator changeCalculator;

    private int productPriceInCents;

    public MoneyManager() {
        userCoinStore = new CoinStore();
        machineCoinStore = new CoinStore();
        changeCalculator = new ChangeCalculator(machineCoinStore, userCoinStore);

        productPriceInCents = 0;
    }

    public void supplyCoins(CoinType coinType, int coinsCount) {
        machineCoinStore.addCoins(coinType, coinsCount);
    }

    public void acceptUserCoin(CoinType coinType) {
        userCoinStore.addCoin(coinType);
    }

    public void setProductPriceInCents(int productPriceInCents) {
        this.productPriceInCents = productPriceInCents;
    }

    public int productPriceInCents() {
        return this.productPriceInCents;
    }

    public int missingMoneyInCents() {
        int missingMoneyInCents = productPriceInCents - userCoinStore.totalMoneyInCents();

        // user paid too much
        if (missingMoneyInCents < 0)
            missingMoneyInCents = 0;

        return missingMoneyInCents;
    }

    private int changeInCents() {
        int changeInCents = userCoinStore.totalMoneyInCents() - productPriceInCents;

        if (changeInCents < 0)
            changeInCents = 0;

        return changeInCents;
    }

    public boolean userCanBuyProduct() {
        if (missingMoneyInCents() > 0)
            return false;

        //noinspection RedundantIfStatement
        if (changeInCents() > 0 && !canReturnChange()) {
            return false;
        }

        return true;
    }

    public boolean canReturnChange() {
        return changeCalculator.canReturnChange(changeInCents());
    }

    public List<CoinType> buyProduct() {
        if (!userCanBuyProduct())
            throw new IllegalStateException("user cannot buy product.");

        List<CoinType> change = changeCalculator.getChange(changeInCents());

        userCoinStore.transferCoinsTo(machineCoinStore);
        machineCoinStore.removeCoins(change);

        return change;
    }

    public List<CoinType> giveUserMoneyBack() {
        return userCoinStore.giveMoneyBack();
    }
}
