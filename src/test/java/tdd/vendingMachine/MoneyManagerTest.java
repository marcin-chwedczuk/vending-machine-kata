package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

public class MoneyManagerTest {
    private MoneyManager moneyManager;

    @Before
    public void before() {
        moneyManager = new MoneyManager();
    }

    @Test
    public void coins_may_be_supplied() {
        moneyManager.supplyCoins(CoinType.CENTS_50, 20);
        moneyManager.supplyCoins(CoinType.CENTS_20, 50);
    }

    @Test
    public void product_price_may_be_set() {
        moneyManager.setProductPriceInCents(200);
    }
}
