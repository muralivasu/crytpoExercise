package app.digital.bdd.runners;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/"},
        format = { "pretty", "json:target/cucumber.json" },
        tags = {"@RunBinaryFile"},
        glue = {"app.digital.bdd.steps"})
public class TestRun_BinaryFile {
}