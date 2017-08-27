package org.hildan.livedoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApiDoc implements Comparable<ApiDoc>, Groupable {

    public final String livedocId = UUID.randomUUID().toString();

    // properties that can be overridden by the values specified in the @Api annotation
    private String name;

    private String description;

    private ApiVisibility visibility;

    private ApiStage stage;

    private String group;

    // properties that cannot be overridden in the mergeApiDoc method
    private List<ApiMethodDoc> methods;

    private ApiVersionDoc supportedversions;

    private ApiAuthDoc auth;

    public ApiDoc() {
        this.name = "";
        this.description = "";
        this.visibility = ApiVisibility.UNDEFINED;
        this.stage = ApiStage.UNDEFINED;
        this.group = "";
        this.methods = new ArrayList<>();
        this.supportedversions = null;
        this.auth = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApiMethodDoc> getMethods() {
        return methods;
    }

    public void setMethods(List<ApiMethodDoc> methods) {
        this.methods = methods;
    }

    public void addMethod(ApiMethodDoc apiMethod) {
        this.methods.add(apiMethod);
    }

    public ApiVersionDoc getSupportedversions() {
        return supportedversions;
    }

    public void setSupportedversions(ApiVersionDoc supportedversions) {
        this.supportedversions = supportedversions;
    }

    public ApiAuthDoc getAuth() {
        return auth;
    }

    public void setAuth(ApiAuthDoc auth) {
        this.auth = auth;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ApiVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ApiVisibility visibility) {
        this.visibility = visibility;
    }

    public ApiStage getStage() {
        return stage;
    }

    public void setStage(ApiStage stage) {
        this.stage = stage;
    }

    @Override
    public int compareTo(ApiDoc o) {
        return name.compareTo(o.getName());
    }

}
