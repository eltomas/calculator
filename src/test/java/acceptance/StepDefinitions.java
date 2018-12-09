package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

/**
 * Steps definitions for calculator.acceptance
 */
public class StepDefinitions {
    private static final String CALCULATOR_URL_PROPERTY = "calculator.url";
    private static final String SERVER = System.getProperty(CALCULATOR_URL_PROPERTY);

    private RestTemplate restTemplate = new RestTemplate();

    private String a;
    private String b;
    private String result;

    @Given("^I have two numbers: (.*) and (.*)$")
    public void i_have_two_numbers(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @When("^the calculator sums them$")
    public void the_calculator_sums_them() {
        String url = format("%s/sum?a=%s&b=%s", SERVER, a, b);
        result = restTemplate.getForObject(url, String.class);
    }

    @Then("^I receive (.*) as a result$")
    public void iReceiveAsAResult(String expectedResult) {
        assertEquals(expectedResult, result);
    }
}
