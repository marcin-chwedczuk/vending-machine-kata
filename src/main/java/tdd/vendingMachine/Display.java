package tdd.vendingMachine;

public class Display {
    public String displayMissingMoney(int missingMoneyInCents) {
        return formatPrice(missingMoneyInCents);
    }

    private static String formatPrice(int totalCents) {
        int dollars = totalCents / 100;
        int cents = totalCents - dollars*100;
        return String.format("$%d.%02d", dollars, cents);
    }

    public String displayProductNotSelectedMessage() {
        return "";
    }
}
