package entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Customer {

    private String id;
    private String first_name;
    private String last_name;
    static private Properties properties = new Properties();

    public Customer setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public Customer setFirstName(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public Customer setLastName(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        Customer.properties = properties;
    }

    public static String getCustomerJSONString(Customer customer) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String pojoJSON = mapper.writeValueAsString(customer);
        return pojoJSON;
    }
}
