import java.util.Random;

class Watcher implements Runnable{
  Bank b;
  int total;

  public Watcher(Bank b, int total) {
    this.b = b;
    this.total = total;
  }

  @Override
  public void run() {
    int i=0 ;
    int N = 100;

    //while(true) {
    while(i < N){
      System.out.println(b.totalBalance());
      i++;
    }
  }
}

class Mover implements Runnable {
  Bank b;
  int s; // Number of accounts

  public Mover(Bank b, int s) { this.b=b; this.s=s; }

  public void run() {
    final int moves=100000;
    int from, to;
    Random rand = new Random();

    for (int m=0; m<moves; m++)
    {
      from=rand.nextInt(s); // Get one
      while ((to=rand.nextInt(s))==from); // Slow way to get distinct
      b.transfer(from,to,1);
    }
  }
}

class BankTest {
  public static void main(String[] args) throws InterruptedException {
    final int N=10;

    Bank b = new Bank(N);

    for (int i=0; i<N; i++) 
      b.deposit(i,1000);

    System.out.println("No inicio: " + b.totalBalance());

    Thread t1 = new Thread(new Mover(b,10)); 
    Thread t2 = new Thread(new Mover(b,10));
    Thread t3 = new Thread(new Watcher(b,10));
    t3.start();
    t1.start(); t2.start(); t1.join(); t2.join();
    t3.join();

    System.out.println("Transferencias feitas.");
    System.out.println("No fim: "+b.totalBalance());
  }
}
