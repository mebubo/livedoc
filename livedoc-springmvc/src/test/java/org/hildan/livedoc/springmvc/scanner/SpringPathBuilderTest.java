package org.hildan.livedoc.springmvc.scanner;

import java.util.Arrays;
import java.util.List;

import org.hildan.livedoc.core.pojo.ApiDoc;
import org.hildan.livedoc.core.pojo.Livedoc.MethodDisplay;
import org.hildan.livedoc.springmvc.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.Assert.assertTrue;

public class SpringPathBuilderTest {

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping
    private static class SpringController {

        @RequestMapping(value = "/path")
        public void slashPath() {
        }

        @RequestMapping(value = "/")
        public void path() {
        }

        @RequestMapping()
        public void none() {
        }
    }

    @Test
    public void testPath() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringController.class, MethodDisplay.URI);
        Assert.assertEquals("SpringController", apiDoc.getName());

        boolean slashPath = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().contains("/path"));
        assertTrue(slashPath);

        boolean slash = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().contains("/"));
        assertTrue(slash);
    }

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping
    private static class SpringController2 {

        @RequestMapping
        public void none() {
        }

        @RequestMapping(value = "/test")
        public void test() {
        }
    }

    @Test
    public void testPath2() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringController2.class, MethodDisplay.URI);
        Assert.assertEquals("SpringController2", apiDoc.getName());

        boolean none = apiDoc.getMethods().stream().anyMatch(input -> {
            System.out.println(input.getPath());
            return input.getPath().contains("/");
        });
        assertTrue(none);

        boolean test = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().contains("/test"));
        assertTrue(test);
    }

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping("/child")
    private static class SpringControllerChild extends SpringController2 {


    }

    @Test
    public void testPathInherited() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringControllerChild.class, MethodDisplay.URI);
        Assert.assertEquals("SpringControllerChild", apiDoc.getName());

        boolean none = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().contains("/child"));
        assertTrue(none);

        boolean test = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().contains("/child/test"));
        assertTrue(test);
    }

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping(value = {"/path1", "/path2/"})
    private static class SpringController3 {

        @RequestMapping(value = {"/path3", "path4"})
        public void none() {
        }
    }

    @Test
    public void testPath3() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringController3.class, MethodDisplay.URI);
        Assert.assertEquals("SpringController3", apiDoc.getName());

        boolean allRight = apiDoc.getMethods()
                                 .stream()
                                 .anyMatch(input -> input.getPath()
                                                         .containsAll(Arrays.asList("/path1/path3", "/path1/path4",
                                                                 "/path2/path3", "/path2/path4")));
        assertTrue(allRight);
    }

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping(value = "/path")
    private static class SpringController4 {

        @RequestMapping
        public void none() {
        }
    }

    @Test
    public void testPath4() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringController4.class, MethodDisplay.URI);
        Assert.assertEquals("SpringController4", apiDoc.getName());

        boolean allRight = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().contains("/path"));
        assertTrue(allRight);
    }

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping(path = {"/path", "/path2"}, value = "/val1")
    private static class SpringController5 {

        @RequestMapping
        public void none() {
        }
    }

    @Test
    public void testPath5() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringController5.class, MethodDisplay.URI);
        Assert.assertEquals("SpringController5", apiDoc.getName());

        List<String> expectedPaths = Arrays.asList("/path", "/path2", "/val1");
        boolean allRight = apiDoc.getMethods().stream().anyMatch(input -> input.getPath().containsAll(expectedPaths));
        assertTrue(allRight);
    }

    @Test
    public void testPathWithMethodDisplayMethod() {
        ApiDoc apiDoc = TestUtils.buildDoc(SpringController5.class, MethodDisplay.METHOD);
        List<String> expectedPaths = Arrays.asList("/path", "/path2", "/val1");
        boolean allRight = apiDoc.getMethods()
                                 .stream()
                                 .anyMatch(input -> input.getPath().containsAll(expectedPaths)
                                         && input.getDisplayedMethodString().contains("none"));
        assertTrue(allRight);
    }
}
