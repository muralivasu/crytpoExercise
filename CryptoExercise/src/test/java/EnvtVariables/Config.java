package EnvtVariables;

import java.io.FileReader;
import java.util.Properties;

public class Config{
     public static void config( ) throws Throwable {
            FileReader reader = new FileReader("/Users/cncbiuser/Downloads/CryptoExercise/src/test/resources/bdd.properties");
            Properties p = new Properties();
            p.load(reader);
            System.out.println(p.getProperty("URL"));
            Envt.locale=p.getProperty("locale1");
            Envt.contentType=p.getProperty("contentType");
        }

    }

