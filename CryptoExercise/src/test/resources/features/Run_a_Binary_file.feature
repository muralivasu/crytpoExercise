@RunBinaryFile
Feature: To run a binary file and validate the response

  Scenario Outline:To Validate the response of executable binary file
    Given a valid binary file "<filepath>" is provided
    When user change the file permissions to executable and execute the file
    Then verify the response matches with the expected result "<eResult>"
    Examples:
    |filepath                                                    |eResult|
    |./sample.exe                                                |It works!!!|
    |./sample                                                    |It works!!!|
    #Binary file with invalid data
    |./sample.exe                                                |It works!!!|
    |                                                            |It works!!!|
