package tdd.vendingMachine;

public final class Fixtures {
    private Fixtures() {}

    public final static ProductType COLA_PRODUCT_TYPE = new ProductType("Cola", 200);
    public final static ProductType SPRITE_PRODUCT_TYPE = new ProductType("Sprite", 250);

    public static Product createCola() {
        return COLA_PRODUCT_TYPE.createProduct();
    }

    public static Product createSprite() {
        return SPRITE_PRODUCT_TYPE.createProduct();
    }
}
