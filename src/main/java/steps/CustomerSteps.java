package steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import di.CustomerProperties;
import entities.Customer;
import http_requests.GetJsonRequest;
import http_requests.PostJsonRequest;
import services.ServiceAPIStub;

import static org.junit.Assert.*;

public class CustomerSteps extends CommonSteps{

    private Response response;
    private CommonSteps commonSteps;
    private CustomerProperties world;

    public CustomerSteps(CustomerProperties customerProperties){
        this.world = customerProperties;
    }

    @Given("^the positive Create a new Customer scenario$")
    public void given_the_positive_scenario() {
        world.customer = new Customer();
        commonSteps = new CommonSteps();
        commonSteps.setPostRequestImpl(new PostJsonRequest());
    }

    @Given("^the negative Create a new Customer scenario$")
    public void given_the_negative_scenario() {
        world.customer = new Customer();
        commonSteps = new CommonSteps();
        commonSteps.setPostRequestImpl(new PostJsonRequest());
    }


    @Given("^the positive GET Customer scenario$")
    public void the_positive_GET_Customer_scenario() {
        world.customer = new Customer();
        world.customer.setProperties(world.properties);
        commonSteps = new CommonSteps();
        commonSteps.setGetRequestImpl(new GetJsonRequest());
        ServiceAPIStub.makeGetCustomerStub(world.customer);
    }

    @Given("^the negative GET Customer scenario$")
    public void the_negative_GET_Customer_scenario() {
        ServiceAPIStub.makeGetCustomerStubNegative();
        commonSteps = new CommonSteps();
        commonSteps.setGetRequestImpl(new GetJsonRequest());
    }

    @When("^I provide Customers mandatory fields with id \"([^\"]*)\" First Name \"([^\"]*)\" and Last Name \"([^\"]*)\"$")
    public void provide_Customers_mandatory_fields(String customerId, String customerName, String customerLastName) {
        world.customer.setId(customerId);
        world.customer.setFirstName(customerName);
        world.customer.setLastName(customerLastName);
        ServiceAPIStub.makePostCustomerStub(world.customer);
    }

    @When("^I dont provide or its invalid one of Customers id \"([^\"]*)\" First Name \"([^\"]*)\" or Last Name \"([^\"]*)\" parameters$")
    public void provide_one_of_Customers_id_first_name_or_last_name_parameter(String id, String firstName, String lastName) {
        world.customer.setId(id);
        world.customer.setFirstName(firstName);
        world.customer.setLastName(lastName);
        world.customer.setProperties(world.properties);
        ServiceAPIStub.makePostCustomerStubNegative(world.customer);
    }

    @When("^I make a POST request to the target endpoint$")
    public void make_a_POST_request_to_the_target_endpoint() {
        response = commonSteps.makePostRequest(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT, Customer.getCustomerJSONString(world.customer));
    }

    @When("^I make a Get request to the system with an existing id$")
    public void make_a_Get_request_to_the_system_with_an_existing_id() {
        response = commonSteps.makeGetRequest(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT +"/"+ world.customer.getId());
    }

    @When("^I make a Get request to the system with a non existing (\\d+) id$")
    public void make_a_Get_request_to_the_system_with_an_non_existing_id(int invalidId) {
        response = commonSteps.makeGetRequest(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT +"/"+ invalidId);
    }

    @Then("^successfully created response message$")
    public void successfully_created_response_message() throws Throwable {
        final String  expectedResponseMessage = "{\n \"id\" : " + world.customer.getId()+ "\n, \"status\" : \"successfully created\"}";
        assertEquals("Expected response " +expectedResponseMessage+ " didn't match with actual: " +response.asString(), expectedResponseMessage, response.asString());
    }

    @Then("^Bad Request response message$")
    public void bad_Request_response_message() throws Throwable {
        final String  expectedResponseMessage = "{\"status\" : \"Bad Request\"}";
        assertEquals("Expected response " +expectedResponseMessage+ " didn't match with actual: " +response.asString(), expectedResponseMessage, response.asString());
    }

    @Then("^I should get (\\d+) response status code$")
    public void i_should_get_response_status_code(int expectedStatusCode) {
        assertEquals("Expected status code " +expectedStatusCode+ " didn't match with ", expectedStatusCode, response.getStatusCode());
    }

    @Then("^correct full Customer information$")
    public void verify_correct_full_Customer_information() throws JsonProcessingException {
        final String customerExpectedInformation = Customer.getCustomerJSONString(world.customer);
        assertEquals("Expected response " +customerExpectedInformation+ " didn't match with actual: " +response.asString(), customerExpectedInformation, response.asString());
    }
}
