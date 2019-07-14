package Neo.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JDBCProperties {

    private static Properties instance;

    public static Properties getInstance() throws IOException {
        if (instance == null) {
            instance = getProperties();
        }
        return instance;
    }
    private static Properties getProperties() throws IOException {
        try (InputStream input = new FileInputStream("src/main/resources/jdbc.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            throw ex;
        }
    }
    private JDBCProperties() {
    }
}
