package bg.sofia.uni.fmi.mjt.laundry.basket;

import bg.sofia.uni.fmi.mjt.laundry.exception.BasketOverloadedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyBasketException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class LaundryBasket<G extends Garment> implements Iterable<G> {
    private final String owner;
    private final double capacityKg;
    private final Stack<G> garments;

    /**
     * @param owner      the owner of the basket
     * @param capacityKg the maximum total weight of the garments in the basket, in kilograms
     * @throws IllegalArgumentException if the owner is null or blank, or the capacity is not positive
     */
    public LaundryBasket(String owner, double capacityKg) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner cannot be null or blank.");
        }

        if (capacityKg <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }

        this.owner = owner;
        this.capacityKg = capacityKg;
        garments = new Stack<>();
    }

    /**
     * @return the owner of the basket
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return the capacity of the basket in kilograms
     */
    public double getCapacityKg() {
        return capacityKg;
    }

    /**
     * @return the total weight of the garments currently in the basket, in kilograms
     */
    public double getWeightKg() {
        return getTotalWeight(garments);
    }

    private double getTotalWeight(Collection<? extends G> garments) {
        double totalWeight = 0;

        for (G garment : garments) {
            totalWeight += garment.getWeightKg();
        }

        return totalWeight;
    }

    /**
     * @return the number of garments currently in the basket
     */
    public int size() {
        return garments.size();
    }

    /**
     * Puts a garment on top of the basket.
     *
     * @param garment the garment to put in
     * @throws IllegalArgumentException  if the garment is null
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the basket
     */
    public void add(G garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment cannot be null.");
        }

        if (getWeightKg() + garment.getWeightKg() > capacityKg) {
            throw new BasketOverloadedException("Basket overloaded.");
        }

        garments.add(garment);
    }

    /**
     * Puts all given garments on top of the basket in the iteration order of the collection.
     * Either all garments are put in, or none of them.
     *
     * @param garments the garments to put in
     * @throws IllegalArgumentException  if the collection is null or contains null
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the basket
     */
    public void addAll(Collection<? extends G> garments) {
        if (garments == null) {
            throw new IllegalArgumentException("Collection cannot be null.");
        }

        for (G garment : garments) {
            if (garment == null) {
                throw new IllegalArgumentException("Collection cannot contain null elements.");
            }
        }

        if (getWeightKg() + getTotalWeight(garments) > capacityKg) {
            throw new BasketOverloadedException("Basket overloaded.");
        }

        this.garments.addAll(garments);
    }

    /**
     * Moves all garments from this basket on top of the other one, preserving their stack order
     * (this basket's top becomes the other basket's top). After a move, this basket is empty.
     * Either all garments are moved, or none of them.
     *
     * @param other the basket to move the garments to
     * @return the number of moved garments
     * @throws IllegalArgumentException  if other is null or is the same object as this basket
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the other basket
     */
    public int moveAllTo(LaundryBasket<? super G> other) {
        if (other == null || other == this) {
            throw new IllegalArgumentException("Other basket cannot be null or same object.");
        }

        if (getWeightKg() + other.getWeightKg() > other.getCapacityKg()) {
            throw new BasketOverloadedException("Basket overloaded.");
        }

        int movedGarments = size();
        other.garments.addAll(garments);
        garments.clear();

        return movedGarments;
    }

    /**
     * Takes out and returns the garment on top of the basket.
     *
     * @return the garment on top of the basket
     * @throws EmptyBasketException if the basket is empty
     */
    public G takeTop() {
        if (size() == 0) {
            throw new EmptyBasketException("Basket is empty.");
        }

        return garments.pop();
    }

    /**
     * @return an iterator over the garments in the basket in LIFO order (from top to bottom)
     */
    @Override
    public Iterator<G> iterator() {
        return garments.reversed().iterator();
    }
}