package tdd.vendingMachine;

import java.util.*;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.*;

@RunWith(Parameterized.class)
public class ComputeChangeAlgorithmTest {
    private final Map<CoinType, Integer> availableCoins;
    private final Map<CoinType, Integer> expectedChange;
    private final Integer changeInCents;

    public ComputeChangeAlgorithmTest(
        Map<CoinType, Integer> availableCoins,
        Integer changeInCents,
        Map<CoinType, Integer> expectedChange)
    {
        this.availableCoins = availableCoins;
        this.expectedChange = expectedChange;
        this.changeInCents = changeInCents;
    }

    @Test
    public void algorithm_works() {
        ComputeChangeAlgorithm algorithm =
            new ComputeChangeAlgorithm(availableCoins, changeInCents);

        Map<CoinType, Integer> actualChange = algorithm.getChange();

        // we use Map.equals to compare if two maps contains same entries
        assertThat(actualChange)
            .as("failure for change: " + changeInCents)
            .isEqualTo(expectedChange);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // format:
            // { availableCoins, changeInCents, expectedChange (null when change cannot be given) }

            // test cases when we can give change:
            {
                coins("1*$2 1*$0.5 3*$0.2"),
                260,
                coins("1*$2 3*$0.2")
            },

            {
                coins("10*$1 10*$0.5 10*$0.2 10*$0.1"),
                350,
                coins("3*$1 1*$0.5")
            },

            {
                coins("10*$1 10*$0.5 10*$0.2 10*$0.1"),
                370,
                coins("3*$1 1*$0.5 1*$0.2")
            },

            {
                coins("10*$1 10*$0.5 10*$0.2 10*$0.1"),
                330,
                coins("3*$1 1*$0.2 1*$0.1")
            },

            {
                coins("10*$1 10*$0.5 10*$0.2 10*$0.1"),
                80,
                coins("1*$0.5 1*$0.2 1*$0.1")
            },

            {
                coins("0*$1 0*$0.5 3*$0.2 10*$0.1"),
                80,
                coins("3*$0.2 2*$0.1")
            },

            {
                coins("0*$1 0*$0.5 5*$0.2 10*$0.1"),
                80,
                coins("4*$0.2")
            },

            {
                // sum equal to all coins
                coins("1*$0.5 1*$0.2 1*$0.1"),
                80,
                coins("1*$0.5 1*$0.2 1*$0.1")
            },

            // test cases when we CANNOT give change:
            {
                coins("3*$1 5*$0.5 3*$0.2 0*$0.1"),
                380,
                null
            },

            {
                // only even coins
                coins("3*$1 0*$0.5 10*$0.2 0*$0.1"),
                390,
                null
            },

            {
                // sum too big
                coins("3*$1 3*$0.5 1*$0.1"),
                500,
                null
            },

            {
                // no $0.1 coin
                coins("3*$5 2*$2 3*$0.5"),
                510,
                null
            }

        });
    }

    // format: "10*$5 3*$2 10*$0.1
    private static Map<CoinType, Integer> coins(String coinsCounts) {
        String[] parts = coinsCounts.split(" ");

        Map<CoinType, Integer> counts = new HashMap<>();
        for(CoinType type : CoinType.values())
            counts.put(type, 0);

        for(String part : parts) {
            String[] countCoin = part.split("\\*\\$");

            int count = Integer.valueOf(countCoin[0]);

            CoinType coin;
            switch (countCoin[1]) {
                case "0.1": coin = CoinType.CENTS_10;  break;
                case "0.2": coin = CoinType.CENTS_20;  break;
                case "0.5": coin = CoinType.CENTS_50;  break;
                case "1"  : coin = CoinType.CENTS_100; break;
                case "2"  : coin = CoinType.CENTS_200; break;
                case "5"  : coin = CoinType.CENTS_500; break;
                default:
                    throw new RuntimeException("invalid format: " + part);
            }

            counts.put(coin, count);
        }

        return counts;
    }
}
