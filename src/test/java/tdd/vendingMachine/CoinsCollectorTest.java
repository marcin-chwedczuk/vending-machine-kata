package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CoinsCollectorTest {
    private CoinsCollector coinsCollector;

    @Before
    public void before() {
        coinsCollector = new CoinsCollector();
    }

    @Test
    public void empty_collector_contains_0_cents() {
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(0);
    }

    @Test
    public void totalMoneyInCents_returns_sum_of_money_in_collector() {
        coinsCollector.addCoin(Coin.CENTS_20);
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(20);

        coinsCollector.addCoin(Coin.CENTS_200);
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(220);

        coinsCollector.addCoin(Coin.CENTS_10);
        assertThat(coinsCollector.totalMoneyInCents())
            .isEqualTo(230);
    }
}
