package app.digital.bdd.steps;

import EnvtVariables.Config;
import app.digital.bdd.Utils.APIUtils;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;

public class GetWeatherData_Step {

    public ArrayList<String> expectedRainfallList = new ArrayList<>();
    public ArrayList<String> expectedTemperatureList = new ArrayList<>();

    public void setRainfallList() {
        this.expectedRainfallList.add("unit");
        this.expectedRainfallList.add("place");
        this.expectedRainfallList.add("max");
        this.expectedRainfallList.add("main");

    }
    public void setTemperatureDataList() {
        this.expectedTemperatureList.add("place");
        this.expectedTemperatureList.add("value");
        this.expectedTemperatureList.add("unit");

    }
    private Response getResponse;
    private RequestSpecification getRequest;

    private Scenario scenario;
    @Autowired
    private APIUtils apiUtils;
    private String getWeatherURL;


    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("^valid end point \"([^\"]*)\" is  passed in the get request$")
    public void valid_end_point_is_passed_in_the_get_request(String endPoint) throws Throwable {
        Config.config();
        getRequest = apiUtils.getRequest();
        getWeatherURL = endPoint+"?";

    }

    @When("^user triggers get weather url with datatype \"([^\"]*)\" and language \"([^\"]*)\"$")
    public void user_triggers_get_weather_url_with_datatype_and_language(String dataType, String language) {
        getResponse = apiUtils.getResponse(getRequest, getWeatherURL+"dataType="+dataType+"&lang="+language);
//        System.out.println("Get Query Response: " + getResponse.prettyPrint());
    }


    @And("^Also verify the response is as expected for status code \"([^\"]*)\"$")
    public void also_verify_the_response_is_as_expected_for_status_code(int statusCode) {
        if(statusCode==200) {
            if (getResponse.prettyPrint().contains("Please include valid parameters in API request")) {
                System.out.println("Please include valid parameters in API request");}
            else
            {
                String unit, place , main, tempPlace, tempValue, tempUnit;
                String max;
                setRainfallList();
                setTemperatureDataList();
                Assert.assertNotNull(getResponse.path("rainfall"));
                Assert.assertNotNull(getResponse.path("rainfall.startTime"));
                Assert.assertNotNull(getResponse.path("rainfall.endTime"));
                Assert.assertNotNull(getResponse.path("icon"));
                Assert.assertNotNull(getResponse.path("iconUpdateTime"));
                Assert.assertNotNull(getResponse.path("uvindex"));
                Assert.assertNotNull(getResponse.path("updateTime"));
                Assert.assertNotNull(getResponse.path("temperature"));
                Assert.assertNotNull(getResponse.path("warningMessage"));
                Assert.assertNotNull(getResponse.path("humidity.recordTime"));
                Assert.assertNotNull(getResponse.path("humidity.data"));

                ArrayList<Map<String, String>> rainfallList = getResponse.path("rainfall.data");

                for (Map<String, String> mapItem : rainfallList) {
                    for (Map.Entry<String, String> item : mapItem.entrySet()) {
                        Assert.assertTrue(expectedRainfallList.contains(item.getKey()));
                        if (item.getKey().equals("unit")) {
                            unit = item.getValue();
                            Assert.assertNotNull(unit);
                        }
                        if (item.getKey().equals("place")) {
                            place = item.getValue();
                            Assert.assertNotNull(place);
                        }
                        if (item.getKey().equals("max")) {
                            max = String.valueOf(item.getValue());
                            Assert.assertNotNull(max);
                        }
                        if (item.getKey().equals("main")) {
                            main = item.getValue();
                            Assert.assertNotNull(main);
                        }
                    }
                }

                ArrayList<Map<String, String>> temperatureList = getResponse.path("temperature.data");
                for (Map<String, String> mapItem : temperatureList) {
                    for (Map.Entry<String, String> item : mapItem.entrySet()) {
                        Assert.assertTrue(expectedTemperatureList.contains(item.getKey()));
                        if (item.getKey().equals("place")) {
                            tempPlace = item.getValue();
                            Assert.assertNotNull(tempPlace);
                        }
                        if (item.getKey().equals("value")) {
                            tempValue = String.valueOf(item.getValue());
                            Assert.assertNotNull(tempValue);
                        }
                        if (item.getKey().equals("unit")) {
                            tempUnit = item.getValue();
                            Assert.assertNotNull(tempUnit);
                        }

                    }
                }
            }
        }
    }

    @Then("^verify current weather response with post status code \"([^\"]*)\"$")
    public void verify_current_weather_response_with_post_status_code(int  statusCode) throws Throwable {
        String currentCurlCommand, lastCurlCommand = "";
        BufferedReader br = new BufferedReader(new FileReader("target/ConsoleOutput.txt"));
        while ((currentCurlCommand = br.readLine()) != null) {
            lastCurlCommand = lastCurlCommand+currentCurlCommand+"\n";
        }
        scenario.write(lastCurlCommand);
        apiUtils.validateStatusCode(statusCode);
    }



}






