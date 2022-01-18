import static java.lang.System.out;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface ControloTrafegoAereo {
    static final int NUM = 5;

    int pedirParaDescolar();

    int pedirParaAterrar();

    void descolou(int pista);

    void aterrou(int pista);
}

final class Solution implements ControloTrafegoAereo {
    private final Lock lock;
    private final Condition trackIsFree;
    private final Condition levantarC;
    private final Condition aterrarC;
    private boolean isAr = true;

    private int esperaLevantar = 0;
    private int esperaAterrar = 0;

    private int ticket = 0;
    private int max = 0;
    private final Queue<Integer> freeTracks;

    private void printFreeTracks() {
        out.print("\nfree tracks:\n[ ");
        this.freeTracks.forEach(track -> out.print(track + " "));
        out.println("]\n");
    }

    public Solution() {
        assert ControloTrafegoAereo.NUM > 0;
        this.lock = new ReentrantLock();
        this.trackIsFree = this.lock.newCondition();
        this.levantarC = this.lock.newCondition();
        this.aterrarC = this.lock.newCondition();
        this.freeTracks = new ArrayDeque<>(ControloTrafegoAereo.NUM);
        for (var i = 0; i < ControloTrafegoAereo.NUM; ++i) {
            this.freeTracks.add(i);
        }
    }

    public int pedirParaDescolar() {
        this.lock.lock();
        try {
            // this.printFreeTracks();
            esperaLevantar++;
            int myTicket = ticket++;
            out.println(
                    "Waiting for %s track [Levantar]".formatted(Thread.currentThread().getName())
            );

            while (this.freeTracks.isEmpty() && myTicket > max) {
                if (!this.isAr && esperaAterrar > 0) {
                    try {
                        this.aterrarC.signalAll();
                        this.levantarC.await();
                    } catch (InterruptedException __) {}
                } else {
                    try {
                        this.trackIsFree.await();
                    } catch (final InterruptedException __) {}
                }
            }

            final var track = this.freeTracks.poll();
            isAr = false;
            final var threadName = Thread.currentThread().getName();
            out.println(
                "Plane '%s' acquired track %d [Levantar]".formatted(threadName, track)
            );
            esperaAterrar--;
            return track;
        } finally {
            this.lock.unlock();
        }
    }

    public int pedirParaAterrar() {
        this.lock.lock();
        esperaAterrar++;
        try {
            // this.printFreeTracks();
            int myTicket = ticket++;
            out.println(
                    "Waiting for %s track [Aterrar]".formatted(Thread.currentThread().getName())
            );
            while (this.freeTracks.isEmpty() && myTicket > max) {
                if (this.isAr && esperaLevantar>0) {
                    try {
                        this.levantarC.signalAll();
                        this.aterrarC.await();
                    } catch (InterruptedException __) {}
                } else {
                    try {
                        this.trackIsFree.await();
                    } catch (final InterruptedException __) {}
                }
            }

            isAr = true;
            final var track = this.freeTracks.poll();
            final var threadName = Thread.currentThread().getName();
            out.println(
                    "Plane '%s' acquired track %d [Aterrar]".formatted(threadName, track)
            );
            esperaAterrar--;
            return track;
        } finally {
            this.lock.unlock();
        }
    }

    public void descolou(int pista) {
        this.lock.lock();
        try {
            out.println(
                "Plane '%s' took on from track %d [Descolou]"
                .formatted(Thread.currentThread().getName(), pista)
            );
            this.freeTracks.add(pista);
            max++;
            //this.printFreeTracks();
            this.aterrarC.signalAll();
            this.trackIsFree.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    public void aterrou(int pista) {
        this.lock.lock();
        try {
            out.println(
                    "Plane '%s' took off from track %d [ATERROU]"
                            .formatted(Thread.currentThread().getName(), pista)
            );
            this.freeTracks.add(pista);
            max++;
            //this.printFreeTracks();
            this.levantarC.signalAll();
            this.trackIsFree.signalAll();
        } finally {
            this.lock.unlock();
        }
    }
}

final class Main {
    private static final int NUM_PLANES = 20;
    public static void main(final String... args) {
        final var rng = new Random();
        final ControloTrafegoAereo airTrafficCtrl = new Solution();

        final var planes = new Thread[NUM_PLANES];
        for (int i = 0; i < NUM_PLANES; ++i) {
            int finalI = i;
            planes[i] = new Thread( () -> {
                boolean takeOff = true;
                int track ;
                if ( finalI / 2 == 0)
                    track = airTrafficCtrl.pedirParaDescolar();
                else {
                    track = airTrafficCtrl.pedirParaAterrar();
                    takeOff = false;
                }

                //if (rng.nextInt(0, 2) == 1) {
                    if (takeOff)
                        airTrafficCtrl.descolou(track);
                    else
                        airTrafficCtrl.aterrou(track);
                //}
            });
        }

        out.println(
            "We have %d take-off tracks..."
            .formatted(ControloTrafegoAereo.NUM)
        );

        for (var i = 0; i < NUM_PLANES; ++i) {
            planes[i].start();
        }

        for (var i = 0; i < NUM_PLANES; ++i) {
            try {
                planes[i].join();
            } catch (final InterruptedException e) {}
        }
    }
}