package services;

import com.github.tomakehurst.wiremock.client.WireMock;
import entities.Customer;
import utilities.Utilities;

public class ServiceAPIStub {

    public final static String BASE_URL = Utilities.config.getProperty("BASE_URL");
    public final static String CUSTOMER_ENDPOINT = Utilities.config.getProperty("CUSTOMER_END_POINT");


    public static void makePostCustomerStub(Customer customer) {
            WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(CUSTOMER_ENDPOINT))
                    .withRequestBody(WireMock.equalToJson(Customer.getCustomerJSONString(customer)))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(201)
                            .withBody("{\n \"id\" : " +customer.getId()+ "\n, \"status\" : \"successfully created\"}")));
    }

    public static void makePostCustomerStubNegative(Customer customer) {
            WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(CUSTOMER_ENDPOINT))
                    .withRequestBody(WireMock.equalToJson(Customer.getCustomerJSONString(customer)))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(400)
                            .withBody("{\"status\" : \"Bad Request\"}")));
    }


    public static void makeGetCustomerStub(Customer customer) {
            WireMock.stubFor(WireMock.get(WireMock.urlMatching(CUSTOMER_ENDPOINT + "/[0-9]+"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(200)
                            .withBody(Customer.getCustomerJSONString(customer))));

    }

    public static void makeGetCustomerStubNegative() {
        WireMock.stubFor(WireMock.get(WireMock.urlMatching(CUSTOMER_ENDPOINT + "/[0-9]+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));
    }
}
