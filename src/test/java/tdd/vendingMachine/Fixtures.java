package tdd.vendingMachine;

public final class Fixtures {
    private Fixtures() {}

    public final static ProductType COLA_PRODUCT_TYPE = new ProductType("Cola", 320);
    public final static ProductType SPRITE_PRODUCT_TYPE = new ProductType("Sprite", 250);

    public static Product createCola() {
        return COLA_PRODUCT_TYPE.createProduct();
    }

    public static Product createSprite() {
        return SPRITE_PRODUCT_TYPE.createProduct();
    }

    public static VendingMachine getMachineWithColaCosting320AndPreselectedShelf() {
        VendingMachine vendingMachine = new VendingMachine(8);

        vendingMachine.supplyProductToShelf(1, COLA_PRODUCT_TYPE, 10);
        vendingMachine.selectShelf(1);

        return vendingMachine;
    }
}
