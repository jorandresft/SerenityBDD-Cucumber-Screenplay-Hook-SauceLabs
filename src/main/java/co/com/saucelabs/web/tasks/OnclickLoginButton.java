package co.com.saucelabs.web.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

import static co.com.saucelabs.web.ui.LoginPage.LOGIN_BUTTON;

public class OnclickLoginButton implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(LOGIN_BUTTON)
        );
    }

    public static OnclickLoginButton onclickLoginButton() {
        return Tasks.instrumented(OnclickLoginButton.class);
    }
}
