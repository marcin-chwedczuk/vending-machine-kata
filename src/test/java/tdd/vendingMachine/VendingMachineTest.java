package tdd.vendingMachine;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine;

    private Product sprite;
    private Product cola;

    @Before
    public void before() {
        vendingMachine = new VendingMachine(8);

        sprite = new Product("Sprite", 300);
        cola = new Product("Cola", 500);
    }

    @Test
    public void product_can_be_added_to_vending_machine() {
        vendingMachine.supplyProductToShelf(1, sprite, 20);

        assertThat(vendingMachine.numberOfProductInstancesOnShelf(1))
            .isEqualTo(20);
    }

    @Test
    public void single_shelf_can_contain_only_single_product() {
        vendingMachine.supplyProductToShelf(1, sprite, 20);

        assertThatThrownBy(() -> vendingMachine.supplyProductToShelf(1, cola, 10))
            .isInstanceOf(VendingMachineException.class);
    }

    @Test
    public void two_different_shelves_can_contain_two_different_products() {
        vendingMachine.supplyProductToShelf(1, sprite, 20);

        vendingMachine.supplyProductToShelf(2, cola, 30);
    }

    @Test
    public void shelvesCount_returns_number_of_available_shelves() {
        assertThat(vendingMachine.shelvesCount())
            .isEqualTo(8);
    }

    @Test
    public void shelves_are_numbered_from_one() {
        vendingMachine.supplyProductToShelf(1, cola, 10);
        vendingMachine.supplyProductToShelf(8, cola, 10);

        assertThatThrownBy(() -> vendingMachine.supplyProductToShelf(0, cola, 1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void exception_is_thrown_when_product_is_added_to_non_existing_shelf() {
        assertThatThrownBy(() -> vendingMachine.supplyProductToShelf(101, cola, 1))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> vendingMachine.supplyProductToShelf(-32, cola, 1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void same_product_may_be_added_many_times_to_shelf() {
        vendingMachine.supplyProductToShelf(1, cola, 5);
        vendingMachine.supplyProductToShelf(1, cola, 5);

        assertThat(vendingMachine.numberOfProductInstancesOnShelf(1))
            .isEqualTo(10);
    }

    @Test
    public void vending_machine_has_a_display() {
        assertThat(vendingMachine.display()).isEmpty();
    }

    @Test
    public void after_selecting_shelf_number_display_shows_product_price() {
        vendingMachine.supplyProductToShelf(3, cola, 10);

        vendingMachine.selectShelf(3);

        assertThat(vendingMachine.display()).isEqualToIgnoringCase("$5.00");
    }

    @Test
    public void coins_500_200_100_50_20_10_cents_can_be_put_into_machine() {
        vendingMachine.putCoin(Coin.CENTS_500);
        vendingMachine.putCoin(Coin.CENTS_200);
        vendingMachine.putCoin(Coin.CENTS_100);
        vendingMachine.putCoin(Coin.CENTS_50);
        vendingMachine.putCoin(Coin.CENTS_20);
        vendingMachine.putCoin(Coin.CENTS_10);
    }

    @Test
    @Ignore
    public void coins_1_2_5_cents_cannot_be_used_with_machine() {
        // TODO:
    }

    @Test
    public void after_selection_of_product_when_you_put_coins_into_machine_displays_shows_how_much_is_missing_to_cover_product_price() {
        vendingMachine.supplyProductToShelf(1, new Product("X", 400), 10);
        vendingMachine.selectShelf(1);

        vendingMachine.putCoin(Coin.CENTS_10);
        assertThat(vendingMachine.display()).isEqualToIgnoringCase("$3.90");

        vendingMachine.putCoin(Coin.CENTS_200);
        assertThat(vendingMachine.display()).isEqualToIgnoringCase("$1.90");

        vendingMachine.putCoin(Coin.CENTS_50);
        assertThat(vendingMachine.display()).isEqualToIgnoringCase("$1.40");
    }

    @Test
    public void when_you_put_more_money_than_product_price_display_shows_zero() {
        vendingMachine.supplyProductToShelf(1, new Product("X", 400), 10);

        vendingMachine.selectShelf(1);
        vendingMachine.putCoin(Coin.CENTS_200);
        vendingMachine.putCoin(Coin.CENTS_50);
        vendingMachine.putCoin(Coin.CENTS_200);

        assertThat(vendingMachine.display()).isEqualToIgnoringCase("$0.00");
    }
}
