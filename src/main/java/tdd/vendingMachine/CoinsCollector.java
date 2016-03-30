package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class CoinsCollector {
    private List<Coin> coins;

    public CoinsCollector() {
        coins = new ArrayList<>();
    }

    public int totalMoneyInCents() {
        return coins.stream()
            .map(Coin::valueInCents)
            .mapToInt(Integer::intValue)
            .sum();
    }

    public void addCoin(Coin coin) {
        if (coin == null)
            throw new NullPointerException("coin");

        coins.add(coin);
    }
}
