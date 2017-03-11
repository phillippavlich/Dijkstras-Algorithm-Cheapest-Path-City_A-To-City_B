/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: BagImplementation Class
 * Class Description: This is a class that is copied from our algorithms textbook with a few changes in order to implement a bag data type.
 * This data type is used in Dijkstra's algorithm in order to find the shortest path
 *
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BagImplementation<Item> implements Iterable<Item> {
    private Node<Item> first;    
    private int N;               
    
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
   
    public BagImplementation() {
        first = null;
        N = 0;
    }
    /**
     * Returns true if this bag is empty.
     *
     * @return <tt>true</tt> if this bag is empty;
     *         <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }
    /**
     * Returns the number of items in this bag.
     *
     * @return the number of items in this bag
     */
    public int size() {
        return N;
    }
    /**
     * Adds the item to this bag.
     *
     * @param  item the item to add to this bag
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }
    /**
     * Returns an iterator that iterates over the items in this bag in arbitrary order.
     *
     * @return an iterator that iterates over the items in this bag in arbitrary order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        public ListIterator(Node<Item> first) {
            current = first;
        }
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    /**
     * Unit tests the <tt>Bag</tt> data type.
     */
}
