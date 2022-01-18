package teste20210721;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ex6 {
    interface ControloVacinas {
        void pedirParaVacinar() throws InterruptedException;
        void fornecerFrascos(int frascos);
    }

    public static class gestaoVacinas implements ControloVacinas {
        private float vacinas = 0;
        private int ticket = 0;
        private int vacinados = 0;
        private int aVacinar = 0;
        private final int NUM;

        private final ReentrantLock l = new ReentrantLock();
        private final Condition c = l.newCondition();

        public gestaoVacinas(int NUM) {
            this.NUM = NUM;
        }

        @Override
        public void fornecerFrascos(int frascos) {
            try {
                l.lock();
                System.out.println(Thread.currentThread().getName() + " -> Forneci vacinas: " + frascos);
                this.vacinas += frascos*NUM;
                c.signalAll();

            } finally {
                l.unlock();
            }
        }

        @Override
        public void pedirParaVacinar() throws InterruptedException {
            try {
                l.lock();
                int ticketPessoa = ticket++;
                aVacinar++;
                System.out.println(Thread.currentThread().getName() + " -> Espera da vacina -> "+ ticketPessoa);
                c.signalAll();
                while (vacinas <= 0 || ( ticketPessoa > (vacinados + NUM))) {
                    c.await();
                }
                System.out.println(Thread.currentThread().getName() + " -> Fui VACINADO.");
                vacinas--;
                aVacinar--;
                vacinados++;
                c.signalAll();

            } finally {
                l.unlock();
            }
        }
    }


    public static class CentroVacinacaoPires implements ControloVacinas {
        private int numVacinas;
        private int numPessoasEspera;
        private int NUM;
        private int ticketAtual;
        private final ReentrantLock l;
        private final Condition pessoaEspera;

        public CentroVacinacaoPires(int NUM) {
            this.NUM = NUM;
            this.numPessoasEspera = 0;
            this.numVacinas = 0;
            this.ticketAtual = 0;
            this.l = new ReentrantLock();
            this.pessoaEspera = l.newCondition();
        }

        @Override
        public void pedirParaVacinar() throws InterruptedException {
            try{
                int ticketAqui = ticketAtual++;
                l.lock();
                numPessoasEspera++;
                System.out.println(Thread.currentThread().getName() + " -> Espera da vacina -> "+ ticketAqui);
                //Porque podem estar à espera porque ainda não há pessoas suficientes.
                pessoaEspera.signalAll();
                while(numVacinas > 0 && numPessoasEspera < NUM && ticketAqui > ticketAtual) {
                    pessoaEspera.await();
                }
                numPessoasEspera--;
                numVacinas = numVacinas - 1 / NUM;
                System.out.println(Thread.currentThread().getName() + " -> Fui VACINADO.");
            }
            finally {
                l.unlock();
            }
        }

        @Override
        public void fornecerFrascos(int frascos){
            try{
                l.lock();
                System.out.println(Thread.currentThread().getName() + " -> Forneci vacinas: " + frascos);
                numVacinas+=frascos;
                pessoaEspera.signalAll();
            }
            finally {
                l.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ControloVacinas gestaoVacinas = new gestaoVacinas(2);
        //ControloVacinas gestaoVacinas = new CentroVacinacaoPires(10);
        int pedidos = 9;
        int frascos = 1;
        int entregas = 2;
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
                    t[i] = new Thread(() -> gestaoVacinas.fornecerFrascos(frascos));
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
                t[i+N] = new Thread(() -> gestaoVacinas.fornecerFrascos(pedidos*2));
            } else {
                t[i+N] = new Thread(() -> gestaoVacinas.fornecerFrascos(frascos));
            }
        }

        t[N*2] = new Thread(() -> {
            try {
                gestaoVacinas.pedirParaVacinar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < 2*N; i++)
            t[i].start();
        t[N*2].start();
        for (int i = 0; i < 2*N; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
