package com.qe.assignment.function;

import com.qe.assignment.util.Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Category {

    final static String URI = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";

    public static Response getCategoryResponse(){
        return Utils.send(null, null, URI, "get");
    }

    public static String getCategoryName(Response response){
        return Utils.getValue(response.getBody().asString(), "Name");
    }

    public static String getCanRelistValue(Response response){
        return Utils.getValue(response.getBody().asString(), "CanRelist");
    }

    public static String getDescriptionInPromotionArray(Response response, String attribute, String key, String keyValue, String keyDescription){
        return Utils.getValueInArrayByKey(response, attribute,key,keyValue,keyDescription);
    }
}
