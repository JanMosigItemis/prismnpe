package de.itemis.jmo.prismnpe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import javafx.application.Application;
import javafx.application.Platform;

/**
 * Adapted copy of org.testfx.toolkit.impl.ApplicationLauncherImpl
 */
public class ApplicationLauncherImpl {

    public void launch(Class<? extends Application> appClass, String... appArgs) {
        initMonocleHeadless();
        Platform.setImplicitExit(false);
        Application.launch(appClass, appArgs);
    }

    private void initMonocleHeadless() {
        try {
            assignMonoclePlatform();
            assignHeadlessPlatform();
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("monocle headless platform not found - did you forget to add " +
                "a dependency on monocle (https://github.com/TestFX/Monocle)?", exception);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void assignMonoclePlatform() throws Exception {
        Class<?> platformFactoryClass = Class.forName("com.sun.glass.ui.PlatformFactory");
        Object platformFactoryImpl = Class.forName("com.sun.glass.ui.monocle.MonoclePlatformFactory")
            .getDeclaredConstructor().newInstance();
        assignPrivateStaticField(platformFactoryClass, "instance", platformFactoryImpl);
    }

    private void assignHeadlessPlatform() throws Exception {
        Class<?> nativePlatformFactoryClass = Class.forName("com.sun.glass.ui.monocle.NativePlatformFactory");
        try {
            Constructor<?> nativePlatformCtor = Class.forName(
                "com.sun.glass.ui.monocle.HeadlessPlatform").getDeclaredConstructor();
            nativePlatformCtor.setAccessible(true);
            assignPrivateStaticField(nativePlatformFactoryClass, "platform", nativePlatformCtor.newInstance());
        } catch (ClassNotFoundException exception) {
            // Before Java 8u40 HeadlessPlatform was located inside of a "headless" package.
            Constructor<?> nativePlatformCtor = Class.forName(
                "com.sun.glass.ui.monocle.headless.HeadlessPlatform").getDeclaredConstructor();
            nativePlatformCtor.setAccessible(true);
            assignPrivateStaticField(nativePlatformFactoryClass, "platform", nativePlatformCtor.newInstance());
        }
    }

    private void assignPrivateStaticField(Class<?> clazz, String name, Object value) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(clazz, value);
        field.setAccessible(false);
    }
}
