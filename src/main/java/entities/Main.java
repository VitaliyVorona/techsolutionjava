package entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    static String getCustomerJSONString(Customer customer) {
        ObjectMapper mapper = new ObjectMapper();
        String pojoJSON = null;
        try {
            pojoJSON = mapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pojoJSON;
    }

    public static void main(String[] args) {
        Customer customer = new Customer();
        Properties properties = new Properties();
        customer.setId("23");
        customer.setFirstName("Vitaliy");
        customer.setLastName("Vorona");
        properties.setAge("234");
        properties.setActive("true");
        properties.setDob("41234");
        customer.setProperties(properties);
        System.out.println(getCustomerJSONString(customer));
    }
}
