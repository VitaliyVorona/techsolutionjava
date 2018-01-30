package steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import di.CustomerProperties;
import entities.Customer;
import entities.Properties;
import http_requests.GetJsonRequest;
import http_requests.PostJsonRequest;
import services.ServiceAPIStub;

import static org.junit.Assert.*;

public class CustomerSteps extends CommonSteps {

    private Response response;
    private CommonSteps commonSteps;
    private CustomerProperties world;

    public CustomerSteps(CustomerProperties customerProperties) {
        this.world = customerProperties;
    }

    @Given("^the positive Create a new Customer scenario$")
    public void given_the_positive_scenario() throws Throwable {
        commonSteps = new CommonSteps();
        commonSteps.setPostRequestImpl(new PostJsonRequest());
    }

    @Given("^the negative Create a new Customer scenario$")
    public void given_the_negative_scenario() throws Throwable {
        commonSteps = new CommonSteps();
        commonSteps.setPostRequestImpl(new PostJsonRequest());
    }


    @Given("^the positive GET Customer scenario$")
    public void the_positive_GET_Customer_scenario() throws Throwable {
        commonSteps = new CommonSteps();
        commonSteps.setGetRequestImpl(new GetJsonRequest());

    }

    @Given("^the negative GET Customer scenario$")
    public void the_negative_GET_Customer_scenario() throws Throwable {
        ServiceAPIStub.makeGetCustomerStubNegative();
        commonSteps = new CommonSteps();
        commonSteps.setGetRequestImpl(new GetJsonRequest());
    }

    @When("^I provide all Customers mandatory fields with id \"([^\"]*)\" First Name \"([^\"]*)\" and Last Name \"([^\"]*)\"$")
    public void provide_Customers_mandatory_fields(String id, String firstName, String lastName) throws Throwable {
        world.customer_properties = new Properties.Builder().build();
        world.customer = new Customer.Builder().
                id(id).
                first_name(firstName).
                last_name(lastName).
                customer_properties(world.customer_properties).
                build();
        ServiceAPIStub.makePostCustomerStub(world.customer);
    }

    @When("^I dont provide or its invalid one of Customers id \"([^\"]*)\" First Name \"([^\"]*)\" or Last Name \"([^\"]*)\" parameters$")
    public void provide_one_of_Customers_id_first_name_or_last_name_parameter(String id, String firstName, String lastName) throws Throwable  {
        world.customer_properties = new Properties.Builder().build();
        world.customer = new Customer.Builder().
                id(id).
                first_name(firstName).
                last_name(lastName).
                customer_properties(world.customer_properties).
                build();
        ServiceAPIStub.makePostCustomerStubNegative(world.customer);
    }

    @When("^I make POST request to the target endpoint$")
    public void make_POST_request_to_the_target_endpoint() throws Throwable {
        response = commonSteps.makePostRequest(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT, Customer.getCustomerJSONString(world.customer));
    }

    @When("^I make a Get request to the system with an existing id$")
    public void make_Get_request_to_the_system_with_an_existing_id() throws Throwable {
        world.customer = new Customer.Builder().
                build();
        ServiceAPIStub.makeGetCustomerStub(world.customer);
        response = commonSteps.makeGetRequest(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT + "/" + world.customer.getId());
    }

    @When("^I make a Get request to the system with a non existing (\\d+) id$")
    public void make_a_Get_request_to_the_system_with_an_non_existing_id(int invalidId) throws Throwable {
        response = commonSteps.makeGetRequest(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT + "/" + invalidId);
    }

    @Then("^successfully created response message$")
    public void successfully_created_response_message() throws Throwable {
        final String expectedResponseMessage = "{\n \"id\" : " + world.customer.getId() + "\n, \"status\" : \"successfully created\"}";
        assertEquals("Expected response " + expectedResponseMessage + " didn't match with actual: " + response.asString(), expectedResponseMessage, response.asString());
    }

    @Then("^\"([^\"]*)\" response message$")
    public void response_message(String responseMessage) throws Throwable {
        final String expectedResponseMessage = "{\"status\" : " + "\"" + responseMessage + "\"" + "}";
        assertEquals("Expected response " + expectedResponseMessage + " didn't match with actual: " + response.asString(), expectedResponseMessage, response.asString());
    }

    @Then("^I should get (\\d+) response status code$")
    public void i_should_get_response_status_code(int expectedStatusCode) throws Throwable {
        assertEquals("Expected status code " + expectedStatusCode + " didn't match with ", expectedStatusCode, response.getStatusCode());
    }

    @Then("^correct full Customer information$")
    public void verify_correct_full_Customer_information() throws JsonProcessingException {
        final String customerExpectedInformation = Customer.getCustomerJSONString(world.customer);
        assertEquals("Expected response " + customerExpectedInformation + " didn't match with actual: " + response.asString(), customerExpectedInformation, response.asString());
    }
}
