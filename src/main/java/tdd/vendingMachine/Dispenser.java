package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Dispenser<Item> {
    private final List<Item> waitingItems = new ArrayList<>();

    public void put(Item item) {
        if (item == null)
            throw new NullPointerException("item must be provided.");

        waitingItems.add(item);
    }

    public void putAll(Collection<Item> items) {
        if (items == null)
            throw new NullPointerException("items must be provided.");

        for(Item item : items) {
            put(item);
        }
    }

    public Item get() {
        if (waitingItems.isEmpty())
            return null;

        Item item = waitingItems.get(0);
        waitingItems.remove(0);
        return item;
    }

    public List<Item> getAll() {
        if (waitingItems.isEmpty())
            return null;

        List<Item> allItems = new ArrayList<>(waitingItems);
        waitingItems.clear();
        return allItems;
    }
}
