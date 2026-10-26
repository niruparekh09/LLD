package domain;

import java.util.*;

public class CashInventory {
    private final Map<Denomination, Integer> notes = new EnumMap<>(
            Denomination.class
    );

    private static final Denomination[] DESCENDING_DENOMINATIONS = {
            Denomination.HUNDRED,
            Denomination.FIFTY,
            Denomination.TWENTY,
            Denomination.TEN,
            Denomination.FIVE,
            Denomination.ONE
    };

    public CashInventory() {
        for (Denomination denomination : Denomination.values()) {
            notes.put(denomination, 0);
        }
    }

    public synchronized void addNotes(
            Denomination denomination,
            int count
    ) {
        Objects.requireNonNull(denomination, "Denomination is required");

        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }

        notes.put(
                denomination,
                Math.addExact(notes.get(denomination), count)
        );
    }

    public synchronized int getCount(Denomination denomination) {
        Objects.requireNonNull(denomination, "Denomination is required");
        return notes.get(denomination);
    }

    public synchronized long getTotalValue() {
        long total = 0;

        for (var entry : notes.entrySet()) {
            total += (long) entry.getKey().getValue() * entry.getValue();
        }

        return total;
    }

    /**
     * Returns the exact change plan if payment can be accepted.
     * Otherwise returns Optional.empty() and leaves cash unchanged.
     *
     * Inserted notes are included when calculating available change.
     */
    public synchronized Optional<Map<Denomination, Integer>>
    acceptPaymentAndMakeChange(
            Map<Denomination, Integer> insertedNotes,
            int changeAmount
    ) {
        validateNotes(insertedNotes);

        if (changeAmount < 0) {
            throw new IllegalArgumentException(
                    "Change amount cannot be negative"
            );
        }

        Map<Denomination, Integer> working = new EnumMap<>(notes);

        // Simulate accepting the customer's notes.
        for (var entry : insertedNotes.entrySet()) {
            Denomination denomination = entry.getKey();
            int count = entry.getValue();

            working.put(
                    denomination,
                    Math.addExact(working.get(denomination), count)
            );
        }

        Map<Denomination, Integer> changePlan =
                findChange(changeAmount, working);

        if (changePlan == null) {
            return Optional.empty();
        }

        // Commit only after a complete change plan has been found.
        for (var entry : changePlan.entrySet()) {
            Denomination denomination = entry.getKey();
            working.put(
                    denomination,
                    working.get(denomination) - entry.getValue()
            );
        }

        notes.clear();
        notes.putAll(working);

        return Optional.of(Map.copyOf(changePlan));
    }

    /**
     * Collects all machine cash and resets its cash inventory.
     */
    public synchronized Map<Denomination, Integer> collectCash() {
        Map<Denomination, Integer> collected = new EnumMap<>(notes);
        for (Denomination denomination : Denomination.values()) {
            notes.put(denomination, 0);
        }
        return Map.copyOf(collected);
    }

    private Map<Denomination, Integer> findChange(
            int amount,
            Map<Denomination, Integer> availableNotes
    ) {
        if (amount < 0) {
            return null;
        }

        Map<Denomination, Integer> result = new EnumMap<>(
                Denomination.class
        );

        return findChangeRecursive(
                0, amount, availableNotes, result
        ) ? result : null;
    }

    private boolean findChangeRecursive(
            int index,
            int remaining,
            Map<Denomination, Integer> availableNotes,
            Map<Denomination, Integer> result
    ) {
        if (remaining == 0) {
            return true;
        }

        if (index == DESCENDING_DENOMINATIONS.length) {
            return false;
        }

        Denomination denomination = DESCENDING_DENOMINATIONS[index];
        int value = denomination.getValue();
        int available = availableNotes.get(denomination);
        int maxToUse = Math.min(available, remaining / value);

        // Try using the largest possible number of this denomination,
        // then backtrack if it prevents an exact solution.
        for (int count = maxToUse; count >= 0; count--) {
            if (count > 0) {
                result.put(denomination, count);
            } else {
                result.remove(denomination);
            }

            if (findChangeRecursive(
                    index + 1,
                    remaining - count * value,
                    availableNotes,
                    result
            )) {
                return true;
            }
        }

        result.remove(denomination);
        return false;
    }

    private void validateNotes(Map<Denomination, Integer> insertedNotes) {
        Objects.requireNonNull(insertedNotes, "Inserted notes are required");

        for (var entry : insertedNotes.entrySet()) {
            if (entry.getKey() == null ||
                    entry.getValue() == null ||
                    entry.getValue() < 0) {
                throw new IllegalArgumentException("Invalid inserted notes");
            }
        }
    }
}