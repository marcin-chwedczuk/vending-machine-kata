package tdd.vendingMachine;

public class Display {
    private String contents;

    public String contents() {
        return contents;
    }

    public void showMissingMoney(int missingMoneyInCents) {
        contents = formatPrice(missingMoneyInCents);
    }

    public void showProductNotSelectedMessage() {
        contents = "";
    }

    public void showCannotReturnChangeWarning() {
        contents = "Cannot return change";
    }

    private static String formatPrice(int totalCents) {
        final int CENTS_PER_DOLLAR = 100;

        int dollars = totalCents / CENTS_PER_DOLLAR;
        int cents = totalCents - dollars*CENTS_PER_DOLLAR;
        return String.format("$%d.%02d", dollars, cents);
    }
}
