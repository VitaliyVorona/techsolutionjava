package steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import entities.Properties;
import services.ServiceAPIStub;

public class CustomerSteps {
    private RequestSpecification request;
    private ValidatableResponse response;
    private Response response_json;
    private ServiceAPIStub serviceAPIStub = new ServiceAPIStub();

    private static Customer customer = new Customer();
    private static Properties properties = new Properties();

    private static String customerName = "vitaliy";
    private static String customerLastName = "vorona";
    private static String customerId = "1684";
    private static String propertiesAge = "34";
    private static String propertiesActive = "true";
    private static String propertiesDOB = "160184";


    @Given("^The positive scenario$")
    public void given_the_positive_scenario() {
        request = RestAssured.with();
    }

    @Given("^The negative scenario$")
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
        request = RestAssured.with();
        ServiceAPIStub.getCustomerStubNegative();
    }

    @When("^I provide Customers mandatory fields$")
    public static void provide_Customers_mandatory_fields() {
        customer.setId(customerId);
        customer.setFirstName(customerName);
        customer.setLastName(customerLastName);
        ServiceAPIStub.postCustomerStub(customer);
    }

    @When("^I make a POST request to the target endpoint$")
    public void make_a_POST_request_to_the_target_endpoint() {
        response_json = request.
                given().
                contentType("application/json").
                body(customer).
                when().
                post(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT).
                then().contentType(ContentType.JSON).
                extract().response();
        System.out.println(response_json.asString());
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

    @When("^I make a Get request to the system with an existing id$")
    public void make_a_Get_request_to_the_system_with_an_existing_id() {
        response_json = request.
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
        response_json = request.
                given().
                contentType("application/json").
                body(customer).
                when().
                get(ServiceAPIStub.BASE_URL + ServiceAPIStub.CUSTOMER_ENDPOINT +"/"+ invalidId).
                then().
                contentType(ContentType.JSON).
                extract().response();
    }

    @Then("^I should get (\\d+) response code and \"([^\"]*)\" response message$")
    public void should_get_successful_response_message(int responseStatusCode, String responseMessage) {
//        response.statusCode(responseStatusCode);
        assert response_json.asString().contains(responseMessage);
    }

    @Then("^I should get (\\d+) Bad Request response message$")
    public void should_get_Bad_Request_message(int responseStatusCode) {
//        response.statusCode(responseStatusCode);
        assert response_json.asString().contains("Bad Request");
//        response.toString().contains("Bad Request");
    }

    @Then("^I should get (\\d+) response code and correct full information$")
    public void should_get_response_code_and_correct_full_information(int responseStatusCode) throws JsonProcessingException {
//        response.statusCode(responseStatusCode);
        assert response_json.asString().contains(Customer.getCustomerJSONString(customer));
    }

    @Then("^I should get (\\d+) Not Found response code$")
    public void should_get_Not_Found_response_code(int responseStatusCode) {
//        response.statusCode(responseStatusCode);
    }
}
