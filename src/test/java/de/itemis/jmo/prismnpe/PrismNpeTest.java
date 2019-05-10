package de.itemis.jmo.prismnpe;

import static de.itemis.jmo.prismnpe.FutureHelper.kill;
import static de.itemis.jmo.prismnpe.FutureHelper.waitForLatch;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Strings;

import org.assertj.core.description.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;

@RunWith(JUnitPlatform.class)
public class PrismNpeTest {

    private ExecutorService executor;

    @BeforeEach
    public void setUp() {
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    public void tearDown() {
        Platform.exit();
        kill(executor);
    }

    @Test
    public void testSomething() {
        var latch = new CountDownLatch(1);
        var thrownExceptionRef = new AtomicReference<Exception>();

        executor.submit(() -> {
            try {
                var launcher = new ApplicationLauncherImpl();
                launcher.launch(PrismNpeApp.class, new String[0]);
            } catch (Exception e) {
                thrownExceptionRef.set(e);
                latch.countDown();
            }
        });

        assertThat(waitForLatch(latch, Duration.ofSeconds(5))).as(new BootstrapDescr(thrownExceptionRef)).isFalse();
    }

    private static final class BootstrapDescr extends Description {
        private final AtomicReference<Exception> thrownExceptionRef;

        public BootstrapDescr(AtomicReference<Exception> thrownExceptionRef) {
            this.thrownExceptionRef = thrownExceptionRef;
        }

        @Override
        public String value() {
            Exception thrownException = thrownExceptionRef.get();
            String details = "No further details.";
            if (thrownException != null) {
                String causeMsg = thrownException.getMessage();
                if (Strings.isNullOrEmpty(causeMsg)) {
                    causeMsg = details;
                }
                details = thrownException.getClass().getSimpleName() + ": " + causeMsg;
            }

            return "Bootstrapping PrismNpe raised an error: " + details;
        }
    }
}
