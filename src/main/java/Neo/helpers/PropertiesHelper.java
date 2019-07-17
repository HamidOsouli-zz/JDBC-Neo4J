package Neo.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    private static PropertiesHelper instance;
    private Properties props;
    private static final Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);


    public static PropertiesHelper getInstance() {
        if (instance == null) {
            instance = new PropertiesHelper();
        }
        return instance;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    private PropertiesHelper() {
        String pathToPropertiesFile = "src/main/resources/application.properties";
        // try only to close input at the end
        try (InputStream input = new FileInputStream(pathToPropertiesFile)) {
            // load a properties file
            props = new Properties();
            props.load(input);
        } catch(FileNotFoundException fileNotFoundException) {
            logger.error("Properties file not found in " + pathToPropertiesFile);
            throw new RuntimeException();
        } catch (IOException IOException) {
            logger.error("Properties file found but in" + pathToPropertiesFile + "but not loaded");
            throw new RuntimeException();
        }
    }
}
