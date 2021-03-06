package org.hildan.livedoc.core.builders.doc;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.hildan.livedoc.core.annotations.ApiAuthBasic;
import org.hildan.livedoc.core.annotations.ApiAuthBasicUser;
import org.hildan.livedoc.core.annotations.ApiAuthNone;
import org.hildan.livedoc.core.annotations.ApiAuthToken;
import org.hildan.livedoc.core.pojo.ApiAuthDoc;
import org.hildan.livedoc.core.pojo.ApiAuthType;

public class ApiAuthDocReader {

    public static final String ANONYMOUS = "anonymous";

    public static ApiAuthDoc read(Class<?> controller) {
        if (controller.isAnnotationPresent(ApiAuthNone.class)) {
            return readFromApiAuthNoneAnnotation(controller.getAnnotation(ApiAuthNone.class));
        }
        if (controller.isAnnotationPresent(ApiAuthBasic.class)) {
            return readFromApiAuthBasicAnnotation(controller.getAnnotation(ApiAuthBasic.class));
        }
        if (controller.isAnnotationPresent(ApiAuthToken.class)) {
            return readFromApiAuthTokenAnnotation(controller.getAnnotation(ApiAuthToken.class));
        }
        return null;
    }

    public static ApiAuthDoc read(Method method) {
        if (method.isAnnotationPresent(ApiAuthNone.class)) {
            return readFromApiAuthNoneAnnotation(method.getAnnotation(ApiAuthNone.class));
        }
        if (method.isAnnotationPresent(ApiAuthBasic.class)) {
            return readFromApiAuthBasicAnnotation(method.getAnnotation(ApiAuthBasic.class));
        }
        if (method.isAnnotationPresent(ApiAuthToken.class)) {
            return readFromApiAuthTokenAnnotation(method.getAnnotation(ApiAuthToken.class));
        }
        return read(method.getDeclaringClass());
    }

    private static ApiAuthDoc readFromApiAuthNoneAnnotation(ApiAuthNone annotation) {
        ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
        apiAuthDoc.setType(ApiAuthType.NONE.name());
        apiAuthDoc.addRole(ANONYMOUS);
        return apiAuthDoc;
    }

    private static ApiAuthDoc readFromApiAuthBasicAnnotation(ApiAuthBasic annotation) {
        ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
        apiAuthDoc.setType(ApiAuthType.BASIC_AUTH.name());
        apiAuthDoc.setRoles(Arrays.asList(annotation.roles()));
        for (ApiAuthBasicUser testuser : annotation.testusers()) {
            apiAuthDoc.addTestUser(testuser.username(), testuser.password());
        }
        return apiAuthDoc;
    }

    private static ApiAuthDoc readFromApiAuthTokenAnnotation(ApiAuthToken annotation) {
        ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
        apiAuthDoc.setType(ApiAuthType.TOKEN.name());
        apiAuthDoc.setScheme(annotation.scheme());
        apiAuthDoc.setRoles(Arrays.asList(annotation.roles()));
        for (String testtoken : annotation.testtokens()) {
            apiAuthDoc.addTestToken(testtoken);
        }
        return apiAuthDoc;
    }

}
