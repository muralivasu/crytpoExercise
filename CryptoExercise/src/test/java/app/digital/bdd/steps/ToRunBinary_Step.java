package app.digital.bdd.steps;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class ToRunBinary_Step{

        private Scenario scenario;
        public String Filepath="";
        Process p = null;
        public StringBuilder Output = new StringBuilder();

        @Before
        public void before(Scenario scenario) {
            this.scenario = scenario;
        }

        @Given("^a valid binary file \"([^\"]*)\" is provided$")
        public void CheckValidBinaryFileIsProvided(String filepath) throws Throwable {
            if ((filepath != "") && (filepath.endsWith(".bin"))) {
                Filepath = filepath;
            } else {
                System.out.println("Invalid file");
            }
        }

        @When("^user change the file permissions to executable and execute the file$")
        public void userChangeTheFileToExecutableAndExecuteTheFile() throws Throwable {
        //First to make the binary file executable and then pass the binary file for execution
        String[] command = {"chmod 755 " +Filepath, Filepath};
         try {
                p = Runtime.getRuntime().exec(command);
         } catch (Exception e) {
                System.out.println("File Not found");
         }
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            System.out.println("Response:" + p.getInputStream().toString());
            String line = null;
            while ((line = reader.readLine()) != null) {
                Output.append(line);
            }
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

      @Then("^verify the response matches with the expected result \"([^\"]*)\"$")
       public void verifyTheResponseMatchesWithTheExpectedResult(String eResult) throws Throwable {
          if(Output.toString().contains(eResult))
           { System.out.println("it works!!!"); }
           else { System.out.println("Return code is not 200:" +Output); }
       }

}


