import java.util.Random;

class Config{
   public int N_ACCONTS = 20;
   public int N_TRANSFERENCIAS = 1000;
   public int N_ACCONTS_DELETED = new Random().nextInt(N_ACCONTS);

   public int MONEY_INICIAL   = 1000;
   public int MONEY_ADICIONAR = 9000;
   public int MONEY_TOTAL     = (MONEY_ADICIONAR + MONEY_INICIAL) * N_ACCONTS;
   public int MONEY_TRANDERENCIA = 100;

   public boolean PRINTS_WATCHER = true;
   public boolean PRINTS_TRANSFER = true;
   public boolean PRINTS_DEPOSIT = false;
   public boolean PRINTS_DELETE = true;
}

class Watcher implements  Runnable {
  Bank b;
  int N;
  Config c;

  public Watcher(Bank b, int n, Config c) {
    this.b = b;
    N = n;
    this.c = c;
  }

  @Override
  public void run() {
    for (int i=0; i< 1000; i++) {
      System.out.println("\n");
      for (int j =0; j< N; j++){
        if (c.PRINTS_WATCHER)
          System.out.println("Conta "+j+": "+b.balance(j));
      }
      System.out.println("\n");
    }
  }
}

class Transfer implements Runnable {
  Bank b;
  int N;
  Config c;
  public Transfer(Bank b, int N, Config c) {
    this.b = b;
    this.N = N;
    this.c = c;
  }

  @Override
  public void run() {
    for (int i=0; i< c.N_TRANSFERENCIAS; i++) {
      int[] a = new int[2];
      a[0] = new Random().nextInt(N);
      a[1] = new Random().nextInt(N);
      while (a[0]==a[1])
        a[1] = new Random().nextInt(N);
      b.transfer(a[0],a[1], c.MONEY_TRANDERENCIA);
      if(c.PRINTS_TRANSFER)
        System.out.println("TRANSFER " + a[0] + " -> " + a[1]);
    }
  }
}

class Delete implements Runnable {
  Bank b;
  int N;
  Config c;

  public Delete(Bank b, int N, Config c) {
    this.b = b;
    this.N = N;
    this.c = c;
  }
  @Override
  public void run() {
    for (int i=0; i < c.N_ACCONTS_DELETED; i++) {
      int eli = new Random().nextInt(c.N_ACCONTS) ;
      int money;
      // eliminar uma conta
      while ((money = b.closeAccount(eli)) ==0 ){
        eli = new Random().nextInt(c.N_ACCONTS) ;
      }
      // transferir o dinheiro da conta eliminada para outra
      int to = new Random().nextInt(c.N_ACCONTS);
      while (!b.deposit(to,money))
        to = new Random().nextInt(c.N_ACCONTS);
      if (c.PRINTS_DELETE)
        System.out.println("DELETE: " + eli + " Money to: " + to);
    }
  }
}

class Deposit implements Runnable {
  Bank b;
  int N;
  Config c;

  public Deposit(Bank b, int N, Config c) {
    this.b = b;
    this.N = N;
    this.c = c;
  }
  @Override
  public void run() {
    for (int j=0; j < c.MONEY_ADICIONAR; j++){
      for (int i=0; i < N ; i++) {
        int  elem = new Random().nextInt(N);
        while (!b.deposit(elem,1))
          elem = new Random().nextInt(N);
        if (c.PRINTS_DEPOSIT)
          System.out.println("DEPOSIT 1$ -> " + elem);
      }
    }
  }
}

class bank_teste {
  public static void main(String[] args) throws InterruptedException {
    int N = new Config().N_ACCONTS;
    Bank b = new Bank();
    Config c = new Config();

    for (int i=0; i<N; i++)
      b.createAccount(new Config().MONEY_INICIAL);

    Thread t0 = new Thread(new Watcher(b,N,c));
    Thread t1 = new Thread(new Delete(b,N,c));
    Thread t2 = new Thread(new Deposit(b,N,c));
    Thread t3 = new Thread(new Transfer(b,N,c));

    t0.start(); t1.start(); t2.start(); t3.start();
    t0.join(); t1.join(); t2.join(); t3.join();

    System.out.println("\n\nIn the end");
    for (int j =0; j< N; j++)
      System.out.println("Conta "+j+": "+b.balance(j));

    int total =0;
    for(int i=0; i< N; i++){
      total+=b.balance(i);
    }

    int n_acconts = b.get_number_acconts();
    int n_acconts_delete = c.N_ACCONTS - b.get_number_acconts();
    boolean fail = (n_acconts_delete == c.N_ACCONTS_DELETED) &&
        total == c.MONEY_TOTAL;
    System.out.println("\nCONFIG | Reality");
    System.out.println("Contas iniciais: \t" + c.N_ACCONTS);
    System.out.println("Contas eliminadas: \t" + c.N_ACCONTS_DELETED + " | " + n_acconts_delete);
    System.out.println("Contas ativas: \t\t" + (c.N_ACCONTS - c.N_ACCONTS_DELETED) + " | " + n_acconts);
    System.out.println("TOTAL: \t"+ total + " | " + c.MONEY_TOTAL);
    System.out.println("\nIs all rigth? -> \t" + fail );
  }
}
