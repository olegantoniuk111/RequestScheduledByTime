import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static InputStream in;
    private static Properties properties = new Properties();

    public static int getRequestsQuantity (){
        return Integer.valueOf(getPropertiesByKey("requestQuantity"));
    }

    public static int getTimeForExecution(){
        return Integer.valueOf(getPropertiesByKey("timeForExecution"));
    }
    public static String getHostName(){
        return getPropertiesByKey("host");
    }


    private static String getPropertiesByKey (String key) throws NoSuchPropertieInPropertiesFileException  {
        try {
            in = new FileInputStream("src/main/resources/application.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file application.properties not found");
            System.exit(0);
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exeption during reading data from InputStream");
        }

        String propertiesValue = properties.getProperty(key);
        if (propertiesValue == null) {
            RuntimeException exception = new NoSuchPropertieInPropertiesFileException(key);
            throw exception;
        }else {
            return propertiesValue;
        }
    }

}