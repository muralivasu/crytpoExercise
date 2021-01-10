package app.digital.bdd.Utils;

import EnvtVariables.Envt;
import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.config.HeaderConfig.headerConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.lessThan;

@Configuration
public class APIUtils {

    private RequestSpecification request;
    private Response response;
    private static final Logger logger = LoggerFactory.getLogger(APIUtils.class);

    public RequestSpecification getPostRequest()  {
    try {
            logger.debug("ApiUtils Test");
            File file = new File("target/ConsoleOutput.txt");
            PrintStream fileOutPutStream = new PrintStream(file);
            RestAssuredConfig configuration = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)).headerConfig(headerConfig().overwriteHeadersWithName("Content-Type")).logConfig(new LogConfig().defaultStream(fileOutPutStream));
            configuration = CurlLoggingRestAssuredConfigFactory.updateConfig(configuration);
            request = given().urlEncodingEnabled(false).config(configuration)
                    .contentType(Envt.contentType)
                    .headers("X-Locale", Envt.locale, "X-Chnl-CountryCode")
                    .log()
                    .all();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }


    public Response getResponse(RequestSpecification request, String url) {
        response = request.
                when().
                get(url);
//        response.then().log().all();
        return response;

    }

    public RequestSpecification getRequest() {
        try {
            logger.debug("ApiUtils Test");
            File file = new File("target/ConsoleOutput.txt");
            PrintStream fileOutPutStream = new PrintStream(file);
            RestAssuredConfig configuration = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)).logConfig(new LogConfig().defaultStream(fileOutPutStream));
            configuration = CurlLoggingRestAssuredConfigFactory.updateConfig(configuration);
            request = given().urlEncodingEnabled(false).config(configuration)
                    .contentType(Envt.contentType)
                    .headers("X-Locale", Envt.locale)
                    .log()
                    .all();
            request.then().log().all();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public Response getPostResponse(RequestSpecification request, String url) {
        response = request.
                when().
                post(url);
//        response.then().log().all();
        return response;

    }

    public Response getPutResponse(RequestSpecification request, String url) {
        response = request.
                when().
                put(url);
        response.then().log().all();
        return response;
    }


    public void validateStatusCode(int respCode) {

        response.
                then().
                assertThat().
                statusCode(respCode);

    }



    public Response getDeleteResponse(RequestSpecification request, String url) {
        response = request.
                when().
                delete(url);
        response.then().log().all();
        return response;

    }
}
