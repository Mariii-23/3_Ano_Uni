import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

  private static class Account {
    private final Lock l;
    private int balance;

    Account(int balance) {
        this.balance = balance;
        this.l = new ReentrantLock();
    }

    int balance() {
      l.lock();
      try {
        return balance;
      } finally {
        l.unlock();
      }
    }

    boolean deposit(int value) {
      l.lock();
      try {
        balance += value;
      }
      finally {
        l.unlock();
      }
      return true;
    }

    boolean withdraw(int value) {
      l.lock();
      try{
       if (value > balance)
         return false;
       balance -= value;
      } finally {
       l.unlock();
      }
      return true;
    }
  }

  // Bank slots and vector of accounts
  private final int slots; // variavel constante
  private final Account[] av;

  public Bank(int n)
  {
    slots=n;
    av=new Account[slots];
    for (int i=0; i<slots; i++) av[i]=new Account(0);
  }

  // Account balance
  public int balance(int id) {
    if (id < 0 || id >= slots)
      return 0;
    return av[id].balance();
  }

  // Deposit
  boolean deposit(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    return av[id].deposit(value);
  }

  // Withdraw; fails if no such account or insufficient balance
  public boolean withdraw(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    return av[id].withdraw(value);
  }

  public boolean transfer(int from, int to,int value) {
    if ( to<0 || from <0 || to>= slots || from>= slots || value<=0 )
      return false;
    Account cfrom = av[from];
    Account cto = av[to];
    if(from > to){
      cto.l.lock();
      cfrom.l.lock();
    } else {
      cfrom.l.lock();
      cto.l.lock();
    }
    try {
      try {
        if ( !this.withdraw(from, value)) {
          return false;
        }
      } finally {
        cfrom.l.unlock();
      }
      return this.deposit(to, value);
    } finally {
        cto.l.unlock();
    }
  }

  public int totalBalance() {
      int result = 0;
    for (int elem=0; elem < slots; elem++){
        av[elem].l.lock();
        result += this.balance(elem);
    }
    for (int elem=0; elem < slots; elem++){
        av[elem].l.unlock();
    }
    return result;
  }
}
