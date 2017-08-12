package org.hildan.livedoc.core.scanner.builder;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.hildan.livedoc.core.annotation.ApiAuthBasic;
import org.hildan.livedoc.core.annotation.ApiAuthBasicUser;
import org.hildan.livedoc.core.annotation.ApiAuthNone;
import org.hildan.livedoc.core.pojo.ApiAuthType;
import org.hildan.livedoc.core.scanner.DefaultJSONDocScanner;
import org.hildan.livedoc.core.annotation.ApiAuthToken;
import org.hildan.livedoc.core.pojo.ApiAuthDoc;

public class JSONDocApiAuthDocBuilder {
	
	public static ApiAuthDoc getApiAuthDocForController(Class<?> controller) {
		if(controller.isAnnotationPresent(ApiAuthNone.class)) {
			return buildFromApiAuthNoneAnnotation(controller.getAnnotation(ApiAuthNone.class));
		}
		
		if(controller.isAnnotationPresent(ApiAuthBasic.class)) {
			return buildFromApiAuthBasicAnnotation(controller.getAnnotation(ApiAuthBasic.class));
		}
		
		if(controller.isAnnotationPresent(ApiAuthToken.class)) {
			return buildFromApiAuthTokenAnnotation(controller.getAnnotation(ApiAuthToken.class));
		}
		
		return null;
	}
	
	public static ApiAuthDoc getApiAuthDocForMethod(Method method) {
		if(method.isAnnotationPresent(ApiAuthNone.class)) {
			return buildFromApiAuthNoneAnnotation(method.getAnnotation(ApiAuthNone.class));
		}
		
		if(method.isAnnotationPresent(ApiAuthBasic.class)) {
			return buildFromApiAuthBasicAnnotation(method.getAnnotation(ApiAuthBasic.class));
		}
		
		if(method.isAnnotationPresent(ApiAuthToken.class)) {
			return buildFromApiAuthTokenAnnotation(method.getAnnotation(ApiAuthToken.class));
		}
		
		return getApiAuthDocForController(method.getDeclaringClass());
	}
	
	private static ApiAuthDoc buildFromApiAuthNoneAnnotation(ApiAuthNone annotation) {
		ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
		apiAuthDoc.setType(ApiAuthType.NONE.name());
		apiAuthDoc.addRole(DefaultJSONDocScanner.ANONYMOUS);
		return apiAuthDoc;
	}
	
	private static ApiAuthDoc buildFromApiAuthBasicAnnotation(ApiAuthBasic annotation) {
		ApiAuthDoc apiAuthDoc = new ApiAuthDoc();
		apiAuthDoc.setType(ApiAuthType.BASIC_AUTH.name());
		apiAuthDoc.setRoles(Arrays.asList(annotation.roles()));
		for (ApiAuthBasicUser testuser : annotation.testusers()) {
			apiAuthDoc.addTestUser(testuser.username(), testuser.password());
		}
		return apiAuthDoc;
	}
	
	private static ApiAuthDoc buildFromApiAuthTokenAnnotation(ApiAuthToken annotation) {
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