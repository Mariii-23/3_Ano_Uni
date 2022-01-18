package teste20210721;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ex6 {
    interface ControloVacinas {
        void pedirParaVacinar() throws InterruptedException;
        void fornecerFrascos(int frascos);
    }

    public static class gestaoVacinas implements ControloVacinas {
        boolean acabar = false;
        private int vacinas = 0;
        private Queue<Condition> pedidos;

        private final ReentrantLock l = new ReentrantLock();
        private final Condition c = l.newCondition();

        public gestaoVacinas() {
          pedidos = new ArrayDeque<>();
        }

        @Override
        public void fornecerFrascos(int frascos) {
            try {
                l.lock();
                System.out.println(Thread.currentThread().getName() + " -> Forneci vacinas: " + frascos);
                this.vacinas += frascos;

                while (!pedidos.isEmpty() && vacinas > 0) {
                    pedidos.remove().signalAll();
                    vacinas--;
                }

            } finally {
                l.unlock();
            }
        }

        @Override
        public void pedirParaVacinar() throws InterruptedException {
            try {
                l.lock();
                System.out.println(Thread.currentThread().getName() + " -> Espera da vacina");

                if (pedidos.isEmpty() && vacinas>0) {
                    return;
                }

                Condition aux = l.newCondition();
                pedidos.add(aux);
                //c.signal();
                aux.await();

                System.out.println(Thread.currentThread().getName() + " -> Fui VACINADO.");
            } finally {
                l.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ControloVacinas gestaoVacinas = new gestaoVacinas();
        int pedidos = 20;
        int frascos = 2;
        int entregas = 10;
        int N = pedidos+entregas;

        Thread[] t = new Thread[N*2+1];
        for (int i = 0 ; i < N; i++) {
                if (i < pedidos) {
                    t[i] = new Thread(() -> {
                        try {
                            gestaoVacinas.pedirParaVacinar();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    t[i] = new Thread(() -> {
                        gestaoVacinas.fornecerFrascos(frascos);
                    });
                }
        }
        for (int i = 0 ; i < N ; i++) {
            if (i < pedidos) {
                t[i+N] = new Thread(() -> {
                    try {
                        gestaoVacinas.pedirParaVacinar();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } else if (i == N-1) {
                t[i+N] = new Thread(() -> {
                    gestaoVacinas.fornecerFrascos(frascos);
                });
            } else {
                t[i+N] = new Thread(() -> {
                    gestaoVacinas.fornecerFrascos(frascos);
                });
            }
        }

        t[N*2] = new Thread(() -> {
            gestaoVacinas.fornecerFrascos(pedidos*2);
        });

        for (int i = 0; i < 2*N; i++)
            t[i].start();
        for (int i = 0; i < 2*N; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
