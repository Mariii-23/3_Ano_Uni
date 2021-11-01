import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Barrier {
  private final int N;
  private int counter;
  private final ReentrantLock l;
  private final Condition c;

  Barrier(int N) {
    this.N = N;
    this.counter = 0;
    this.l = new ReentrantLock();
    this.c = l.newCondition();
  }

  void await() throws InterruptedException {
    l.lock();
    try {
      counter++;
        while (counter < N) {
          c.await();
        }
        c.signalAll();
    } finally {
      l.unlock();
    }
  }
}


class Agreement implements Runnable{
  private final int N;
  private int counter;
  private final int[] array;

  private final ReentrantLock l;
  private final Condition c;

  public Agreement(int n) {
    N = n;
    this.counter = 0;
    this.l = new ReentrantLock();
    this.c = l.newCondition();
    this.array = new int[N];
  }

  public int propose(int choice) throws  InterruptedException {
    l.lock();
    try {
      this.array[counter] = choice;
      counter++;
      System.out.println("tou aquiii a espera");
      while (counter < N) {
        c.await();
      }
      c.signalAll();
    } finally {
      l.unlock();
    }
    return Arrays.stream(this.array).max().orElse(0);
  }

  @Override
  public void run() {
    try {
      int random =  new Random().nextInt(10);
      System.out.println(random);
      int max = this.propose(random);
      System.out.println("Maximo: "+max);
    } catch (InterruptedException e) {
      System.out.println("Algo de errado aconteceu");
    }
  }
}

public class main {
  public static void main(String[] args)throws InterruptedException {
    final int N = 10;
    Agreement agreement = new Agreement(N);
    Thread[] t = new Thread[N];

    for (int i = 0; i < N; i++)
      t[i] = new Thread(agreement);

    for (int i = 0; i < N; i++)
      t[i].start();

    for (int i = 0; i < N; i++)
      t[i].join();
  }
}
