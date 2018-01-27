package http_requests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import interfaces.Get;

public class GetJsonRequest implements Get {

    public Response get(String endpoint) {
        RequestSpecification request = RestAssured.with();
        return request.
                given().
                    contentType("application/json").
                when().
                    log().all().
                    get(endpoint).
                then().
                    log().all().
                    contentType(ContentType.JSON).
                    extract().response();
    }
}
