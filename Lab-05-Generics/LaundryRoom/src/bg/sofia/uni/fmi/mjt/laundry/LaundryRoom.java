package bg.sofia.uni.fmi.mjt.laundry;

import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.exception.AlreadyQueuedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyQueueException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.program.WashProgram;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LaundryRoom implements LaundryRoomAPI {
    private final Queue<WashJob<?>> queue;
    private final Map<String, Integer> ownerTokens;

    public LaundryRoom() {
        queue = new ArrayDeque<WashJob<?>>();
        ownerTokens = new HashMap<String, Integer>();
    }

    @Override
    public <G extends Garment> WashJob<G> scheduleWash(LaundryBasket<G> basket, WashProgram<? super G> program) {
        if (basket == null || basket.size() == 0) {
            throw new IllegalArgumentException("Basket cannot be null or empty.");
        }

        if (program == null) {
            throw new IllegalArgumentException("Program cannot be null.");
        }

        for (WashJob<?> washjob : queue) {
            if (washjob.basket() == basket) {
                throw new AlreadyQueuedException("Basket is already waiting in the queue.");
            }
        }

        WashJob<G> washJob = new WashJob<G>(basket, program);
        queue.add(washJob);

        return washJob;
    }

    @Override
    public boolean cancelWash(LaundryBasket<?> basket) {
        if (basket == null) {
            throw new IllegalArgumentException("Basket cannot be null.");
        }

        Iterator<WashJob<?>> iterator = queue.iterator();

        while (iterator.hasNext()) {
            WashJob<?> washJob = iterator.next();

            if (washJob.basket() == basket) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    @Override
    public WashJob<?> runNext() {
        if (queue.isEmpty()) {
            throw new EmptyQueueException("Queue is empty.");
        }

        WashJob<?> washJob = queue.remove();
        washJob.wash();
        String owner = washJob.basket().getOwner();

        ownerTokens.put(owner, ownerTokens.getOrDefault(owner, 0) + 1);

        return washJob;
    }

    @Override
    public List<WashJob<?>> getScheduledWashes() {
        return Collections.unmodifiableList(new ArrayList<>(queue));
    }

    @Override
    public int getTokensSpent(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner cannot be null or blank.");
        }

        return ownerTokens.getOrDefault(owner, 0);
    }
}
