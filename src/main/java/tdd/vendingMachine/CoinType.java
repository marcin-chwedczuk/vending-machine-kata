package tdd.vendingMachine;

public enum CoinType {
    CENTS_500(500),
    CENTS_200(200),
    CENTS_100(100),
    CENTS_50(50),
    CENTS_20(20),
    CENTS_10(10);

    private int valueInCents;

    CoinType(int valueInCents) {
        this.valueInCents = valueInCents;
    }

    public int valueInCents() {
        return this.valueInCents;
    }
}
