package org.hildan.livedoc.springmvc.issues.issue151;

import java.util.Collections;
import java.util.List;

import org.hildan.livedoc.core.LivedocReader;
import org.hildan.livedoc.core.pojo.Livedoc;
import org.hildan.livedoc.core.pojo.Livedoc.MethodDisplay;
import org.hildan.livedoc.springmvc.SpringLivedocReaderFactory;
import org.junit.Assert;
import org.junit.Test;

// @ApiObject ignored for ParameterizedType return objects
// https://github.com/fabiomaffioletti/jsondoc/issues/151
public class Issue151Test {

    @Test
    public void testIssue151() {
        List<String> packages = Collections.singletonList("org.hildan.livedoc.springmvc.issues.issue151");
        LivedocReader builder = SpringLivedocReaderFactory.getReader(packages);
        Livedoc livedoc = builder.read("version", "basePath", true, MethodDisplay.URI);
        Assert.assertEquals(2, livedoc.getObjects().keySet().size());
        Assert.assertEquals(1, livedoc.getObjects().get("bargroup").size());
        Assert.assertEquals(1, livedoc.getObjects().get("foogroup").size());
    }

}
