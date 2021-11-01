import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class Agreement2 implements Runnable{
  private final int N;
  private int[] array;
  private int counter;
  private int[] max;

  private final ReentrantLock l;
  private final Condition c;

  public Agreement2(int n) {
    N = n;
    this.array = new int[N];
    this.counter = 0;
    this.l = new ReentrantLock();
    this.c = l.newCondition();
    this.max = new int[N];
  }

  //ver melhor
  public int propose(int choice) throws  InterruptedException {
    l.lock();
    try {
      // coisas acontecem
      int n_iter = this.array[this.counter]++;
      counter++;
      while (n_iter == Arrays.stream(this.array).min().orElse(-1)) {
        c.await();
      }
      c.signalAll();

      this.array[counter - 1] = choice;
      while (counter < N) {
        c.await();
      }
      c.signalAll();

      // mandar o counter a 0 e por o max com o valor min again
    } finally {
      l.unlock();
    }
    return Arrays.stream(this.max).max().orElse(0);
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

public class experiencia {
  public static void main(String[] args)throws InterruptedException {
    final int N = 3;
    Agreement2 agreement = new Agreement2(N);
    Thread[] t = new Thread[N];

    for (int i = 0; i < N; i++)
      t[i] = new Thread(agreement, Integer.toString(i));

    for (int i = 0; i < N; i++)
      t[i].start();

    for (int i = 0; i < N; i++)
      t[i].join();
  }
}
