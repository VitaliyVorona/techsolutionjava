package utilities;

import java.io.FileReader;
import java.util.Properties;

public class Utilities {

    public static final Properties config = loadProperties();


    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader("config.properties");
            properties.load(fileReader);
        } catch (Exception e) {
            throw new RuntimeException("Exception in loadProperties(): " + e.getMessage(), e);
        }
        return properties;
    }

}
