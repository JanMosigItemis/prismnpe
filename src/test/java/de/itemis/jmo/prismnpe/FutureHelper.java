package de.itemis.jmo.prismnpe;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Static helper class with convenience methods that help keeping test code clean of {@link Future}
 * details.
 */
public final class FutureHelper {

    private FutureHelper() {}

    /**
     * Shutdown the provided {@code executor} and wait some seconds for running tasks to be
     * interrupted. prints a warning if waiting is interrupted. Prints a warning if the executor's
     * tasks have not been shut down in time.
     *
     * @param executor - Shutdown this {@link ExecutorService}.
     */
    public static void kill(ExecutorService executor) {
        executor.shutdownNow();
        boolean notTerminated = false;
        try {
            notTerminated = !executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (notTerminated) {
            System.err.println("Possible resource leak. Executor did not shut down in time.");
        }
    }

    /**
     * Wait for the {@code latch} to become zero.
     *
     * @param latch - Wait for this latch.
     * @param timeout - Wait for as max as this timeout. Precision is milliseconds.
     * @return {@code true} If the count reached zero and {@code false} if the waiting time elapsed
     *         before the count reached zero.
     * @throws RuntimeException In case waiting was interrupted. The original
     *         {@link InterruptedException} will be attached as cause.
     */
    public static boolean waitForLatch(CountDownLatch latch, Duration timeout) {
        try {
            return latch.await(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Was interrupted while waiting on latch.", e);
        }
    }
}
