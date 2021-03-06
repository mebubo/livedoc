package org.hildan.livedoc.core;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hildan.livedoc.core.annotations.flow.ApiFlow;
import org.hildan.livedoc.core.annotations.flow.ApiFlowSet;
import org.hildan.livedoc.core.annotations.global.ApiChangelogSet;
import org.hildan.livedoc.core.annotations.global.ApiGlobal;
import org.hildan.livedoc.core.annotations.global.ApiMigrationSet;
import org.hildan.livedoc.core.builders.doc.ApiGlobalDocReader;
import org.hildan.livedoc.core.pojo.ApiMethodDoc;
import org.hildan.livedoc.core.pojo.flow.ApiFlowDoc;
import org.hildan.livedoc.core.pojo.global.ApiGlobalDoc;

public class LivedocAnnotationGlobalDocReader implements GlobalDocReader {

    private final AnnotatedTypesFinder annotatedTypesFinder;

    public LivedocAnnotationGlobalDocReader(AnnotatedTypesFinder annotatedTypesFinder) {
        this.annotatedTypesFinder = annotatedTypesFinder;
    }

    /**
     * Gets the API flow documentation for the set of classes passed as argument
     */
    @Override
    public Set<ApiFlowDoc> getApiFlowDocs(Map<String, ? extends ApiMethodDoc> apiMethodDocsById) {
        Set<ApiFlowDoc> apiFlowDocs = new TreeSet<>();
        for (Class<?> clazz : getClassesWithFlows()) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(ApiFlow.class)) {
                    ApiFlowDoc apiFlowDoc = getApiFlowDoc(method, apiMethodDocsById);
                    apiFlowDocs.add(apiFlowDoc);
                }
            }
        }
        return apiFlowDocs;
    }

    private Iterable<Class<?>> getClassesWithFlows() {
        return annotatedTypesFinder.apply(ApiFlowSet.class);
    }

    private ApiFlowDoc getApiFlowDoc(Method method, Map<String, ? extends ApiMethodDoc> apiMethodDocsById) {
        return ApiFlowDoc.buildFromAnnotation(method.getAnnotation(ApiFlow.class), apiMethodDocsById);
    }

    @Override
    public ApiGlobalDoc getApiGlobalDoc() {
        Collection<Class<?>> global = getClassesWithGlobalDoc();
        Collection<Class<?>> changelogs = getClassesWithChangelogs();
        Collection<Class<?>> migrations = getClassesWithMigrations();
        return ApiGlobalDocReader.read(global, changelogs, migrations);
    }

    private Collection<Class<?>> getClassesWithGlobalDoc() {
        return annotatedTypesFinder.apply(ApiGlobal.class);
    }

    private Collection<Class<?>> getClassesWithChangelogs() {
        return annotatedTypesFinder.apply(ApiChangelogSet.class);
    }

    private Collection<Class<?>> getClassesWithMigrations() {
        return annotatedTypesFinder.apply(ApiMigrationSet.class);
    }
}
