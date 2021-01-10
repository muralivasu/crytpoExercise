@GetWeather
Feature: To test Get Weather details API for Current weather dataset

  Scenario Outline:To Validate the request and response for Get Weather API with current weather dataset
    #My intention to pass the endpoint as value is to re-use step definition with different endpoints
    Given valid end point "<EndPoint>" is  passed in the get request
    When user triggers get weather url with datatype "<dataType>" and language "<language>"
    Then verify current weather response with post status code "<statusCode>"
    And Also verify the response is as expected for status code "<statusCode>"
    Examples:
    |EndPoint                                                    |dataType  |language|statusCode   |
    #Positive case
    |https://data.weather.gov.hk/weatherAPI/opendata/weather.php| rhrread |en        |  200        |
    #With out https
    |http://data.weather.gov.hk/weatherAPI/opendata/weather.php| rhrread |en        |  200        |
    #with no datatype paramter
    |https://data.weather.gov.hk/weatherAPI/opendata/weather.php|  |en         |  200        |
    #with no langauges parameter
    |https://data.weather.gov.hk/weatherAPI/opendata/weather.php|rhrread    |        |  200        |
    #with invalid datatype
    |https://data.weather.gov.hk/weatherAPI/opendata/weather.php| null      |en        |  200        |
     #with invalid language
    |https://data.weather.gov.hk/weatherAPI/opendata/weather.php| rhrread |invalid        |  200        |
     #with blank datatype and language
    |https://data.weather.gov.hk/weatherAPI/opendata/weather.php|           |        |  200        |

