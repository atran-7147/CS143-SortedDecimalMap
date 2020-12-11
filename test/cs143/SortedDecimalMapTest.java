package cs143;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests SortedDecimalMap.java
 *
 * @author Jonathan Fuller
 * @author Leiana Omine
 * @author Tyler Rose
 * @author Andy Tran
 */
public class SortedDecimalMapTest {

    //fields
    Product[] products = new Product[1000];
    SortedDecimalMap map = new SortedDecimalMap(5);
    SortedDecimalMap map2 = new SortedDecimalMap(5);

    /**
     * Creates a number products to be inserted into maps. Also creates a
     * partially filled map.
     */
    @Before
    public void setUp() {
        for (int i = 0; i < 1000; i++) {
            products[i] = new Product(i * 10);
        }
        for (int i = 0; i < 1000; i++) {
            map2.insert(products[i]);
        }
    }

    /**
     * Test of contains method, of class SortedDecimalMap.
     */
    @Test
    public void testContainsEmpty() {
        assertFalse(map.contains(0));
        assertFalse(map.contains(1));
    }

    @Test
    public void testContainsOutOfRange() {
        assertFalse(map2.contains(-1));
        assertFalse(map2.contains(1000000));
    }

    @Test
    public void testContainsOne() {
        map.insert(products[1]);
        assertFalse(map.contains(1));
        assertTrue(map.contains(10));
        assertFalse(map.contains(100));
    }

    @Test
    public void testContainsFilled() {
        for (int i = 0; i < 1000; i++) {
            map.insert(products[i]);
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue(map.contains(i * 10));
        }
        assertFalse(map.contains(5));
        assertFalse(map.contains(99999));
    }

    /**
     * Test of get method, of class SortedDecimalMap.
     */
    @Test
    public void testGetFilled() {
        for (int i = 0; i < 1000; i++) {
            assertSame(map2.get(i * 10), products[i]);
        }
    }

    @Test
    public void testGetEmpty() {
        assertNull(map.get(10));
    }

    @Test
    public void testGetNonexistant() {
        assertNull(map2.get(1005));
    }

    @Test
    public void testGetOutOfRange() {
        assertNull(map2.get(-1));
        assertNull(map2.get(1000000));
    }

    /**
     * Test of insert method, of class SortedDecimalMap.
     */
    @Test
    public void testInsertOne() {
        map.insert(products[27]);
        assertSame(map.get(270), products[27]);
        assertTrue(map.contains(270));
    }

    @Test
    public void testInsertMany() {
        for (int i = 0; i < 1000; i++) {
            assertSame(map2.get(i * 10), products[i]);
            assertTrue(map2.contains(i * 10));
        }
    }

    @Test
    public void testInsertDuplicate() {
        assertTrue(map2.insert(new Product(15)));
        assertFalse(map2.insert(new Product(10)));
        assertEquals(map2.get(15), new Product(15));
        assertSame(map2.get(10), products[1]);
    }

    @Test
    public void testInsertOutOfRange() {
        assertFalse(map2.insert(new Product(123456)));
        assertTrue(map2.insert(new Product(12345)));
        assertFalse(map2.contains(123456));
        assertEquals(map2.get(12345), new Product(12345));
    }

    /**
     * Test of remove method, of class SortedDecimalMap.
     */
    @Test
    public void testRemoveOne() {
        map.insert(products[27]);
        assertTrue(map.remove(270));
        assertFalse(map.contains(270));
        assertTrue(map2.remove(270));
        assertFalse(map2.contains(270));
    }

    @Test
    public void testRemoveEmpty() {
        assertFalse(map.remove(270));
        assertFalse(map.contains(270));
    }

    @Test
    public void testRemoveNonexistant() {
        assertFalse(map.remove(275));
        assertFalse(map.contains(275));
    }

    @Test
    public void testRemoveFilled() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(map2.remove(i * 10));
            assertFalse(map2.contains(i * 10));
        }
    }

    @Test
    public void testRemoveDuplicate() {
        assertTrue(map2.remove(10));
        assertFalse(map2.contains(10));
        assertFalse(map2.remove(10));
        assertFalse(map2.contains(10));
    }

    @Test
    public void testRemoveOutOfRange() {
        assertFalse(map2.remove(-275));
        assertFalse(map2.remove(275000));
        assertFalse(map2.contains(-275));
        assertFalse(map2.contains(275000));
    }

    /**
     * Test of isEmpty method, of class SortedDecimalMap.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(map.isEmpty());
        assertFalse(map2.isEmpty());
    }

    @Test
    public void testIsEmptyRemoved() {
        assertTrue(map.isEmpty());
        map.insert(new Product(150));
        map.remove(150);
        assertTrue(map.isEmpty());
    }

    /**
     * Test of iterator method, of class SortedDecimalMap.
     */
    @Test
    public void testIterator() {
        Iterator<Product> itr = map2.iterator();
        int index = 0;
        for (Object o : map2) {
            assertEquals(o, products[index++]);
        }
    }

    @Test
    public void testIteratorRemove() {
        Iterator<Product> itr = map2.iterator();
        while (itr.hasNext()) {
            Product p = itr.next();
            if (p.equals(products[15])) {
                itr.remove();
            }
        }
        for (int i = 0; i < 15; i++) {
            assertEquals(map2.get(i * 10), products[i]);
        }
        assertNull(map2.get(150));
        for (int i = 16; i < 1000; i++) {
            assertEquals(map2.get(i * 10), products[i]);
        }
    }

    @Test
    public void testIteratorNull() {
        Iterator<Product> itr = map.iterator();
        assertFalse(itr.hasNext());
    }

}
