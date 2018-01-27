package interfaces;

import com.jayway.restassured.response.Response;

public interface Post {
    Response post(String endpoint, String body);
}
