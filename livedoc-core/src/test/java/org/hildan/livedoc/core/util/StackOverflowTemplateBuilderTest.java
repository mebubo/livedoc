package org.hildan.livedoc.core.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.hildan.livedoc.core.builders.templates.ObjectTemplateBuilder;
import org.hildan.livedoc.core.util.pojo.NotAnnotatedStackOverflowObjectOne;
import org.hildan.livedoc.core.util.pojo.NotAnnotatedStackOverflowObjectTwo;
import org.hildan.livedoc.core.util.pojo.StackOverflowTemplateObjectOne;
import org.hildan.livedoc.core.util.pojo.StackOverflowTemplateObjectTwo;
import org.hildan.livedoc.core.util.pojo.StackOverflowTemplateSelf;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

public class StackOverflowTemplateBuilderTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testTemplate()
            throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        Set<Class<?>> classes = Sets.newHashSet(StackOverflowTemplateSelf.class, StackOverflowTemplateObjectOne.class,
                StackOverflowTemplateObjectTwo.class);

        StackOverflowTemplateSelf objectSelf = new StackOverflowTemplateSelf();
        Map<String, Object> template = ObjectTemplateBuilder.build(objectSelf.getClass(), classes);
        System.out.println(mapper.writeValueAsString(template));

        StackOverflowTemplateObjectOne objectOne = new StackOverflowTemplateObjectOne();
        template = ObjectTemplateBuilder.build(objectOne.getClass(), classes);
        System.out.println(mapper.writeValueAsString(template));

        StackOverflowTemplateObjectTwo objectTwo = new StackOverflowTemplateObjectTwo();
        template = ObjectTemplateBuilder.build(objectTwo.getClass(), classes);
        System.out.println(mapper.writeValueAsString(template));
    }

    @Test
    public void typeOneTwo() throws IOException {
        Set<Class<?>> classes = Sets.newHashSet(NotAnnotatedStackOverflowObjectOne.class,
                NotAnnotatedStackOverflowObjectTwo.class);

        NotAnnotatedStackOverflowObjectOne typeOne = new NotAnnotatedStackOverflowObjectOne();
        Map<String, Object> template = ObjectTemplateBuilder.build(typeOne.getClass(), classes);
        System.out.println(mapper.writeValueAsString(template));
    }

}
