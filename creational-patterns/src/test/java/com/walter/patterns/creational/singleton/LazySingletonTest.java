package com.walter.patterns.creational.singleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
class LazySingletonTest {
    @BeforeEach
    void resetSingleton() throws Exception {
        Field instanceField = LazySingleton.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }
    @Test
    void shouldReturnSameInstance() {
        LazySingleton first = LazySingleton.getInstance("first");
        LazySingleton second = LazySingleton.getInstance("second");
        assertSame(first, second);
    }
    @Test
    void shouldKeepStateFromFirstInitialization() {
        LazySingleton singleton = LazySingleton.getInstance("practice");
        assertEquals("practice", singleton.getNote());
    }
}
