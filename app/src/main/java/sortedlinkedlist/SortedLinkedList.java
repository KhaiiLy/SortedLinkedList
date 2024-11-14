package sortedlinkedlist;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SortedLinkedList<T extends Comparable<T>> {

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private Comparator<T> comparator = Comparator.naturalOrder();

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Construct an empty list
     */
    public SortedLinkedList() {
        initilizeProps();
    }

    /**
     * Construct an empty list and set comparing method.
     *
     * To compare string case-insensitive use
     * CaseInsensitiveStringComparator custom class
     * 
     * @param c the comparator method that determines the order of a list
     */
    public SortedLinkedList(Comparator<T> c) {
        initilizeProps();
        this.comparator = c;
    }

    /**
     * Construct sorted linked list with given collection
     * 
     * @param collection the collection whose elements are to be placed into this
     *                   list
     */
    public SortedLinkedList(Collection<T> collection) {
        initilizeProps();
        buildLinkedList(collection);
        head = MergeSort(head);
    }

    /**
     * Construct sorted linked list with given collection and comparator method
     * .naturalOrder(), .reverseOrder(), etc.
     * 
     * @param collection the collection whose elements are to be placed into this
     *                   list
     * @param c          the comparator method that determines the order of a list
     */
    public SortedLinkedList(Collection<T> collection, Comparator<T> c) {
        initilizeProps();
        this.comparator = c;
        buildLinkedList(collection);
        head = MergeSort(head);
    }

    /**
     * Initilize the SortedLinkedList properties
     */
    private void initilizeProps() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Inserts value into linked list and keeps it in sorted order specified by
     * comparator method. Default order is ascending.
     *
     * @param val the value to be inserted into the list
     */
    public void insert(T val) {
        Node<T> h = head;
        Node<T> t = tail;
        Node<T> newNode = new Node<T>(null, val, null);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            /*
             * compare inserted value with head and tail
             * (lowest and highest value in list), else insert in middle
             */
            if (comparator.compare(h.value, val) >= 0)
                insertFirst(newNode);
            else if (comparator.compare(t.value, val) <= 0)
                insertLast(newNode);
            else
                insertMiddle(newNode);
        }
        size++;
    }

    /**
     * Inserts value from the beginning of a list
     * 
     * @param newNode the node to be inserted into a list
     */
    private void insertFirst(Node<T> newNode) {
        Node<T> h = head;
        newNode.next = h;
        head = newNode;
        h.prev = head;
    }

    /**
     * Inserts value at the end of a list
     * 
     * @param newNode the node to be inserted into a list
     */
    private void insertLast(Node<T> newNode) {
        Node<T> t = tail;
        newNode.prev = t;
        tail = newNode;
        t.next = tail;
    }

    /**
     * Inserts value into right position somewhere in the middle of a list
     * 
     * @param newNode the node to be inserted into a list
     */
    private void insertMiddle(Node<T> newNode) {
        Node<T> curr = head.next;
        while (comparator.compare(curr.value, newNode.value) < 0) {
            curr = curr.next;
        }
        newNode.next = curr;
        newNode.prev = curr.prev;
        curr.prev.next = newNode;
        curr.prev = newNode;
    }

    /**
     * Removes first/head node from a list
     * 
     * @return the removed node.value
     */
    public T removeFirst() {
        Node<T> h = head;
        if (h == null)
            throw new NoSuchElementException();
        return unlinkFirst(h);
    }

    /**
     * Removes last/tail node form a list
     * 
     * @return the removed node.value
     */
    public T removeLast() {
        Node<T> t = tail;
        if (t == null)
            throw new NoSuchElementException();
        return unlinkLast(t);
    }

    /**
     * Removes the value (node) by the index postion
     * 
     * @param index the positon of the value from the beginning of a list
     * @return true if the remove was successful
     */
    public T remove(int index) {
        Node<T> x = findNode(index);
        return unlink(x);
    }

    /**
     * Removes the first found corresponding value (node) in a list.
     * Iterates from a beginning of a list (head).
     * 
     * @param value the value to be removed
     * @return the removed value
     */
    public boolean remove(T value) {
        Node<T> curr = head;
        if (size == 0)
            throw new IllegalStateException("Cannot remove from empty list.");
        else {
            if (value != null) {
                while (curr != null && curr.value != value)
                    curr = curr.next;
                if (curr == null)
                    throw new NoSuchElementException("No such element was found in the list.");
                else
                    return unlink(curr) != null;
            }
        }
        return false;
    }

    /**
     * Unlinks and removes the node from a list
     * 
     * @param node the node to be removed
     * @return the value of removed node
     */
    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        T val = node.value;
        if (prev == null && next == null) { // remove single element in list
            head = null;
            tail = null;
        } else if (prev == null)
            return unlinkFirst(node);
        else if (next == null)
            return unlinkLast(node);
        else {
            prev.next = next;
            next.prev = prev;
        }
        node = null;
        size--;
        return val;
    }

    /**
     * Unlinks the first/head node from a list and replaces it
     * 
     * @param h the node to be removed/replaced
     * @return the value of removed/replaced node
     */
    private T unlinkFirst(Node<T> h) {
        T val = h.value;
        Node<T> next = h.next;
        h.value = null;
        h.next = null;
        head = next;
        if (next == null)
            tail = null;
        else
            next.prev = null;
        size--;
        return val;
    }

    /**
     * Unlinks the last/tail node from a list and replaces it.
     * 
     * @param t the node to be removed/replaced
     * @return the value of removed/replaced node
     */
    private T unlinkLast(Node<T> t) {
        T value = t.value;
        Node<T> prev = t.prev;
        t.value = null;
        t.prev = null;
        tail = prev;
        if (prev == null)
            head = null;
        else
            prev.next = null;
        size--;
        return value;
    }

    public boolean contains(T val) {
        Node<T> x = head;
        while (x != null && comparator.compare(x.value, val) != 0)
            x = x.next;
        if (x == null)
            return false;
        return true;
    }

    /**
     * Traverse the list from the head or the tail
     * depending on the comparator method (asc, desc order).
     * 
     * Negative and out of bounds idexes not handled yet
     * negative idx returns lowest value = head.value
     * index out of bounds returns highest value = tail.value
     * 
     * @param index the corresponding node position in a list
     */
    private Node<T> findNode(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    /**
     * Gets the value at the corresponding position.
     * 
     * @param index the value at the specified index
     * @return
     */
    public T get(int index) {
        return findNode(index).value;
    }

    /**
     * Returns first/head value from a list.
     * 
     * @return the returned value
     */
    public T getFirst() {
        return head.value;
    }

    /**
     * Returns last/tail value from a list.
     * 
     * @return he returned value
     */
    public T getLast() {
        return tail.value;
    }

    /**
     * Returns the lenght of a list.
     * 
     * @return the number of elements in a list
     */
    public int size() {
        return size;
    }

    /**
     * Prints out all elemetns from a list.
     */
    public void display() {
        Node<T> node = head;
        if (isEmpty())
            // throw new IllegalStateException("List is empty.");
            System.out.println("List is empty.");
        else {
            while (node != null) {
                System.out.print(node.value);
                node = node.next;
                if (node != null)
                    System.out.print(" <=> ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Clears out the list. Sets all Node props to null.
     */
    public void clear() {
        Node<T> x = head;
        while (x != null) {
            Node<T> next = x.next;
            x.value = null;
            x.prev = null;
            x.next = null;
            x = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Checks if the list contains any elements.
     * 
     * @return true if the list does not contain any element
     */
    public boolean isEmpty() {
        if (size == 0 && head == null && tail == null)
            return true;
        return false;
    }

    /**
     * Creates linked list from a given collection.
     * 
     * @param collection the values to create link. list from
     */
    private void buildLinkedList(Collection<T> collection) {
        for (T val : collection) {
            Node<T> newNode = new Node<>(null, val, null);
            if (head == null)
                head = tail = newNode;
            else
                insertLast(newNode);
            size++;
        }
    }

    /**
     * Splits the list into two halfs. Receives the starting
     * point through parameter and traverses to the end of list/sublist.
     * 
     * @param x the starting node in a list/sublist
     * @return the middle node that splits the list
     */
    private Node<T> split(Node<T> x) {
        Node<T> fast = x;
        Node<T> slow = x;
        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        Node<T> temp = slow.next;
        slow.next = null;
        if (temp != null)
            temp.prev = null;
        return temp;
    }

    /**
     * Sorts the linked list with merge sort algorithm.
     * 
     * @param h the node from where to run the algorithm
     * @return each sorted sublist until the whole list is sorted
     */
    private Node<T> MergeSort(Node<T> h) {
        if (h == null || h.next == null) {
            return h;
        }
        Node<T> middle = split(h);
        h = MergeSort(h);
        middle = MergeSort(middle);

        return merge(h, middle);
    }

    /**
     * Sorts the splitted lists and merges them together.
     * Compares each node with recursion, each recursion links returned node
     * to the sorted list (last linked sorted node -> returned node).
     * 
     * @param n1 head node of a first sublist
     * @param n2 head node of a second sublist
     * @return head node of a merged sublists
     */
    private Node<T> merge(Node<T> n1, Node<T> n2) {
        if (n1 == null)
            return n2;
        if (n2 == null)
            return n1;
        if (comparator.compare(n1.value, n2.value) < 0) {
            n1.next = merge(n1.next, n2);
            if (n1.next != null)
                n1.next.prev = n1;
            n1.prev = null;
            return n1;
        } else {
            n2.next = merge(n1, n2.next);
            if (n2.next != null)
                n2.next.prev = n2;
            n2.prev = null;
            return n2;
        }
    }
}
