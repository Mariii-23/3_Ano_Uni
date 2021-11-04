import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
  private final Map<String, Product> map;
  private final Lock l;
  Condition c ;

  private class Product {
    int quantity = 0;
    Lock l = new ReentrantLock();
    Condition c = l.newCondition();
  }

  public Warehouse() {
    this.map = new HashMap<String, Product>();
    this.l = new ReentrantLock();
    this.c = l.newCondition();
  }

  private Product get(String item) {
    Product p;
    l.lock();
    try {
      p = map.get(item);
      if (p != null) return p;

      p = new Product();
      map.put(item, p);
      return p;
    } finally {
      l.unlock();
    }
  }

  public void supply(String item, int quantity) {
    Product p;
    l.lock();
    try {
      p = get(item);
      if ( p==null )
        return;
      p.l.lock();
    } finally {
      l.unlock();
    }
    try {
      p.quantity += quantity;
      p.c.signalAll();
    } finally {
      p.l.unlock();
    }
  }

  public void consume(Set<String> items) throws InterruptedException {
    List<Product> list = new LinkedList<>();
    l.lock();

    try {
      for (String s : items){
        Product p = get(s);
        if (s.isEmpty())
          continue;
        list.add(p);
      }
    } finally {
      l.unlock();
    }

    for (Product p :list) {
      p.l.lock();
      try {
        while (p.quantity<=0)
          p.c.await();
        p.quantity--;
        p.c.signalAll();
      } finally {
        p.l.unlock();
      }
    }
  }
}
