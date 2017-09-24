package org.hildan.livedoc.core.scanners.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hildan.livedoc.core.annotations.ApiObject;
import org.hildan.livedoc.core.annotations.ApiObjectProperty;
import org.hildan.livedoc.core.scanners.properties.FieldPropertyScanner;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TemplateProviderTest {

    private enum TestEnum {
        VAL1, VAL2
    }

    private static class Parent {

        private String string;

        private int primitiveInt;

        private Long wrapperLong;

        private Child child;
    }

    private static class Child {

        private String name;
    }

    @Test
    public void getTemplate() {
        TemplateProvider templateProvider = new TemplateProvider(new FieldPropertyScanner());
        Object obj = templateProvider.getTemplate(Parent.class);

    }

    @Test
    public void getTemplate_generalTest() {
        TemplateProvider templateProvider = new TemplateProvider(new FieldPropertyScanner());
        @SuppressWarnings("unchecked")
        Map<String, Object> template = (Map<String, Object>) templateProvider.getTemplate(TemplateObject.class);

        Map<String, Object> subSubObjectTemplate = new HashMap<>();
        subSubObjectTemplate.put("id", "");
        subSubObjectTemplate.put("name", "");

        Map<String, Object> subObjectTemplate = new HashMap<>();
        subObjectTemplate.put("test", "");
        subObjectTemplate.put("id", 0);
        subObjectTemplate.put("subSubObj", subSubObjectTemplate);

        Assert.assertEquals(0, template.get("my_id"));
        Assert.assertEquals(0, template.get("idint"));
        Assert.assertEquals(0, template.get("idlong"));
        Assert.assertEquals("", template.get("name"));
        Assert.assertEquals("MALE", template.get("gender"));
        Assert.assertEquals(true, template.get("bool"));
        Assert.assertEquals(new ArrayList(), template.get("intarrarr"));
        Assert.assertEquals(subObjectTemplate, template.get("sub_obj"));
        Assert.assertEquals(new ArrayList(), template.get("untypedlist"));
        Assert.assertEquals(new ArrayList(), template.get("subsubobjarr"));
        Assert.assertEquals(new ArrayList(), template.get("stringlist"));
        Assert.assertEquals(new ArrayList(), template.get("stringarrarr"));
        Assert.assertEquals(new ArrayList(), template.get("integerarr"));
        Assert.assertEquals(new ArrayList(), template.get("stringarr"));
        Assert.assertEquals(new ArrayList(), template.get("intarr"));
        Assert.assertEquals(new ArrayList(), template.get("subobjlist"));
        Assert.assertEquals(new ArrayList(), template.get("wildcardlist"));
        Assert.assertEquals(new ArrayList(), template.get("longlist"));
        Assert.assertEquals("", template.get("namechar"));
        Assert.assertEquals(new HashMap(), template.get("map"));
        Assert.assertEquals(new HashMap(), template.get("mapstringinteger"));
        Assert.assertEquals(new HashMap(), template.get("mapsubobjinteger"));
        Assert.assertEquals(new HashMap(), template.get("mapintegersubobj"));
        Assert.assertEquals(new HashMap(), template.get("mapintegerlistsubsubobj"));
    }

    @ApiObject(name = "unordered")
    static class Unordered {

        @ApiObjectProperty(name = "xField")
        public String x;

        @ApiObjectProperty(name = "aField")
        public String a;

    }

    @ApiObject(name = "ordered")
    static class Ordered {

        @ApiObjectProperty(name = "bField", order = 2)
        public String b;

        @ApiObjectProperty(name = "xField", order = 1)
        public String x;

        @ApiObjectProperty(name = "aField", order = 2)
        public String a;
    }

    @Test
    public void getTemplate_customOrder() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        TemplateProvider templateProvider = new TemplateProvider(new FieldPropertyScanner());
        Object unorderedTemplate = templateProvider.getTemplate(Unordered.class);
        Assert.assertEquals("{\"aField\":\"\",\"xField\":\"\"}", mapper.writeValueAsString(unorderedTemplate));

        Object orderedTemplate = templateProvider.getTemplate(Ordered.class);
        Assert.assertEquals("{\"xField\":\"\",\"aField\":\"\",\"bField\":\"\"}",
                mapper.writeValueAsString(orderedTemplate));
    }
}
