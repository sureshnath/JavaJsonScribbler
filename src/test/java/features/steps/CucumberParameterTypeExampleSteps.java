package features.steps;

import java.util.Map;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;

public class CucumberParameterTypeExampleSteps {

    String objectUnderTest;

    @When("I ask what color is {}")
    public void iAskWhatColorIsSolarObject(String solarObject) {
        objectUnderTest = solarObject;
    }

    @Then("I get an answer it is {}")
    public void iGetAnAnswerItIsThisColor(Color expectedColor) {
        assertThat(solarObjects.get(objectUnderTest)).isEqualTo(expectedColor);
    }

    @ParameterType("\\w+")
    public Color color(String color){
        return Color.valueOf(color);
    }

    final Map<String, Color> solarObjects = Map.of( "Earth", Color.Blue, "Sun", Color.Red
            , "Moon", Color.White, "Mercury", Color.Silver);

    enum Color{
        Red, White, Blue, Silver
    }

}
