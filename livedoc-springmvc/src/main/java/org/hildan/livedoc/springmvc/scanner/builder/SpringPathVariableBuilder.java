package org.hildan.livedoc.springmvc.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hildan.livedoc.core.annotations.ApiPathParam;
import org.hildan.livedoc.core.builders.types.LivedocType;
import org.hildan.livedoc.core.builders.types.LivedocTypeBuilder;
import org.hildan.livedoc.core.pojo.ApiParamDoc;
import org.springframework.web.bind.annotation.PathVariable;

public class SpringPathVariableBuilder {

    public static Set<ApiParamDoc> buildPathVariable(Method method) {
        Set<ApiParamDoc> apiParamDocs = new LinkedHashSet<>();

        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            PathVariable pathVariable = null;
            ApiPathParam apiPathParam = null;
            ApiParamDoc apiParamDoc = null;

            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof PathVariable) {
                    pathVariable = (PathVariable) parametersAnnotations[i][j];
                }
                if (parametersAnnotations[i][j] instanceof ApiPathParam) {
                    apiPathParam = (ApiPathParam) parametersAnnotations[i][j];
                }

                if (pathVariable != null) {
                    LivedocType livedocType = LivedocTypeBuilder.build(method.getGenericParameterTypes()[i]);
                    apiParamDoc = new ApiParamDoc(pathVariable.value(), "", livedocType, "true", new String[] {}, null,
                            "");
                    mergeApiPathParamDoc(apiPathParam, apiParamDoc);
                }
            }

            if (apiParamDoc != null) {
                apiParamDocs.add(apiParamDoc);
            }
        }

        return apiParamDocs;
    }

    /**
     * Available properties that can be overridden: name, description, allowedvalues, format. Name is overridden only if
     * it's empty in the apiParamDoc argument. Description, format and allowedvalues are copied in any case.
     *
     * @param apiPathParam
     * @param apiParamDoc
     */
    private static void mergeApiPathParamDoc(ApiPathParam apiPathParam, ApiParamDoc apiParamDoc) {
        if (apiPathParam != null) {
            if (apiParamDoc.getName().trim().isEmpty()) {
                apiParamDoc.setName(apiPathParam.name());
            }
            apiParamDoc.setDescription(apiPathParam.description());
            apiParamDoc.setAllowedvalues(apiPathParam.allowedvalues());
            apiParamDoc.setFormat(apiPathParam.format());
        }
    }

}
