package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ChangeCalculatorTest {
    private ChangeCalculator changeCalculator;

    @Before
    public void before() {
        CoinStore machineCoins = new CoinStore();
        CoinStore userCoins = new CoinStore();

        machineCoins
            .addCoins(CoinType.CENTS_100, 3)
            .addCoins(CoinType.CENTS_20, 2);

        userCoins
            .addCoins(CoinType.CENTS_500, 1)
            .addCoins(CoinType.CENTS_10, 1);

        changeCalculator = new ChangeCalculator(machineCoins, userCoins);
    }

    @Test
    public void returns_list_of_coins_that_will_be_returned_to_user_as_change_when_machine_is_able_to_return_change() {
        assertThat(changeCalculator.getChange(20))
            .containsExactly(CoinType.CENTS_20);

        assertThat(changeCalculator.getChange(40))
            .containsExactly(CoinType.CENTS_20, CoinType.CENTS_20);

        assertThat(changeCalculator.getChange(350))
            .containsExactly(CoinType.CENTS_100, CoinType.CENTS_100, CoinType.CENTS_100,
                CoinType.CENTS_20, CoinType.CENTS_20, CoinType.CENTS_10);
    }

    @Test
    public void returns_null_if_machine_cannot_return_change_using_coins_it_have() {
        assertThat(changeCalculator.getChange(400))
            .isNull();

        assertThat(changeCalculator.getChange(160))
            .isNull();

        assertThat(changeCalculator.getChange(900))
            .isNull();
    }

    @Test
    public void bug1_simple_algorithm_not_works() {
        CoinStore machineCoins = new CoinStore();
        machineCoins.addCoin(CoinType.CENTS_50);
        machineCoins.addCoins(CoinType.CENTS_20, 3);

        ChangeCalculator changeCalculator = new ChangeCalculator(machineCoins, new CoinStore());

        assertThat(changeCalculator.getChange(60))
            .containsExactly(CoinType.CENTS_20, CoinType.CENTS_20, CoinType.CENTS_20);
    }
}
