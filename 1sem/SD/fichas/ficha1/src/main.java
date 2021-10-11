import java.util.concurrent.locks.ReentrantLock;

class Incrementar extends Thread {
  final int I;
  public Incrementar(int I) {this.I =I;
  }

  public void run() {
   for(int i = 0; i<I; i++)
     System.out.println(i);
  }
}

class Depositador extends Thread {
  final int I;
  final int v;
  final Bank b;

  public Depositador(int i, int v, Bank b) {
    I = i;
    this.v = v;
    this.b = b;
  }
  public void run() {
    for(int i=0; i<I;i++)
     b.deposit(v);
  }
}

// com Lock
class Depositador2 extends Thread {
  final int I;
  final int v;
  final Bank b;
  ReentrantLock r;

  public Depositador2(int i, int v, Bank b) {
    I = i;
    this.v = v;
    this.b = b;
    this.r = new ReentrantLock();
  }
  public void run() {
    r.lock();
    for(int i=0; i<I;i++) {
      b.deposit(v);
    }
    r.unlock();
  }
}

public class main {
  public static void main(String[] s) throws InterruptedException {
    System.out.println("Exemplo");
    exemplo e = new exemplo();
    e.ex();

    System.out.println("Ex 1");
    Ex1 ex1 = new Ex1();
    ex1.ex();

    System.out.println("Ex 2");
    Ex2 ex2 = new Ex2();
    ex2.ex();

    System.out.println("Ex 3");
    Ex3 ex3 = new Ex3();
    ex3.ex();
  }

  public static class Ex1 {
    public void ex() throws InterruptedException {
      int N = 10;
      int I = 100;
      Thread[] t = new Thread[N];

      for(int i=0; i<N;i++){
        t[i] = new Incrementar(I);
      }

      for(int i=0; i<N;i++)
        t[i].start();

      for(int i=0; i<N;i++)
        t[i].join();
    }
  }

  //Nota:
  // Coisas vao se sobrepor
  public static class Ex2 {
    public void ex() throws InterruptedException {
      int N = 10;
      Bank k = new Bank();
      Thread[] t = new Thread[N];

      for(int i=0; i<N;i++){
        t[i] = new Depositador(1000,100, k);
      }

      for(int i=0; i<N;i++)
        t[i].start();

      for(int i=0; i<N;i++)
        t[i].join();

      System.out.println("Money: " + k.balance());
    }
  }

  public static class Ex3 {
    public void ex() throws InterruptedException {
      int N = 10;
      int v = 100;
      Bank k = new Bank();
      Thread[] t = new Thread[N];

      for(int i=0; i<N;i++)
        t[i] = new Depositador2(1000,v, k);

      for(int i=0; i<N;i++)
        t[i].run();

      for(int i=0; i<N;i++)
        t[i].join();

      System.out.println("Money: " + k.balance());
    }
  }

  public static class exemplo {
    public void ex()  throws InterruptedException {
    int N = 10;
    int I = 100;
    Thread[] t = new Thread[N];

    for(int i=0; i<N;i++){
      t[i] = new Incrementar(I);
    }

    for(int i=0; i<N;i++){
      t[i].run();
    }
    }
  }
}
