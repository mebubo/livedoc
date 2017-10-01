package org.hildan.livedoc.core.scanners.templates;

public interface TemplateProvider {

    Object getTemplate(Class<?> type);
}
