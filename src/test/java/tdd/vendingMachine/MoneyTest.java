package tdd.vendingMachine;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class MoneyTest {
    @Test
    public void can_create_money_from_given_sum_of_dollars() {
        Money m = Money.fromDollars(5);

        assertThat(m.dollars()).isEqualTo(5);
        assertThat(m.cents()).isEqualTo(0);
    }

    @Test
    public void can_create_money_from_given_sum_of_cents() {
        Money m = Money.fromCents(520);

        assertThat(m.dollars()).isEqualTo(5);
        assertThat(m.cents()).isEqualTo(20);
    }

    @Test
    public void toString_returns_monetary_value_in_standard_format() {
        // format: $0.69 or $10.00

        Money tenDollars = Money.fromDollars(10);
        Money sixtyNineCents = Money.fromCents(69);

        assertThat(tenDollars.toString()).isEqualToIgnoringCase("$10.00");
        assertThat(sixtyNineCents.toString()).isEqualToIgnoringCase("$0.69");
    }
}
