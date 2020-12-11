package cs143;

public interface DecimalSearchTree<E> extends Iterable<E> {
    
    /**
     * Reports if the tree contains the key given.
     *
     * @param key to search for.
     * @return true if a value with the given key exists in the tree, false if
     * not.
     */
    public boolean contains(int key);
    
    /**
     * Reports the value of the key given if it exists.
     *
     * @param key to retrieve value from.
     * @return the value at the given key, or null if it does not exist.
     */
    public E get(int key);

    /**
     * Inserts a value that contains a unique non-negative whole-number key.
     *
     * @param e value to insert into the map.
     * @return true if the value was inserted and false if not.
     */
    public boolean insert(E e);

    /**
     * Removes the value with the given key from the tree.
     *
     * @param key of the value to find and remove.
     * @return true if the value was found and removed and false if not.
     */
    public boolean remove(int key);

    /**
     * Reports if the tree is empty or not.
     *
     * @return true if the tree is empty, false if not.
     */
    public boolean isEmpty();
    
}
