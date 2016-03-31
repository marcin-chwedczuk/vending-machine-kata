package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {
    private ProductType productType;
    private Product product;

    @Before
    public void before() {
        productType = new ProductType("foo", 10);
        product = new Product(productType);
    }

    @Test
    public void productType_returns_type_of_the_product() {
        assertThat(product.productType())
            .isEqualTo(productType);
    }

    @Test
    public void name_returns_product_name() {
        assertThat(product.name())
            .isEqualTo("foo");
    }

    @Test
    public void price_returns_product_price() {
        assertThat(product.priceInCents())
            .isEqualTo(10);
    }
}
