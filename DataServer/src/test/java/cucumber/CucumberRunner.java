package cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * By gekoreed on 10/4/15.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/steps",
        glue = "cucumber/steps"
)
public class CucumberRunner {

}
