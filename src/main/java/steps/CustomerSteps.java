package steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import entities.Properties;
import services.ServiceAPIStub;

import static org.junit.Assert.*;

public class CustomerSteps {
    private RequestSpecification request;
    private Response response;

    private final static Customer customer = new Customer();
    private final static Properties properties = new Properties();

    private final static String customerName = "vitaliy";
    private final static String customerLastName = "vorona";
    private final static String customerId = "1684";
    private final static String propertiesAge = "34";
    private final static String propertiesActive = "true";
    private final static String propertiesDOB = "160184";


    @Given("^the positive Create a new Customer scenario$")
    public void given_the_positive_scenario() {
        request = RestAssured.with();
    }

    @Given("^the negative Create a new Customer scenario$")
    public void given_the_negative_scenario() {
        request = RestAssured.with();
    }


    @Given("^the positive GET Customer scenario$")
    public void the_positive_GET_Customer_scenario() {
        customer.setId(customerId);
        customer.setFirstName(customerName);
        customer.setLastName(customerLastName);
        properties.setAge(propertiesAge);
        properties.setDob(propertiesDOB);
        properties.setActive(propertiesActive);
        customer.setProperties(properties);
        ServiceAPIStub.getCustomerStub(customer);
        request = RestAssured.with();
    }

    @Given("^the negative GET Customer scenario$")
    public void the_negative_GET_Customer_scenario() {
        ServiceAPIStub.getCustomerStubNegative();
        request = RestAssured.with();
    }

    @When("^I provide Customers mandatory fields with id \"([^\"]*)\" First Name \"([^\"]*)\" and Last Name \"([^\"]*)\"$")
    public static void provide_Customers_mandatory_fields(String customerId, String customerName, String customerLastName) {
        customer.setId(customerId);
        customer.setFirstName(customerName);
        customer.setLastName(customerLastName);
        ServiceAPIStub.postCustomerStub(customer);
    }

    @When("^I dont provide or its invalid one of Customers id \"([^\"]*)\" First Name \"([^\"]*)\" or Last Name \"([^\"]*)\" parameters$")
    public static void provide_one_of_Customers_id_first_name_or_last_name_parameter(String id, String firstName, String lastName) {
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        properties.setAge(propertiesAge);
        properties.setDob(propertiesDOB);
        properties.setActive(propertiesActive);
        customer.setProperties(properties);
        ServiceAPIStub.postCustomerStubNegative(customer);
    }

    @When("^I make a POST request to the target endpoint$")
    public void make_a_POST_request_to_the_target_endpoint() {
        response = request.
                given().
                contentType("application/json").
                body(customer).
                when().
                post(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT).
                then().contentType(ContentType.JSON).
                extract().response();
    }

    @When("^I make a Get request to the system with an existing id$")
    public void make_a_Get_request_to_the_system_with_an_existing_id() {
        response = request.
                given().
                contentType("application/json").
                body(customer).
                when().
                get(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT +"/"+ customerId).
                then().contentType(ContentType.JSON).
                extract().response();
    }

    @When("^I make a Get request to the system with a non existing (\\d+) id$")
    public void make_a_Get_request_to_the_system_with_an_non_existing_id(int invalidId) {
        response = request.
                given().
                contentType("application/json").
                body(customer).
                when().
                get(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT +"/"+ invalidId).
                then().
                contentType(ContentType.JSON).extract().response();
    }

    @Then("^successfully created response message$")
    public void successfully_created_response_message() throws Throwable {
        final String  expectedResponseMessage = "{\n \"id\" : " +customer.getId()+ "\n, \"status\" : \"successfully created\"}";
        assertEquals("Expected response " +expectedResponseMessage+ " didn't match with actual: " +response.asString(), expectedResponseMessage, response.asString());
    }

    @Then("^Bad Request response message$")
    public void bad_Request_response_message() throws Throwable {
        final String  expectedResponseMessage = "{\"status\" : \"Bad Request\"}";
        assertEquals("Expected response " +expectedResponseMessage+ " didn't match with actual: " +response.asString(), expectedResponseMessage, response.asString());
    }

    @Then("^I should get (\\d+) response status code$")
    public void i_should_get_response_status_code(int expectedStatusCode) {
        assertEquals("Expected status code " +expectedStatusCode+ " didn't match ", expectedStatusCode, response.getStatusCode());
    }

    @Then("^correct full Customer information$")
    public void verify_correct_full_Customer_information() throws JsonProcessingException {
        final String customerExpectedInformation = Customer.getCustomerJSONString(customer);
        assertEquals("Expected response " +customerExpectedInformation+ " didn't match with actual: " +response.asString(), customerExpectedInformation, response.asString());
    }
}
