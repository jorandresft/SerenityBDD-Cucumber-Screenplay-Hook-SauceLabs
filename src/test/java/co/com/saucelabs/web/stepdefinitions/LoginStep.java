package co.com.saucelabs.web.stepdefinitions;

import co.com.saucelabs.web.questions.TextEqual;
import co.com.saucelabs.web.tasks.EnterCredentials;
import co.com.saucelabs.web.tasks.OnclickLoginButton;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static co.com.saucelabs.web.ui.LoginPage.ERROR_TEXT;
import static co.com.saucelabs.web.ui.ProductsPage.TEXT_ELEMENT;
import static co.com.saucelabs.web.utils.Constants.ACTOR;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.hamcrest.Matchers.equalTo;

public class LoginStep {

    @And("The {string} send his credentials user {string} and password {string}")
    public void theSendHisCredentialsUserAndPassword(String actor, String user, String pass) {
        theActorCalled(actor).attemptsTo(
                EnterCredentials.enterCredentials(user, pass)
        );
    }

    @When("He clicks on the login button")
    public void heClicksOnTheLoginButton() {
        theActorCalled(ACTOR).attemptsTo(
                OnclickLoginButton.onclickLoginButton()
        );
    }

    @Then("He should see the title {string} with login is successful")
    public void heShouldSeeTheTitleWithLoginIsSuccessful(String text) {
        theActorCalled(ACTOR).should(
                seeThat(TextEqual.textEqual(TEXT_ELEMENT), equalTo(text))
        );

        /*
        // Otra opción para la validación
        theActorCalled(ACTOR).should(
            seeThat(ValidateElementText.validateElementText(text))
        );
        */
    }

    @Then("He should see the title {string}")
    public void heShouldSeeTheTitle(String text) {
        theActorCalled(ACTOR).should(
                seeThat(TextEqual.textEqual(ERROR_TEXT), equalTo(text))
        );
    }
}
