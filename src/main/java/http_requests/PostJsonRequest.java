package http_requests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import interfaces.Post;

public class PostJsonRequest implements Post {

    public Response post(String endpoint, String body) {
        RequestSpecification request = RestAssured.with();
        return request.
                given().
                    contentType("application/json").
                    body(body).
                when().
                    log().all().
                    post(endpoint).
                then().
                    log().all().
                    contentType(ContentType.JSON).
                    extract().response();
    }
}
