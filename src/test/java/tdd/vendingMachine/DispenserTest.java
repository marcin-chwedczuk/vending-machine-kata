package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DispenserTest {
    private Dispenser<Product> productDispenser;

    @Before
    public void before() {
        productDispenser = new Dispenser<>();
    }

    @Test
    public void product_may_be_put_into_dispenser() {
        productDispenser.put(Fixtures.createCola());
    }

    @Test
    public void when_there_is_no_product_in_dispenser_getProduct_returns_null() {
        assertThat(productDispenser.get())
            .isNull();
    }

    @Test
    public void after_you_put_a_product_into_dispenser_you_may_get_that_product_only_once() {
        Product cola = Fixtures.createCola();

        productDispenser.put(cola);

        assertThat(productDispenser.get())
            .isEqualTo(cola);

        assertThat(productDispenser.get())
            .isNull();
    }

    @Test
    public void after_you_put_2_products_into_dispenser_you_may_get_them_in_any_order() {
        Product cola = Fixtures.createCola();
        Product sprite = Fixtures.createSprite();

        productDispenser.put(cola);
        productDispenser.put(sprite);

        List<Product> returnedProducts = new ArrayList<>();
        returnedProducts.add(productDispenser.get());
        returnedProducts.add(productDispenser.get());

        assertThat(returnedProducts)
            .contains(cola)
            .contains(sprite);

        assertThat(productDispenser.get())
            .isNull();
    }

}
