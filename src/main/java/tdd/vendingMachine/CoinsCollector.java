package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinsCollector {
    private final Map<CoinType, Integer> coinsCounts = new HashMap<>();

    public int totalMoneyInCents() {
        return coinsCounts.entrySet().stream()
            .map(entry -> entry.getKey().valueInCents()*entry.getValue())
            .mapToInt(Integer::intValue)
            .sum();
    }

    public void addCoin(CoinType coin) {
        addCoins(coin, 1);
    }

    public CoinsCollector addCoins(CoinType coinType, int coinsCount) {
        if (coinType == null)
            throw new NullPointerException("coin");

        if (coinsCount < 0)
            throw new IllegalArgumentException("coinsCount cannot be negative.");

        if (!coinsCounts.containsKey(coinType)) {
            coinsCounts.put(coinType, 0);
        }

        coinsCounts.put(coinType, coinsCounts.get(coinType)+coinsCount);
        return this;
    }

    public int numberOfCoins(CoinType coinType) {
        if (!coinsCounts.containsKey(coinType))
            return 0;

        return coinsCounts.get(coinType);
    }
}
