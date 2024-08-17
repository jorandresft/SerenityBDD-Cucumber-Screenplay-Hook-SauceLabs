package co.com.saucelabs.web.hook;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class OpenWeb implements Task {

    private EnvironmentVariables environmentVariables;
    private String webUrl;

    public OpenWeb(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String pathWebUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty(webUrl);

        actor.attemptsTo(Open.url(pathWebUrl));
    }

    public static Performable browserUrl(String webUrl) {
        return instrumented(OpenWeb.class, webUrl);
    }
}
