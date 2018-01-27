package interfaces;

import com.jayway.restassured.response.Response;

public interface Get {
    Response get(String endpoint);
}
