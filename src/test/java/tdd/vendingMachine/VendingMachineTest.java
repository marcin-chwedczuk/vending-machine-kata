package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine;

    private Product sprite;
    private Product cola;

    @Before
    public void before() {
        vendingMachine = new VendingMachine(8);

        sprite = new Product("Sprite", 3);
        cola = new Product("Cola", 5);
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

}
