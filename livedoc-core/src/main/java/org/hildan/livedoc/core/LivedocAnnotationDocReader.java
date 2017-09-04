package org.hildan.livedoc.core;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.hildan.livedoc.core.annotations.Api;
import org.hildan.livedoc.core.annotations.ApiBodyObject;
import org.hildan.livedoc.core.annotations.ApiMethod;
import org.hildan.livedoc.core.annotations.ApiObject;
import org.hildan.livedoc.core.builders.doc.ApiDocReader;
import org.hildan.livedoc.core.builders.doc.ApiMethodDocReader;
import org.hildan.livedoc.core.builders.templates.ObjectTemplate;
import org.hildan.livedoc.core.pojo.ApiDoc;
import org.hildan.livedoc.core.pojo.ApiMethodDoc;
import org.hildan.livedoc.core.util.LivedocUtils;

public class LivedocAnnotationDocReader implements DocReader {

    private final AnnotatedTypesFinder annotatedTypesFinder;

    public LivedocAnnotationDocReader(AnnotatedTypesFinder annotatedTypesFinder) {
        this.annotatedTypesFinder = annotatedTypesFinder;
    }

    @Override
    public Collection<Class<?>> findControllerTypes() {
        return annotatedTypesFinder.apply(Api.class);
    }

    @Override
    public Optional<ApiDoc> buildApiDocBase(Class<?> controllerType) {
        return Optional.of(ApiDocReader.read(controllerType));
    }

    @Override
    public Optional<ApiMethodDoc> buildApiMethodDoc(Method method, ApiDoc parentApiDoc, Map<Class<?>, ObjectTemplate> templates) {
        ApiMethod methodAnnotation = method.getAnnotation(ApiMethod.class);
        if (methodAnnotation == null) {
            return Optional.empty(); // this basic builder only supports annotated methods
        }
        ApiMethodDoc apiMethodDoc = ApiMethodDocReader.read(method, parentApiDoc);
        if (method.isAnnotationPresent(ApiBodyObject.class)) {
            apiMethodDoc.getBodyobject()
                        .setTemplate(templates.get(method.getAnnotation(ApiBodyObject.class).clazz()));
        }
        Integer index = LivedocUtils.getIndexOfParameterWithAnnotation(method, ApiBodyObject.class);
        if (index != -1) {
            apiMethodDoc.getBodyobject().setTemplate(templates.get(method.getParameterTypes()[index]));
        }
        return Optional.of(apiMethodDoc);
    }

    @Override
    public Collection<? extends Type> extractTypesToDocument(Method method) {
        // this default reader is not able to extract anything from methods
        // this will only be useful in specialized readers like the spring one
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends Type> getAdditionalTypesToDocument() {
        return annotatedTypesFinder.apply(ApiObject.class);
    }
}