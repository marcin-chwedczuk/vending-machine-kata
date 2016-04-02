package tdd.vendingMachine;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CoinStore {
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

    public CoinStore addCoins(CoinType coin, int coinsCount) {
        if (coin == null)
            throw new NullPointerException("coin");

        if (coinsCount < 0)
            throw new IllegalArgumentException("coinsCount cannot be negative.");

        if (!coinsCounts.containsKey(coin)) {
            coinsCounts.put(coin, 0);
        }

        coinsCounts.put(coin, coinsCounts.get(coin)+coinsCount);
        return this;
    }

    public void removeCoins(List<CoinType> coins) {
        if (coins == null)
            throw new NullPointerException("coins");

        // TODO: checking for removing too much coins
        for(CoinType coin : coins) {
            coinsCounts.put(coin, coinsCounts.get(coin)-1);
        }
    }

    public int numberOfCoins(CoinType coinType) {
        if (!coinsCounts.containsKey(coinType))
            return 0;

        return coinsCounts.get(coinType);
    }

    public void transferCoinsTo(CoinStore other) {
        if (other == null)
            throw new NullPointerException("other");

        if (other == this)
            return;

        for(Map.Entry<CoinType, Integer> entry : coinsCounts.entrySet()) {
            other.addCoins(entry.getKey(), entry.getValue());
        }

        coinsCounts.clear();
    }

    public List<CoinType> giveMoneyBack() {
        List<CoinType> userCoins = coinsCounts.entrySet().stream()
            .flatMap(e -> Collections.nCopies(e.getValue(), e.getKey()).stream())
            .collect(Collectors.toList());

        coinsCounts.clear();
        
        return userCoins;
    }
}
