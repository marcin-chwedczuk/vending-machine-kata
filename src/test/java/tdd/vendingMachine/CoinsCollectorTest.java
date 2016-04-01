package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CoinsCollectorTest {
    private CoinStore coinsCollector;

    @Before
    public void before() {
        coinsCollector = new CoinStore();
    }

    @Test
    public void empty_collector_contains_0_cents() {
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(0);
    }

    @Test
    public void totalMoneyInCents_returns_sum_of_money_in_collector() {
        coinsCollector.addCoin(CoinType.CENTS_20);
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(20);

        coinsCollector.addCoin(CoinType.CENTS_200);
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(220);

        coinsCollector.addCoin(CoinType.CENTS_10);
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(230);
    }
}
