package org.hildan.livedoc.springmvc.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hildan.livedoc.core.pojo.ApiHeaderDoc;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ValueConstants;

public class SpringHeaderBuilder {

    /**
     * Reads the headers that are necessary to resolve to the given method.
     *
     * @param method
     *         the method to read the headers from
     * @param controller
     *         the controller to read type-level headers from
     *
     * @return the {@link ApiHeaderDoc}s for the given method
     */
    public static Set<ApiHeaderDoc> buildHeaders(Method method, Class<?> controller) {
        Set<ApiHeaderDoc> headers = new LinkedHashSet<>();

        RequestMapping typeAnnotation = controller.getAnnotation(RequestMapping.class);
        RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);

        if (typeAnnotation != null) {
            List<String> headersStringList = Arrays.asList(typeAnnotation.headers());
            addToHeaders(headers, headersStringList);
        }

        if (methodAnnotation != null) {
            List<String> headersStringList = Arrays.asList(methodAnnotation.headers());
            addToHeaders(headers, headersStringList);
        }

        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof RequestHeader) {
                    RequestHeader requestHeader = (RequestHeader) parametersAnnotations[i][j];
                    headers.add(new ApiHeaderDoc(requestHeader.value(), "",
                            requestHeader.defaultValue().equals(ValueConstants.DEFAULT_NONE) ?
                                    new String[] {} :
                                    new String[] {requestHeader.defaultValue()}));
                }
            }
        }

        return headers;
    }

    private static void addToHeaders(Set<ApiHeaderDoc> headers, List<String> headersStringList) {
        for (String header : headersStringList) {
            String[] splitHeader = header.split("=");
            if (splitHeader.length > 1) {
                headers.add(new ApiHeaderDoc(splitHeader[0], null, new String[] {splitHeader[1]}));
            } else {
                headers.add(new ApiHeaderDoc(splitHeader[0], null, new String[] {}));
            }
        }
    }

}
