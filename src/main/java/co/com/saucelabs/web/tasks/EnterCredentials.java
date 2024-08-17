package co.com.saucelabs.web.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;

import static co.com.saucelabs.web.ui.LoginPage.PASSWORD_TEXT;
import static co.com.saucelabs.web.ui.LoginPage.USERNAME_TEXT;

public class EnterCredentials implements Task {

    private final String user;
    private final String pass;

    public EnterCredentials(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(user).into(USERNAME_TEXT),
                Enter.theValue(pass).into(PASSWORD_TEXT)
        );
    }

    public static EnterCredentials enterCredentials (String user, String pass) {
        return Tasks.instrumented(EnterCredentials.class, user, pass);
    }
}
