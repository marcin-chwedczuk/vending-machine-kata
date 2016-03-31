package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class Dispenser<Item> {
    private List<Item> waitingItems = new ArrayList<>();

    public void put(Item item) {
        if (item == null)
            throw new NullPointerException("item must be provided.");

        waitingItems.add(item);
    }

    public Item get() {
        if (waitingItems.isEmpty())
            return null;

        Item item = waitingItems.get(0);
        waitingItems.remove(0);
        return item;
    }
}
