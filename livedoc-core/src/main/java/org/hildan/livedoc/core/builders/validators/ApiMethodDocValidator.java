package org.hildan.livedoc.core.builders.validators;

import java.util.Collections;

import org.hildan.livedoc.core.pojo.ApiHeaderDoc;
import org.hildan.livedoc.core.pojo.ApiMethodDoc;
import org.hildan.livedoc.core.pojo.ApiParamDoc;
import org.hildan.livedoc.core.pojo.ApiVerb;
import org.hildan.livedoc.core.pojo.Livedoc.MethodDisplay;

public class ApiMethodDocValidator {

    private static final String ERROR_MISSING_METHOD_PATH = "Missing documentation data: path";

    private static final String ERROR_MISSING_PATH_PARAM_NAME = "Missing documentation data: path parameter name";

    private static final String ERROR_MISSING_QUERY_PARAM_NAME = "Missing documentation data: query parameter name";

    private static final String ERROR_MISSING_HEADER_NAME = "Missing documentation data: header name";

    private static final String WARN_MISSING_METHOD_PRODUCES = "Missing documentation data: produces";

    private static final String WARN_MISSING_METHOD_CONSUMES = "Missing documentation data: consumes";

    private static final String HINT_MISSING_PATH_PARAM_DESCRIPTION = "Add description to ApiPathParam";

    private static final String HINT_MISSING_QUERY_PARAM_DESCRIPTION = "Add description to ApiQueryParam";

    private static final String HINT_MISSING_METHOD_DESCRIPTION = "Add description to ApiMethod";

    private static final String HINT_MISSING_METHOD_BODY_OBJECT =
            "Add annotation ApiBodyObject to document the " + "expected body of the request";

    private static final String HINT_MISSING_METHOD_RESPONSE_OBJECT =
            "Add annotation ApiResponseObject to document " + "the returned object";

    private static final String HINT_MISSING_METHOD_SUMMARY =
            "Method display set to SUMMARY, but summary info has " + "not been specified";

    private static final String MESSAGE_MISSING_METHOD_SUMMARY = "Missing documentation data: summary";

    /**
     * This checks that some of the properties are correctly set to produce a meaningful documentation and a working
     * playground. In case this is not the case, an error string is added to the jsondocerrors list in ApiMethodDoc. It
     * also checks that some properties are set to produce a meaningful documentation. In case this does not happen an
     * error string is added to the jsondocwarnings list in ApiMethodDoc.
     *
     * @param apiMethodDoc
     *         the doc to validate
     */
    public static void validate(ApiMethodDoc apiMethodDoc) {

        if (apiMethodDoc.getPath().isEmpty()) {
            apiMethodDoc.setPath(Collections.singleton(ERROR_MISSING_METHOD_PATH));
            apiMethodDoc.addJsondocerror(ERROR_MISSING_METHOD_PATH);
        }

        if (apiMethodDoc.getSummary().trim().isEmpty() && requiresSummary(apiMethodDoc)) {
            apiMethodDoc.setSummary(MESSAGE_MISSING_METHOD_SUMMARY);
            apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_SUMMARY);
        }

        if (apiMethodDoc.getDescription().trim().isEmpty()) {
            apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_DESCRIPTION);
        }

        validateHeaders(apiMethodDoc);
        validatePathParams(apiMethodDoc);
        validateQueryParams(apiMethodDoc);
        validateRequestBody(apiMethodDoc);
        validateResponse(apiMethodDoc);
    }

    private static boolean requiresSummary(ApiMethodDoc apiMethodDoc) {
        return apiMethodDoc.getDisplayMethodAs().equals(MethodDisplay.SUMMARY);
    }

    private static void validateHeaders(ApiMethodDoc apiMethodDoc) {
        for (ApiHeaderDoc apiHeaderDoc : apiMethodDoc.getHeaders()) {
            if (apiHeaderDoc.getName().trim().isEmpty()) {
                apiMethodDoc.addJsondocerror(ERROR_MISSING_HEADER_NAME);
            }
        }
    }

    private static void validatePathParams(ApiMethodDoc apiMethodDoc) {
        for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
            if (apiParamDoc.getName().trim().isEmpty()) {
                apiMethodDoc.addJsondocerror(ERROR_MISSING_PATH_PARAM_NAME);
            }

            if (apiParamDoc.getDescription().trim().isEmpty()) {
                apiMethodDoc.addJsondochint(HINT_MISSING_PATH_PARAM_DESCRIPTION);
            }
        }
    }

    private static void validateQueryParams(ApiMethodDoc apiMethodDoc) {
        for (ApiParamDoc apiParamDoc : apiMethodDoc.getQueryparameters()) {
            if (apiParamDoc.getName().trim().isEmpty()) {
                apiMethodDoc.addJsondocerror(ERROR_MISSING_QUERY_PARAM_NAME);
            }
            if (apiParamDoc.getDescription().trim().isEmpty()) {
                apiMethodDoc.addJsondochint(HINT_MISSING_QUERY_PARAM_DESCRIPTION);
            }
        }
    }

    private static void validateRequestBody(ApiMethodDoc apiMethodDoc) {
        if (apiMethodDoc.getVerb().stream().anyMatch(ApiVerb::requiresBody)) {
            if (apiMethodDoc.getConsumes().isEmpty()) {
                apiMethodDoc.addJsondocwarning(WARN_MISSING_METHOD_CONSUMES);
            }
            if (apiMethodDoc.getBodyobject() == null) {
                apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_BODY_OBJECT);
            }
        }
    }

    private static void validateResponse(ApiMethodDoc apiMethodDoc) {
        if (apiMethodDoc.getProduces().isEmpty()) {
            apiMethodDoc.addJsondocwarning(WARN_MISSING_METHOD_PRODUCES);
        }
        if (apiMethodDoc.getResponse() == null) {
            apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_RESPONSE_OBJECT);
        }
    }

}
