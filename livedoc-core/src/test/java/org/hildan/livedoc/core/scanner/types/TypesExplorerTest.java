package org.hildan.livedoc.core.scanner.types;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

import org.hildan.livedoc.core.scanner.types.records.FieldTypesScanner;
import org.hildan.livedoc.core.scanner.types.records.RecordTypesScanner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;

import static org.junit.Assert.assertTrue;

public class TypesExplorerTest {

    private static Reflections reflections;

    @BeforeClass
    public static void setUp() throws Exception {
        reflections = new Reflections(TypesExplorerTest.class.getPackage().getName());
    }

    @Test
    public void findTypes_simpleType() {
        Set<Type> rootTypes = Collections.singleton(String.class);
        check(rootTypes, String.class);
    }

    private static class Custom {
        private Long longField;
    }

    @Test
    public void findTypes_customType() {
        Set<Type> rootTypes = Collections.singleton(Custom.class);
        check(rootTypes, Custom.class, Long.class);
    }

    private static class Child extends Custom {
        private Double doubleField;
    }

    @Test
    public void findTypes_inheritedFields() {
        Set<Type> rootTypes = Collections.singleton(Child.class);
        check(rootTypes, Child.class, Double.class, Long.class);
    }

    private static class TestRoot {
        private WithCustomObject nestedObject;

        private WithPrimitives nestedPrimitives;
    }

    private static class WithCustomObject {
        private Custom customObject;
    }

    private static class WithPrimitives {
        private String stringField;

        private int intField;
    }

    @Test
    public void findTypes_complexHierarchy() {
        Set<Type> rootTypes = Collections.singleton(TestRoot.class);
        check(rootTypes, TestRoot.class, WithCustomObject.class, WithPrimitives.class, Custom.class, Long.class,
                String.class, int.class);
    }

    private static void check(Set<Type> rootTypes, Class<?>... expectedFoundClasses) {
        RecordTypesScanner scanner = new FieldTypesScanner();
        TypesExplorer explorer = new TypesExplorer(reflections, scanner, clazz -> true);
        Set<Class<?>> classes = explorer.findTypes(rootTypes);
        assertContainsAll(classes, expectedFoundClasses);
    }

    private static void assertContainsAll(Set<Class<?>> classes, Class<?>... expectedClasses) {
        for (Class<?> expectedClass : expectedClasses) {
            assertTrue("Should find class " + expectedClass.getName(), classes.contains(expectedClass));
        }
    }
}
