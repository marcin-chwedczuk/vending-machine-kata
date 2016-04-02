package tdd.vendingMachine;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChangeCalculator {
    private final CoinStore machineCoins;
    private final CoinStore userCoins;

    public ChangeCalculator(CoinStore machineCoins, CoinStore userCoins) {
        this.machineCoins = machineCoins;
        this.userCoins = userCoins;
    }

    public List<CoinType> getChange(int changeInCents) {
        if (changeInCents == 0)
            return new ArrayList<>();

        Map<CoinType, Integer> counts = Arrays.asList(CoinType.values())
            .stream()
            .collect(Collectors.toMap(
                coin -> coin,
                coin -> machineCoins.numberOfCoins(coin) + userCoins.numberOfCoins(coin)));

        ComputeChangeAlgorithm algorithm = new ComputeChangeAlgorithm(counts, changeInCents);

        Map<CoinType, Integer> change = algorithm.getChange();
        if (change == null)
            return null;

        //noinspection UnnecessaryLocalVariable
        List<CoinType> listOfCoins = change.entrySet().stream()
            .flatMap(e -> Collections.nCopies(e.getValue(), e.getKey()).stream())
            .sorted((a, b) -> -Integer.compare(a.valueInCents(), b.valueInCents()))
            .collect(Collectors.toList());

        return listOfCoins;
    }

    public boolean canReturnChange(int changeInCents) {
        return getChange(changeInCents) != null;
    }
}
