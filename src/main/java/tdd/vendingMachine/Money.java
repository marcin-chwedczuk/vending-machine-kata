package tdd.vendingMachine;

public class Money {
    private final static int CENTS_PER_DOLLAR = 100;

    private int cents;

    private Money(int cents) {
        this.cents = cents;
    }

    public int totalCents() {
        return cents;
    }

    public int dollars() {
        return cents / CENTS_PER_DOLLAR;
    }

    public int cents() {
        return totalCents() - dollars()*CENTS_PER_DOLLAR;
    }

    @Override
    public String toString() {
        return String.format("$%d.%02d", dollars(), cents());
    }

    public static Money fromDollars(int dollars) {
        return new Money(dollars*CENTS_PER_DOLLAR);
    }

    public static Money fromCents(int cents) {
        return new Money(cents);
    }
}
