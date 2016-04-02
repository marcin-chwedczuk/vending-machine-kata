package tdd.vendingMachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class ComputeChangeAlgorithm {

    private final Map<CoinType, Integer> availableCoins;
    private final Integer changeInCents;

    private final Solution[] _solution;

    public ComputeChangeAlgorithm(Map<CoinType, Integer> availableCoins, Integer changeInCents) {
        if (availableCoins == null)
            throw new NullPointerException("availableCoins");

        if (changeInCents <= 0)
            throw new IllegalArgumentException("change cannot be zero or negative.");

        this.availableCoins = availableCoins;
        this.changeInCents = changeInCents;

        _solution = new Solution[changeInCents+1];
        for (int i = 10; i < _solution.length; i += 10)
            _solution[i] = new Solution(null, Integer.MAX_VALUE, null);

        // dummy item
        _solution[0] = new Solution(CoinType.CENTS_10, 0, availableCoins);
    }

    public Map<CoinType, Integer> getChange() {
        // we use dynamic programming as suggested in:
        // https://en.wikipedia.org/wiki/Change-making_problem

        for (int change = 10; change <= changeInCents; change += 10) {
            for(CoinType coin : CoinType.values()) {
                int rest = change - coin.valueInCents();

                if (rest < 0) continue;
                if (!_solution[rest].solutionExists()) continue;
                if (!_solution[rest].isCoinAvailable(coin)) continue;

                int newSolutionNOfCoins = 1 + _solution[rest].totalCoins;
                if (!_solution[change].solutionExists() || _solution[change].totalCoins > newSolutionNOfCoins) {
                    _solution[change] = new Solution(
                        coin,
                        newSolutionNOfCoins,
                        _solution[rest].getAvailableCoinsWithout(coin));
                }
            }
        }

        if (!_solution[changeInCents].solutionExists())
            return null;

        return reconstructChange();
    }

    private Map<CoinType, Integer> reconstructChange() {
        int change = changeInCents;

        Map<CoinType, Integer> counts = Arrays.asList(CoinType.values())
            .stream()
            .collect(Collectors.toMap(coin -> coin, coin -> 0));

        while (change > 0) {
            CoinType coin = _solution[change].coin;
            counts.put(coin, counts.get(coin)+1);
            change -= coin.valueInCents();
        }

        return counts;
    }

    private static class Solution {
        final CoinType coin;
        final int totalCoins;
        final Map<CoinType, Integer> availableCoins;

        public Solution(CoinType coin, int totalCoins, Map<CoinType, Integer> availableCoins) {
            this.coin = coin;
            this.totalCoins = totalCoins;
            this.availableCoins = availableCoins;
        }

        public boolean solutionExists() {
            return this.coin != null;
        }

        public boolean isCoinAvailable(CoinType coin) {
            Integer count = availableCoins.get(coin);
            return count != null && (count > 0);
        }

        public Map<CoinType,Integer> getAvailableCoinsWithout(CoinType coin) {
            Map<CoinType, Integer> clone = new HashMap<>(availableCoins);
            clone.put(coin, clone.get(coin)-1);
            return clone;
        }
    }
}
