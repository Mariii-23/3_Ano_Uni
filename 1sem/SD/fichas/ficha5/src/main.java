//TODO -> Criar testes.... pedir produtos, aumentar produtos ....

class AumentarProdutos implements Runnable{
  Warehouse warehouse;

  public AumentarProdutos(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  @Override
  public void run() {
    int N = 4;
    String[] elems = {"Maca", "Pera" ,"Arroz", "Massa"};
    int[] n = {2,3,1,5};

    for (int i = 0; i<N; i++) {
      warehouse.supply(elems[i],n[i]);
    }
  }
}

public class main {
  public static void main(String[] args) throws InterruptedException{
    final int N = 3;
    Warehouse warehouse = new Warehouse();
    Thread[] t = new Thread[N];

    // for (int i = 0; i < N; i++)
    //  t[i] = new Thread(warehouse);

    t[0] = new Thread(new AumentarProdutos(warehouse));

    for (int i = 0; i < N; i++)
      t[i].start();

    for (int i = 0; i < N; i++)
      t[i].join();
  }
}
