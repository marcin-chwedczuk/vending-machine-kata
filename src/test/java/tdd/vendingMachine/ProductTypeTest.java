package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTypeTest {

    @Test
    public void product_type_must_have_a_product_name() {
        assertThatThrownBy(() -> new ProductType(null, 1))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new ProductType("", 1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void product_price_must_be_positive() {
        assertThatThrownBy(() -> new ProductType("foo", -3))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void productType_is_equal_to_other_productType_when_its_name_and_price_is_equal() {
        ProductType foo1 = new ProductType("foo", 1);
        ProductType foo2 = new ProductType("foo", 2);
        ProductType bar1 = new ProductType("bar", 1);
        ProductType foo1_copy = new ProductType("foo", 1);

        assertThat(foo1)
            .isEqualTo(foo1_copy)
            .isNotEqualTo(foo2)
            .isNotEqualTo(bar1);
    }

    @Test
    public void productType_can_be_used_to_create_instance_of_product() {
        ProductType foo = new ProductType("foo", 10);
        Product fooProduct = foo.createProduct();

        assertThat(fooProduct.productType())
            .isEqualTo(foo);
    }
}
