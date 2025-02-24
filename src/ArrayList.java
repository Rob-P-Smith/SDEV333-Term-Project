import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Implementation of the ArrayList<E> using the List<E> interface. This is my term assignment 1 Linked List.
 *
 * @param <E> class / data type of the items in the ArrayList
 * @author Rob Smith
 * @version 1.0
 */
public class ArrayList<E> implements List<E> {

    private int size;
    private E[] buffer;

    /**
     * Constructor for the ArrayList<> class.
     * Runtime is O(1). It always takes the same time to perform this construction.
     */
    public ArrayList() {
        this.size = 0;
        this.buffer = (E[])new Object[10];
    }

    /**
     * Add item to the front.
     * Runtime is O(n) if it doesn't need to resize. The method loops the through the array one time.
     * If it needs to resize the runtime is O(n)+runtime of doubleSize() which is O(1) + the runtime of resize which
     * is O(n). The total time if a resize is triggered is still O(n) but it has a higher constant.
     *
     * @param item the item to be added
     */
    @Override
    public void addFront(E item) {
        doubleSize();
        for (int i = size; i >= 1; i--) {
            buffer[i] = buffer[i - 1];
        }
        buffer[0] = item;
        size++;
    }

    /**
     * Add item to the back.
     * Runtime for this is O(1) if it doesn't need to resize. It always takes the same time to add to the back of the array.
     * If a resize is triggered the runtime is O(n).
     *
     * @param item the item to be added
     */
    @Override
    public void addBack(E item) {
        doubleSize();
        buffer[size] = item;
        size++;
    }

    /**
     * Add an item at specified index (position).
     * Runtime for this is O(n) if it doesn't need to resize with a possible low constant.
     * If a resize is triggered the runtime is O(n) with a greater constant.
     *
     * @param index the index where the item should be added
     * @param item  the item to be added
     */
    @Override
    public void add(int index, E item) {
        doubleSize();
        if (index < 0 || index > size || size == 0) {
            throw new IndexOutOfBoundsException("Index ouf of bounds. NOPE!");
        } else {
            for (int i = size; i > index; i--) {
                buffer[i] = buffer[i - 1];
            }
            buffer[index] = item;
            size++;
        }
    }

    /**
     * Get the item at a specified index.
     * Runtime for this is O(1). Retrieval is always a constant time function on arrays that are indexed.
     *
     * @param index the index where the item should be retrieved
     * @return the item located at that index
     */
    @Override
    public E get(int index) {
        if (index >= 0 && index < size) {
            return buffer[index];
        } else {
            throw new IndexOutOfBoundsException("Index cannot be outside the size of the array.");
        }
    }

    /**
     * Set (save) an item at a specified index. Previous
     * item at that index is overwritten.
     * Runtime for this is O(1). Since it's indexed, there is no search time, so it takes constant time.
     *
     * @param index the index where the item should be saved
     * @param item  the item to be saved
     */
    @Override
    public void set(int index, E item) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index cannot be outside the size of the array.");
        }
        buffer[index] = item;
    }

    /**
     * Remove item at the front of the list.
     * Runtime for this method is O(n) because of the shifting left that must occur of the remained of the array.
     *
     * @return the item that was removed
     */
    @Override
    public E removeFront() {
        if (size <= 0) {
            throw new NoSuchElementException("Cannot remove element from an empty array");
        }
        E result = buffer[0];
        for (int i = 0; i < size; i++) {
            buffer[i] = buffer[i + 1];
        }
        buffer[size] = null;
        size--;
        return result;
    }

    /**
     * Remove item at the back of the list
     * Runtime for this method is O(1). It always takes the same time to remove the last element.
     *
     * @return the item that was removed
     */
    @Override
    public E removeBack() {
        if (size <= 0) {
            throw new NoSuchElementException("Cannot remove element from empty array");
        }
        E result = buffer[size - 1];
        buffer[size - 1] = null;
        size--;
        return result;
    }

    /**
     * Remove item from the list
     * Runtime for this is O(n) with a constant of 1. If it's not found it goes through the array once.
     * If it is found the second loop picks up where the first loop stopped and finishes looping through.
     * Then the first loop finishes looping through the array. It has a constant of ~2.
     *
     * @param item the item to be removed
     */
    @Override
    public void remove(E item) {
        for (int i = 0; i < size; i++) {
            if (buffer[i] == item) {
                for (int k = i; k < size - 1; k++) {
                    buffer[k] = buffer[k + 1];
                }
                size--;
            }
        }
    }

    /**
     * Remove item at a specified index.
     * The runtime of this method is O(n) because the worst case the index provided is 0, and it has to shift
     * the whole array left one index.
     *
     * @param index the index where the item should be removed
     * @return the item that was removed
     */
    @Override
    public E remove(int index) {
        E value = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds at position " + index);
        } else if (index < size - 1) {
            value = buffer[index];
            for (int i = index; i < size; i++) {
                buffer[i] = buffer[i + 1];
            }
            buffer[size] = null;
            size--;
        } else if (index == size - 1) {
            value = buffer[index];
            buffer[index] = null;
            size--;
        }
        return value;
    }

    /**
     * Checks if an item is in the list.
     * This method has a runtime of O(n) because it loops through the array and if the element is not present
     * or is the last position, it goes through the whole array.
     *
     * @param item the item to search for
     * @return true if the item is in the list, false otherwise
     */
    @Override
    public boolean contains(E item) {
        for (int i = 0; i < size; i++) {
            if (buffer[i] == item) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the list is empty.
     * Runtime of this is O(1). It's simply returning a single boolean with no loops.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Provides a count of the number of items in the list.
     * Runtime of this method is O(1). It's returning an int and doesn't loop.
     *
     * @return number of items in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Helper method that doubles the array size using the resize method.
     * Runtime for this method is O(1) because it doesn't loop anything and is just a helper method to clean
     * up implementation elsewhere of resize.
     */
    private void doubleSize() {
        if (size == buffer.length) {
            resize(buffer.length * 2);
        }
    }

    /**
     * Grows and Shrinks the array as required by data size.
     * Runtime for this method is O(n). An arraycopy loops through the original array from start to finish while
     * copying.
     */
    private void resize(int newSize) {
        E[] newBuffer = (E[]) new Object[newSize];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        buffer = newBuffer;
    }


    /**
     * Custom toString for this arraylist implementation that returns a pretty print display of the contents
     * Runtime for this method is O(n).It must loop through the entire array to convert it to a string for printing.
     *
     * @return a string of the contents of the arraylist
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                sb.append(buffer[i]);
                sb.append(',');
                sb.append(' ');
            }
            sb.append(buffer[size - 1]);
            sb.append(']');
        } else {
            return "null";
        }
        return sb.toString();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * Runtime for this method is O(1) because it simply8 instantiates an object and returns it.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        List.super.forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="../util/Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is un-sized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }

    private class ArrayListIterator implements Iterator<E> {
        private int i;

        private ArrayListIterator() {
            this.i = 0;
        }

        /**
         * Tracker of if there's a next for the iterator.
         *
         * @return returns the boolean representing if there's another element after the current element.
         */
        public boolean hasNext() {
            return i < size;
        }

        /**
         * Item to retrieve the element values for the iterator.
         * Runtime is O(1) because it's only looking at a single location and not looping.
         *
         * @return the data of the current element.
         */
        public E next() {
            E currentItem = buffer[i];
            i++;
            return currentItem;
        }
    }
}
