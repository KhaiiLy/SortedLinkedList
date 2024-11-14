# SortedLinkedList

A simple implementation of a sorted linked list in Java. This linked list automatically keeps its elements sorted as items are added, using either the natural order (for comparable elements) or a custom comparator.

## Note
This project is still in development. While the core functionality should work as expected, there may be bugs or incomplete features.

## Installation

To use SortedLinkedList in your project, you can either clone this repository or download the source code directly. Add sortedlinkedlist folder to your project.

```bash
git clone https://github.com/yourusername/SortedLinkedList.git
```

## Features
- Automatically sorts elements as they are inserted.
- Supports both ascending and descending order.
- Can be used with any object type that implements Comparable or with a custom Comparator.
- To compare string elemens ignoring case sensitivity use CaseInsensitiveStringComparator class in mentioned folder above.

## Methods
Here are some of implemented methods that can be use with the SortedLinkedList:
- void insert(T value)
- T remove(int index)
- boolean contains(T value)
- T get(int index)
- int size()
- void display()
- void clear()
- boolean isEmpty()


## License

[MIT](https://choosealicense.com/licenses/mit/)
