package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {
    @Test
    public void product_must_have_a_name() {
        assertThatThrownBy(() -> new Product(null, 1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void product_price_must_be_positive() {
        assertThatThrownBy(() -> new Product("foo", -3))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void product_is_equal_to_other_product_when_its_name_and_price_is_equal() {
        Product foo1 = new Product("foo", 1);
        Product foo2 = new Product("foo", 2);
        Product bar1 = new Product("bar", 1);
        Product foo1_copy = new Product("foo", 1);

        assertThat(foo1)
            .isEqualTo(foo1_copy)
            .isNotEqualTo(foo2)
            .isNotEqualTo(bar1);
    }
}
