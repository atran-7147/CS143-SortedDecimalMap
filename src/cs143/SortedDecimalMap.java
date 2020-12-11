package cs143;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class creates a Sorted Decimal Map using a Decimal Search Tree.
 *
 * @author Jonathan Fuller
 * @author Leiana Omine
 * @author Tyler Rose
 * @author Andy Tran
 * @param <E>
 */
public class SortedDecimalMap<E extends DecimalSortable>
        implements DecimalSearchTree<E> {

    //private fields
    private DecimalNode root;
    private int digitCount;

    /**
     * Constructor.
     *
     * @param digitCount the largest number of digits a sorting key will contain
     * in this sorted decimal map
     */
    public SortedDecimalMap(int digitCount) {
        root = new DecimalNode();
        this.digitCount = digitCount;
    }

    /**
     * Reports if the tree contains the key given.
     *
     * @param key to search for.
     * @return true if a value with the given key exists in the tree, false if
     * not.
     */
    @Override
    public boolean contains(int key) {
        if (key < 0) {
            return false;
        }
        LinkedList<Integer> keyDigits = new LinkedList<>();
        while (key > 0) {
            keyDigits.addFirst(key % 10);
            key /= 10;
        }
        while (keyDigits.size() < digitCount) {
            keyDigits.addFirst(0);
        }
        if (keyDigits.size() > digitCount) {
            return false;
        }
        DecimalNode node = root;
        int digitSearch = 0;
        do {
            if (node.children[keyDigits.get(digitSearch)] != null) {
                node = node.children[keyDigits.get(digitSearch)];
            } else {
                return false;
            }
            digitSearch++;
        } while (digitSearch < digitCount);
        return node.value != null;
    }

    /**
     * Reports the value of the key given if it exists.
     *
     * @param key to retrieve value from.
     * @return the value at the given key, or null if it does not exist.
     */
    @Override
    public E get(int key) {
        if (key < 0) {
            return null;
        }
        LinkedList<Integer> keyDigits = new LinkedList<>();
        while (key > 0) {
            keyDigits.addFirst(key % 10);
            key /= 10;
        }
        while (keyDigits.size() < digitCount) {
            keyDigits.addFirst(0);
        }
        DecimalNode node = root;
        int digitSearch = 0;
        do {
            if (node.children[keyDigits.get(digitSearch)] != null) {
                node = node.children[keyDigits.get(digitSearch)];
            } else {
                break;
            }
            digitSearch++;
        } while (digitSearch < digitCount);
        return (E) node.value;
    }

    /**
     * Inserts a value that contains a unique non-negative whole-number key.
     *
     * @param e value to insert into the map.
     * @return true if the value was inserted and false if not.
     */
    @Override
    public boolean insert(E e) {
        int key = e.getKey();
        if (contains(key)) {
            return false;
        }
        LinkedList<Integer> keyDigits = new LinkedList<>();
        while (key > 0) {
            keyDigits.addFirst(key % 10);
            key /= 10;
        }
        while (keyDigits.size() < digitCount) {
            keyDigits.addFirst(0);
        }
        if (keyDigits.size() > digitCount) {
            return false;
        }
        DecimalNode node = root;
        int digitSearch = 0;
        do {
            if (node.children[keyDigits.get(digitSearch)] != null) {
                node = node.children[keyDigits.get(digitSearch)];
            } else {
                node.makeChild(keyDigits.get(digitSearch));
                node = node.children[keyDigits.get(digitSearch)];
            }
            digitSearch++;
        } while (digitSearch < digitCount);
        node.value = e;
        return true;
    }

    /**
     * Removes the value with the given key from the tree.
     *
     * @param key of the value to find and remove.
     * @return true if the value was found and removed and false if not.
     */
    @Override
    public boolean remove(int key) {
        if (get(key) == null) {
            return false;
        }
        LinkedList<Integer> keyDigits = new LinkedList<>();
        while (key > 0) {
            keyDigits.addFirst(key % 10);
            key /= 10;
        }
        while (keyDigits.size() < digitCount) {
            keyDigits.addFirst(0);
        }
        DecimalNode node = root;
        int digitSearch = 0;
        do {
            node = node.children[keyDigits.get(digitSearch)];
            digitSearch++;
        } while (digitSearch < digitCount);
        node.value = null;
        return true;
    }

    /**
     * Reports if the tree is empty or not.
     *
     * @return true if the tree is empty, false if not.
     */
    @Override
    public boolean isEmpty() {
        return isEmpty(root, true);
    }

    /**
     * Private recursive method to determine if the tree is empty.
     *
     * @param node the current node the recursion is on
     * @return false if a value is found in the tree, true if recursion is
     * complete and no values were found
     */
    private boolean isEmpty(DecimalNode node, boolean empty) {
        if (node.value != null) {
            return false;
        } else {
            for (int i = 0; i < 10; i++) {
                if (node.children[i] != null) {
                    empty = empty && isEmpty(node.children[i], empty);
                }
            }
        }
        return empty;
    }

    /**
     * Provides access to a type-specific iterator.
     *
     * @return a new iterator object
     */
    @Override
    public Iterator<E> iterator() {
        return new DecimalIterator();
    }

    /**
     * An inner class that defines a type-specific iterator. Uses a queue
     * internally to manage iterating through the tree.
     */
    private class DecimalIterator implements Iterator<E> {

        //private fields
        private Queue<E> queue;
        private E lastRemoved;

        /**
         * Default constructor.
         */
        public DecimalIterator() {
            fillQueue();
        }

        /**
         * A private method used to fill the queue.
         */
        private void fillQueue() {
            queue = new LinkedList<>();
            fillQueue(root);
        }

        /**
         * A private recursive method to fill the queue with the value of each
         * node in sorted order.
         *
         * @param node the current node in the recursive process
         */
        private void fillQueue(DecimalNode node) {
            if (node.value != null) {
                queue.add((E) node.value);
            } else {
                for (int i = 0; i < 10; i++) {
                    if (node.children[i] != null) {
                        fillQueue(node.children[i]);
                    }
                }
            }
        }

        /**
         * Determines if there is a next value in the map.
         *
         * @return true if there is a next value, false if not
         */
        @Override
        public boolean hasNext() {
            if (queue.isEmpty()) {
                return false;
            }
            return true;
        }

        /**
         * Provides access to the next value in the map, in sorted order.
         *
         * @return the next value
         */
        @Override
        public E next() {
            lastRemoved = queue.poll();
            return lastRemoved;
        }

        /**
         * Removes the last reported value from the map.
         */
        @Override
        public void remove() {
            SortedDecimalMap.this.remove(lastRemoved.getKey());
        }
    }

    /**
     * An inner class that defines the decimal tree node.
     */
    private class DecimalNode<E> {

        /**
         * the value stored in this node - will be null for all nodes except the
         * lowest-level leaf nodes
         */
        public E value;

        /**
         * the array used to store the children of this node
         */
        public DecimalNode[] children;

        /**
         * Default constructor.
         */
        public DecimalNode() {
            children = new DecimalNode[10];
        }

        /**
         * A convenience method to create a new node at the given index of the
         * current node.
         *
         * @param index the index of the children array where the new node
         * should be stored
         */
        public void makeChild(int index) {
            children[index] = new DecimalNode();
        }
    }

}
