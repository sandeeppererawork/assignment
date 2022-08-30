package com.qe.assignment.util;

import java.util.Map;
import io.restassured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class Utils {

    public static Response send(Map<String, String> headers, String bodyString, String uri, String requestMethod) {
        RestAssured.baseURI = uri;
        RestAssured.basePath = "";
        RequestSpecification requestSpecification = getRequestSpec(headers, bodyString);
        RestAssured.useRelaxedHTTPSValidation();
        requestSpecification = RestAssured.given().spec(requestSpecification);
        return execute(requestMethod, requestSpecification, uri);
    }

    public static RequestSpecification getRequestSpec(Map<String, String> headers, String body) {
        RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
        if (headers != null)
            reqSpecBuilder.addHeaders(headers);
        if (body != null && body.length() > 0) {
            reqSpecBuilder.setBody(body);
        }
        return reqSpecBuilder.build();
    }

    public static Response execute(String reqMethod, RequestSpecification requestSpec, String uri) {
        RequestSpecification requestSpecification = requestSpec;
        requestSpecification = given(requestSpecification).config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        Response response = null;
        if ("GET".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().get(uri, new Object[0]);
        }
        if ("POST".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().post(uri, new Object[0]);
        }
        if ("PUT".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().put(uri, new Object[0]);
        }
        if ("DELETE".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().delete(uri, new Object[0]);
        }
        if ("PATCH".equalsIgnoreCase(reqMethod)) {
            response = requestSpecification.expect().when().patch(uri, new Object[0]);
        }
        return response;
    }

    public static String getValue(String response, String key) {
        try {
            JSONObject responseBody;
            try {
                responseBody = new JSONObject(response);
                return responseBody.get(key).toString();
            } catch (Exception e) {
                e.getMessage();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String getValueInArrayByKey(Response response, String attribute, String key, String keyValue, String keyDescription){
        JSONObject element = new JSONObject();
        try {
            JSONObject responseBody = new JSONObject(response.getBody().asString());
            JSONArray promotionArray = (JSONArray)responseBody.get(attribute);
            for(int n = 0; n < promotionArray.length(); n++)
            {
                JSONObject object = promotionArray.getJSONObject(n);
                if (object.get(key).equals(keyValue)){
                    element = object;
                    break;
                }
            }
            return element.get(keyDescription).toString();
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }
}