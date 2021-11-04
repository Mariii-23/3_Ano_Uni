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


// esta versao so funciona quando é chamada apenas 1 vez
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

// Código reutilizavel
class AgreementV2 implements Runnable{
  private final ReentrantLock l ;
  private final Condition c;

  private final int N;
  private final int Run;
  private int counter;
  private final int[] array;
  private int stage;

  //public static class Max {
  //   int max = Integer.MIN_VALUE;
  //}
  //
  //private Max max;


  public AgreementV2(int n) {
    N = n;
    Run = 1;
    this.counter = 0;
    this.stage = 0;
    this.l = new ReentrantLock();
    this.c = l.newCondition();
    this.array = new int[N];
    //this.max =  new Max();
  }

  public AgreementV2(int n, int run) {
    N = n;
    Run = run;
    this.counter = 0;
    this.stage = 0;
    this.l = new ReentrantLock();
    this.c = l.newCondition();
    this.array = new int[N];
  }

  public int propose(int choice) throws  InterruptedException {
    l.lock();
    //System.out.println("entrei");
    try {
      //System.out.println("fazer coisas");
      int stage = this.stage;
      this.array[counter] = choice;
      counter++;
      if (counter < N) {
        while (stage == this.stage) {
          c.await();
        }
      } else {
        this.stage++;
        c.signalAll();
        counter = 0;
      }
      c.signalAll();
    } finally {
      l.unlock();
    }
    //System.out.println("sai");
    return Arrays.stream(this.array).max().orElse(0);
  }

  @Override
  public void run() {
    try {
      for (int i=0; i< this.Run; i++){
        int random =  new Random().nextInt(10);
        System.out.println(random);
        int max = this.propose(random);
        System.out.println("Maximo: "+max);
      }
    } catch (InterruptedException e) {
      System.out.println("Algo de errado aconteceu");
    }
  }
}

public class main {
  public static void main(String[] args)throws InterruptedException {
    final int N = 3;
    final int Run = 2;
    Agreement agreement = new Agreement(N);
    AgreementV2 agreement2 = new AgreementV2(N,Run);
    Thread[] t = new Thread[N];

    System.out.println("Versao 1");
    for (int i = 0; i < N; i++)
      t[i] = new Thread(agreement);

    for (int i = 0; i < N; i++)
      t[i].start();

    for (int i = 0; i < N; i++)
      t[i].join();

    System.out.println("\n\nVersao 2");
    for (int i = 0; i < N; i++)
      t[i] = new Thread(agreement2);

    for (int i = 0; i < N; i++)
      t[i].start();

    for (int i = 0; i < N; i++)
      t[i].join();
  }
}
