package tdd.vendingMachine;

import java.util.*;

public class ChangeCalculator {
    private final CoinStore machineCoins;
    private final CoinStore userCoins;

    public ChangeCalculator(CoinStore machineCoins, CoinStore userCoins) {
        this.machineCoins = machineCoins;
        this.userCoins = userCoins;
    }

    public List<CoinType> getChange(int changeInCents) {
        List<CoinType> change = new ArrayList<>();

        for(CoinType coin : getCoinsInDescendingOrder()) {
            int availableCoins = machineCoins.numberOfCoins(coin) + userCoins.numberOfCoins(coin);

            while (changeInCents >= coin.valueInCents() && availableCoins > 0) {
                change.add(coin);
                availableCoins--;
                changeInCents -= coin.valueInCents();
            }
        }

        if (changeInCents > 0)
            return null;

        return change;
    }

    public boolean canReturnChange(int changeInCents) {
        return getChange(changeInCents) != null;
    }

    private CoinType[] getCoinsInDescendingOrder() {
        CoinType[] coinTypes = CoinType.values();

        Arrays.sort(coinTypes, (o1, o2) -> {
            // coin with greater values goes first
            return -Integer.compare(o1.valueInCents(), o2.valueInCents());
        });

        return coinTypes;
    }

}
