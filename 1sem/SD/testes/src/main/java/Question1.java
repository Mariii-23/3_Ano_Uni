import static java.lang.System.out;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface ControloTrafegoAereo {
    static final int NUM = 10;

    int pedirParaDescolar();

    int pedirParaAterrar();

    void descolou(int pista);

    void aterrou(int pista);
}

final class Solution implements ControloTrafegoAereo {
    private final Lock lock;
    private final Condition trackIsFree;
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
        this.freeTracks = new ArrayDeque<>(ControloTrafegoAereo.NUM);
        for (var i = 0; i < ControloTrafegoAereo.NUM; ++i) {
            this.freeTracks.add(i);
        }
    }

    public int pedirParaDescolar() {
        this.lock.lock();
        try {
            this.printFreeTracks();
            while (this.freeTracks.isEmpty()) {
                try {
                    this.trackIsFree.await();
                } catch (final InterruptedException __) {}
            }
            final var track = this.freeTracks.poll();
            final var threadName = Thread.currentThread().getName();
            out.println(
                "Plane '%s' acquired track %d".formatted(threadName, track)
            );
            return track;
        } finally {
            this.lock.unlock();
        }
    }

    public int pedirParaAterrar() {
        return pedirParaDescolar();
    }

    public void descolou(int pista) {
        this.lock.lock();
        try {
            out.println(
                "Plane '%s' took off from track %d"
                .formatted(Thread.currentThread().getName(), pista)
            );
            this.freeTracks.add(pista);
            this.printFreeTracks();
            this.trackIsFree.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public void aterrou(int pista) {
        descolou(pista);
    }
}

final class Main {
    private static final int NUM_PLANES = 20;
    public static void main(final String... args) {
        final var rng = new Random();
        final var airTrafficCtrl = new Solution();

        final var planes = new Thread[NUM_PLANES];
        for (var i = 0; i < NUM_PLANES; ++i) {
            planes[i] = new Thread(() -> {
                final var track = airTrafficCtrl.pedirParaDescolar();
                if (rng.nextInt(0, 2) == 1) {
                    airTrafficCtrl.descolou(track);
                }
            });
        }

        out.println(
            "Asking for %d take-off tracks..."
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