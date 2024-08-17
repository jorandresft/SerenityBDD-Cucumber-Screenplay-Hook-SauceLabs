package co.com.saucelabs.web.stepdefinitions.hook;

import co.com.saucelabs.web.hook.OpenWeb;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static co.com.saucelabs.web.utils.Constants.WEB_URL;

public class Hook {

    @Before
    public void setup() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("The {string} open the web site")
    public void openWebSite(String actor) {
        OnStage.theActorCalled(actor).attemptsTo(
                OpenWeb.browserUrl(WEB_URL)
        );
    }
}
