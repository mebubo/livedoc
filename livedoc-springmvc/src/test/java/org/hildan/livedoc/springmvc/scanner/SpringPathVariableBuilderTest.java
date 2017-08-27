package org.hildan.livedoc.springmvc.scanner;

import java.util.Collections;
import java.util.Iterator;

import org.hildan.livedoc.core.LivedocBuilder;
import org.hildan.livedoc.core.annotation.ApiPathParam;
import org.hildan.livedoc.core.pojo.ApiDoc;
import org.hildan.livedoc.core.pojo.ApiMethodDoc;
import org.hildan.livedoc.core.pojo.ApiParamDoc;
import org.hildan.livedoc.core.pojo.Livedoc.MethodDisplay;
import org.hildan.livedoc.springmvc.SpringLivedocBuilderFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class SpringPathVariableBuilderTest {

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping
    public class SpringController {

        @RequestMapping(value = "/param-one/{id}/{string}")
        public void paramOne(@PathVariable Long id, @PathVariable("name") String name) {

        }

        @RequestMapping(value = "/param-one/{id}/{string}/{test}")
        public void paramTwo(@ApiPathParam(name = "id", description = "my description") @PathVariable Long id,
                @PathVariable("name") String name, @PathVariable @ApiPathParam Long test) {

        }

    }

    @Test
    public void testPathVariable() {
        LivedocBuilder builder = SpringLivedocBuilderFactory.springLivedocBuilder(Collections.emptyList());
        ApiDoc apiDoc = builder.readApiDoc(SpringController.class, MethodDisplay.URI, Collections.emptyMap());
        Assert.assertEquals("SpringController", apiDoc.getName());
        Assert.assertEquals(2, apiDoc.getMethods().size());
        for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
            if (apiMethodDoc.getPath().contains("/param-one/{id}/{string}")) {
                Assert.assertEquals(2, apiMethodDoc.getPathparameters().size());
                Iterator<ApiParamDoc> iterator = apiMethodDoc.getPathparameters().iterator();
                ApiParamDoc id = iterator.next();
                Assert.assertEquals("", id.getName());
                Assert.assertEquals("Long", id.getLivedocType().getOneLineText());
                ApiParamDoc name = iterator.next();
                Assert.assertEquals("name", name.getName());
                Assert.assertEquals("String", name.getLivedocType().getOneLineText());
            }

            if (apiMethodDoc.getPath().contains("/param-one/{id}/{string}/{test}")) {
                Assert.assertEquals(3, apiMethodDoc.getPathparameters().size());
                Iterator<ApiParamDoc> iterator = apiMethodDoc.getPathparameters().iterator();
                ApiParamDoc id = iterator.next();
                Assert.assertEquals("id", id.getName());
                Assert.assertEquals("Long", id.getLivedocType().getOneLineText());
                ApiParamDoc name = iterator.next();
                Assert.assertEquals("name", name.getName());
                Assert.assertEquals("String", name.getLivedocType().getOneLineText());
                ApiParamDoc test = iterator.next();
                Assert.assertEquals("", test.getName());
                Assert.assertEquals("Long", test.getLivedocType().getOneLineText());
            }
        }

    }

    @SuppressWarnings("unused")
    @Controller
    @RequestMapping
    public class SpringController2 {

        @RequestMapping(value = "/param-one/{id}/{string}")
        public void paramOne(@ApiPathParam(description = "description for id") @PathVariable Long id,
                @PathVariable("name") String name) {

        }

    }

    @Test
    public void testPathVariableWithJSONDoc() {
        LivedocBuilder builder = SpringLivedocBuilderFactory.springLivedocBuilder(Collections.emptyList());
        ApiDoc apiDoc = builder.readApiDoc(SpringController2.class, MethodDisplay.URI, Collections.emptyMap());
        Assert.assertEquals("SpringController2", apiDoc.getName());
        Assert.assertEquals(1, apiDoc.getMethods().size());
        for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
            if (apiMethodDoc.getPath().contains("/param-one/{id}/{string}")) {
                Assert.assertEquals(2, apiMethodDoc.getPathparameters().size());
                Iterator<ApiParamDoc> iterator = apiMethodDoc.getPathparameters().iterator();
                ApiParamDoc id = iterator.next();
                Assert.assertEquals("", id.getName());
                Assert.assertEquals("Long", id.getLivedocType().getOneLineText());
                Assert.assertEquals("description for id", id.getDescription());
                ApiParamDoc name = iterator.next();
                Assert.assertEquals("name", name.getName());
                Assert.assertEquals("String", name.getLivedocType().getOneLineText());
            }
        }

    }

}
