package steps;

import com.jayway.restassured.response.Response;
import interfaces.Get;
import interfaces.Post;

public class CommonSteps {

    public Get get;
    public Post post;

    public Response makePostRequest(String endpoint, String body) {
        return post.post(endpoint, body);
    }

    public Response makeGetRequest(String endpoint) {
        return get.get(endpoint);
    }

    public void setGetRequestImpl(Get get) {
        this.get = get;
    }

    public void setPostRequestImpl(Post post) {
        this.post = post;
    }
}
