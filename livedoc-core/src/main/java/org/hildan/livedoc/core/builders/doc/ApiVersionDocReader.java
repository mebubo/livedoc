package org.hildan.livedoc.core.builders.doc;

import java.lang.reflect.AnnotatedElement;

import org.hildan.livedoc.core.annotations.ApiVersion;
import org.hildan.livedoc.core.pojo.ApiVersionDoc;

public class ApiVersionDocReader {

    public static ApiVersionDoc read(Class<?> clazz) {
        return read(clazz, null);
    }

    /**
     * In case this annotation is present at type and method level, then the method annotation will override the type
     * one.
     */
    public static ApiVersionDoc read(AnnotatedElement element, ApiVersionDoc defaultDoc) {
        ApiVersion elementAnnotation = element.getAnnotation(ApiVersion.class);
        if (elementAnnotation != null) {
            return buildFromAnnotation(element.getAnnotation(ApiVersion.class));
        }
        return defaultDoc;
    }

    private static ApiVersionDoc buildFromAnnotation(ApiVersion annotation) {
        ApiVersionDoc apiVersionDoc = new ApiVersionDoc();
        apiVersionDoc.setSince(annotation.since());
        apiVersionDoc.setUntil(annotation.until());
        return apiVersionDoc;
    }

}