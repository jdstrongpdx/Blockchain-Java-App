package blockchain;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.Attributes;

final class ZeroService {
    int zeros = 0;
    ZeroService(){}

    public synchronized void regulateZeros(int seconds) {
        System.out.println("Block was generating for " + seconds + " seconds");
        // TODO change back to longer variable mine times
        if (zeros == 4) System.out.println("N stays the same");
        else {
            if (seconds > 1) {
                this.zeros--;
                System.out.println("N was decreased to " + zeros);
            } else if (seconds < 1) {
                this.zeros++;
                System.out.println("N was increased to " + zeros);
            }
        }
    }

    public int getZeros() {
        return this.zeros;
    }
}
