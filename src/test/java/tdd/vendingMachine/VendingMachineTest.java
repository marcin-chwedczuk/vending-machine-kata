package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine;

    private ProductType sprite;
    private ProductType cola;

    @Before
    public void before() {
        vendingMachine = new VendingMachine(8);

        sprite = new ProductType("Sprite", 300);
        cola = new ProductType("Cola", 500);
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
    public void display_shows_nothing_when_product_is_not_selected() {
        assertThat(vendingMachine.displayContents())
            .isEmpty();
    }

    @Test
    public void after_selecting_shelf_number_display_shows_product_price() {
        vendingMachine.supplyProductToShelf(3, cola, 10);

        vendingMachine.selectShelf(3);

        assertThat(vendingMachine.displayContents())
            .isEqualToIgnoringCase("$5.00");
    }

    @Test
    public void coins_500_200_100_50_20_10_cents_can_be_put_into_machine() {
        vendingMachine.supplyProductToShelf(1, Fixtures.COLA_PRODUCT_TYPE, 100);
        vendingMachine.selectShelf(1);

        vendingMachine.putCoin(CoinType.CENTS_500);
        vendingMachine.putCoin(CoinType.CENTS_200);
        vendingMachine.putCoin(CoinType.CENTS_100);
        vendingMachine.putCoin(CoinType.CENTS_50);
        vendingMachine.putCoin(CoinType.CENTS_20);
        vendingMachine.putCoin(CoinType.CENTS_10);
    }

    @Test
    public void after_selection_of_product_when_you_put_coins_into_machine_displays_shows_how_much_is_missing_to_cover_product_price() {
        vendingMachine.supplyProductToShelf(1, new ProductType("X", 400), 10);
        vendingMachine.selectShelf(1);

        vendingMachine.putCoin(CoinType.CENTS_10);
        assertThat(vendingMachine.displayContents()).isEqualToIgnoringCase("$3.90");

        vendingMachine.putCoin(CoinType.CENTS_200);
        assertThat(vendingMachine.displayContents()).isEqualToIgnoringCase("$1.90");

        vendingMachine.putCoin(CoinType.CENTS_50);
        assertThat(vendingMachine.displayContents()).isEqualToIgnoringCase("$1.40");
    }

    @Test
    public void after_you_put_enough_money_to_buy_a_product_machine_dispenses_product() {
        ProductType productX = new ProductType("X", 100);
        vendingMachine.supplyProductToShelf(1, productX, 10);

        vendingMachine.selectShelf(1);
        vendingMachine.putCoin(CoinType.CENTS_100);

        Product boughtProduct = vendingMachine.getPurchasedProduct();

        assertThat(boughtProduct)
            .isNotNull();

        assertThat(boughtProduct.productType())
            .isEqualTo(productX);
    }

    @Test
    public void coins_may_be_supplied_to_vending_machine() {
        vendingMachine.supplyCoins(CoinType.CENTS_20, 30);
    }

    @Test
    public void when_machine_cannot_return_change_it_will_not_release_product_and_it_will_return_user_money_and_display_warning() {
        // machine has only $5 coins
        vendingMachine.supplyCoins(CoinType.CENTS_500, 10);

        vendingMachine.supplyProductToShelf(1, new ProductType("X", 50), 5);
        vendingMachine.selectShelf(1);

        vendingMachine.putCoin(CoinType.CENTS_20);
        vendingMachine.putCoin(CoinType.CENTS_20);
        vendingMachine.putCoin(CoinType.CENTS_20);

        assertThat(vendingMachine.getPurchasedProduct())
            .isNull();

        assertThat(vendingMachine.displayContents())
            .isEqualToIgnoringCase("cannot return change");

        assertThat(vendingMachine.returnChange())
            .containsExactly(CoinType.CENTS_20, CoinType.CENTS_20, CoinType.CENTS_20);
    }

    @Test
    public void when_you_put_to_much_money_in_the_machine_machine_returns_product_and_change() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1AndSelf1Selected();

        vendingMachine.supplyCoins(CoinType.CENTS_50, 10);
        vendingMachine.supplyCoins(CoinType.CENTS_10, 3);

        vendingMachine.putCoin(CoinType.CENTS_500);

        assertThat(vendingMachine.getPurchasedProduct())
            .isNotNull();

        // $5 = $3.2 + $0.5*3 + $0.1*3
        assertThat(vendingMachine.returnChange())
            .containsExactly(CoinType.CENTS_50, CoinType.CENTS_50, CoinType.CENTS_50,
                CoinType.CENTS_10, CoinType.CENTS_10, CoinType.CENTS_10);
    }

    @Test
    public void machine_returns_change_only_after_you_buy_product() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1AndSelf1Selected();

        vendingMachine.supplyCoins(CoinType.CENTS_50, 10);
        vendingMachine.supplyCoins(CoinType.CENTS_10, 10);

        assertThat(vendingMachine.returnChange())
            .isNull();

        vendingMachine.putCoin(CoinType.CENTS_200);
        vendingMachine.putCoin(CoinType.CENTS_200);

        vendingMachine.returnChange();
        vendingMachine.getPurchasedProduct();

        assertThat(vendingMachine.returnChange())
            .isNull();
    }

    @Test
    public void user_can_press_cancel_to_get_his_money_back() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1AndSelf1Selected();

        vendingMachine.putCoin(CoinType.CENTS_100);
        vendingMachine.putCoin(CoinType.CENTS_200);

        assertThat(vendingMachine.returnChange())
            .isNull();

        vendingMachine.cancelOrder();

        assertThat(vendingMachine.returnChange())
            .containsExactly(CoinType.CENTS_200, CoinType.CENTS_100);

        assertThat(vendingMachine.returnChange())
            .isNull();
    }

    @Test
    public void when_user_press_cancel_product_is_not_dispensed() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1AndSelf1Selected();

        vendingMachine.putCoin(CoinType.CENTS_100);
        vendingMachine.putCoin(CoinType.CENTS_200);

        vendingMachine.cancelOrder();

        assertThat(vendingMachine.getPurchasedProduct())
            .isNull();
    }

    @Test
    public void user_can_buy_product_by_first_putting_coins_then_selecting_shelf() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1();

        vendingMachine
            .putCoin(CoinType.CENTS_200)
            .putCoin(CoinType.CENTS_100)
            .putCoin(CoinType.CENTS_20);

        vendingMachine.selectShelf(1);

        assertThat(vendingMachine.getPurchasedProduct())
            .isNotNull();
    }

    @Test
    public void shelf_without_product_cannot_be_selected() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1();

        vendingMachine.selectShelf(2);
        vendingMachine.putCoin(CoinType.CENTS_20);

        // check shelf not selected
        assertThat(vendingMachine.displayContents())
            .isEqualToIgnoringCase("");
    }

    @Test
    public void after_cancel_money_were_returned_to_user() {
        VendingMachine vendingMachine = Fixtures.getMachineWithColaCosting320OnShelf1AndSelf1Selected();

        vendingMachine.putCoin(CoinType.CENTS_100);
        vendingMachine.putCoin(CoinType.CENTS_200);

        vendingMachine.cancelOrder();
        vendingMachine.returnChange();

        vendingMachine.selectShelf(1);
        vendingMachine.putCoin(CoinType.CENTS_20);

        assertThat(vendingMachine.getPurchasedProduct())
            .isNull();
    }
}
