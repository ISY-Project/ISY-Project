package src.ALG.battleshipbot;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedNode<T> implements Iterable<T> {
  private T value;
  private LinkedNode<T> next;

  public LinkedNode(T value) {
    this.value = value;
  }
  
  public T getValue() {
    return value;
  }

  public LinkedNode<T> getNext() {
    return next;
  }

  boolean hasNext() {
    return this.next != null;
  }

  public LinkedNode<T> getTail() {
    LinkedNode<T> tail = this;
    while (tail.hasNext()) tail = tail.next;
    return tail;
  }

  public int getLength() {
    LinkedNode<T> tail = this;
    int count = 1;
    while (tail.hasNext()) {
      tail = tail.next;
      count++;
    }
    return count;
  }

  public void add(LinkedNode<T> node) {
    LinkedNode<T> tail = this;
    while (tail.hasNext()) tail = tail.next;
    tail.next = node;
  }

  public void remove() {
    if (this.hasNext()) this.next = this.next.next;
  }

  public void stitch(LinkedNode<T> node) {
    if (hasNext()) throw new RuntimeException("Can not stitch into the middle of a linked node chain, please use insert instead.");
    this.next = node;
  }

  public void insert(LinkedNode<T> node) {
    node.add(this.next);
    this.next = node;
  }

  public <R> LinkedNode<R> map(Function<T, R> mapper) {
    LinkedNode<R> head = null;
    LinkedNode<R> tail = null;

    for (T item : this) {
      LinkedNode<R> node = new LinkedNode<>(mapper.apply(item));
      if (head == null) head = node;
      if (tail != null) tail.stitch(node);
      tail = node.getTail();
    }

    return head;
  }

  public <R> LinkedNode<R> flatMap(Function<T, LinkedNode<R>> mapper) {
    LinkedNode<R> head = null;
    LinkedNode<R> tail = null;

    for (T item : this) {
      LinkedNode<R> node = mapper.apply(item);
      if (node == null) continue;
      if (head == null) head = node;
      if (tail != null) tail.stitch(node);
      tail = node.getTail();
    }

    return head;
  } 

  public LinkedNode<T> filter(Predicate<T> predicate) {
    LinkedNode<T> head = null;
    LinkedNode<T> tail = null;

    for (T item : this) {
      if (!predicate.test(item)) continue;
      LinkedNode<T> node = new LinkedNode<>(item);
      if (head == null) head = node;
      if (tail != null) tail.stitch(node);
      tail = node.getTail();
    }

    return head;
  }


  @Override
  public Iterator<T> iterator() {
    LinkedNode<T> head = this;
    Iterator<T> iterator = new Iterator<T>() {
      private LinkedNode<T> current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        T value = current.getValue();
        current = current.getNext();
        return value;
      }
    };
    
    return iterator;
  }



  public static <T> LinkedNode<T> fromList(List<T> list) {
    LinkedNode<T> head = null;
    LinkedNode<T> tail = null;
    for (T item : list) {
      LinkedNode<T> node = new LinkedNode<T>(item);
      if (head == null) head = node;
      if (tail != null) tail.stitch(node);
      tail = node.getTail();
    }
    return head;
  }
}