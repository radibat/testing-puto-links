package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Proper {
    public static final String propertiesDirection = "src/main/resources/config.properties";
    private static Properties props = new Properties();

    public static final Properties getProps() throws IOException {
        FileInputStream in = new FileInputStream(propertiesDirection);
        props.load(in);
        in.close();
        return props;
    }
}
