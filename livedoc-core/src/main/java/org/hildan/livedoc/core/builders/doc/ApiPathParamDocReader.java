package org.hildan.livedoc.core.builders.doc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hildan.livedoc.core.annotations.ApiParams;
import org.hildan.livedoc.core.annotations.ApiPathParam;
import org.hildan.livedoc.core.builders.types.LivedocType;
import org.hildan.livedoc.core.builders.types.LivedocTypeBuilder;
import org.hildan.livedoc.core.pojo.ApiParamDoc;

public class ApiPathParamDocReader {

    public static Set<ApiParamDoc> read(Method method) {
        Set<ApiParamDoc> docs = new LinkedHashSet<>();

        if (method.isAnnotationPresent(ApiParams.class)) {
            for (ApiPathParam apiParam : method.getAnnotation(ApiParams.class).pathparams()) {
                LivedocType type = LivedocTypeBuilder.build(apiParam.clazz());
                ApiParamDoc apiParamDoc = buildFromAnnotation(apiParam, type);
                docs.add(apiParamDoc);
            }
        }

        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof ApiPathParam) {
                    ApiPathParam annotation = (ApiPathParam) parametersAnnotations[i][j];
                    Class<?> clazz = method.getParameterTypes()[i];
                    Type type = method.getGenericParameterTypes()[i];
                    LivedocType livedocType = LivedocTypeBuilder.build(type);
                    ApiParamDoc apiParamDoc = buildFromAnnotation(annotation, livedocType);
                    docs.add(apiParamDoc);
                }
            }
        }

        return docs;
    }

    private static ApiParamDoc buildFromAnnotation(ApiPathParam annotation, LivedocType livedocType) {
        return new ApiParamDoc(annotation.name(), annotation.description(), livedocType, "true",
                annotation.allowedvalues(), annotation.format(), null);
    }
}
