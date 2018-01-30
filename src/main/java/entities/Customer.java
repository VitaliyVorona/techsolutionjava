package entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Customer extends User {

    private String id;
    private String first_name;
    private String last_name;

    private Properties customer_properties;

    public String getId() {

        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Properties getProperties() {
        return customer_properties;
    }


    public static class Builder {
        private String id = "1179";
        private String first_name = "Elon";
        private String last_name = "Musk";
        private Properties properties;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public Builder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public Builder customer_properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

    private Customer(Builder builder) {
        id = builder.id;
        first_name = builder.first_name;
        last_name = builder.last_name;
        customer_properties = builder.properties;
    }

    public static String getCustomerJSONString(Object customer) {
        ObjectMapper mapper = new ObjectMapper();
        String pojoJSON = null;
        try {
            pojoJSON = mapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pojoJSON;
    }
}
