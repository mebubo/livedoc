package org.hildan.livedoc.core.pojo;

public enum ApiVerb {
    GET,
    POST,
    PATCH,
    PUT,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE,
    UNDEFINED;

    public boolean requiresBody() {
        return this == POST || this == PUT;
    }
}
