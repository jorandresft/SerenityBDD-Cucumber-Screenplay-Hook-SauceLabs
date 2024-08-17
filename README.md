
# SerenityBDD-Cucumber-Screenplay-Hook-SauceLabs

Proyecto para validar el login en la página https://www.saucedemo.com/v1/index.html con Serenity BDD, Cucumber y Screenplay.

## Paso a Paso

Pasos de como realice el proyecto

```bash
    Abrir IntelliJ y crear un proyecto Java y Gradle
```

Modificar el archivo build.gradle con la siguiente estructura

```bash
buildscript {
    ext.serenityCoreVersion = '3.3.0'
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("net.serenity-bdd:serenity-gradle-plugin:$serenityCoreVersion")
        classpath "net.serenity-bdd:serenity-single-page-report:$serenityCoreVersion"
    }
}
plugins {
    id "net.serenity-bdd.serenity-gradle-plugin" version "${serenityCoreVersion}"
    id 'java'
    id 'eclipse'
    id 'idea'
}

defaultTasks 'clean','test','aggregate'
apply plugin: 'net.serenity-bdd.serenity-gradle-plugin'

group 'co.com.screenplay.project'
version '1.0-SNAPSHOT'

compileJava.options.encoding = "UTF-8"
compileTestJava.options.encoding = "UTF-8"

sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
    mavenCentral()
}

ext {
    slf4jVersion = '1.7.7'
    serenityCoreVersion = '3.3.4'
    serenityCucumberVersion = '3.3.4'
    junitVersion = '5.8.2'
    assertJVersion = '3.22.0'
    logbackVersion = '1.2.10'
    lombokVersion = '1.18.22'
    log4jVersion = '2.17.1'
}

dependencies {
    testImplementation ("net.serenity-bdd:serenity-core:${serenityCoreVersion}"){
        exclude group: 'org.apache.groovy', module: 'groovy'
    }
    implementation "net.serenity-bdd:serenity-ensure:${serenityCoreVersion}"
    implementation "net.serenity-bdd:serenity-junit5:${serenityCoreVersion}"
    implementation "net.serenity-bdd:serenity-cucumber:${serenityCucumberVersion}"
    implementation "net.serenity-bdd:serenity-screenplay:${serenityCoreVersion}"
    implementation "net.serenity-bdd:serenity-screenplay-webdriver:${serenityCoreVersion}"
    implementation "net.serenity-bdd:serenity-screenplay-rest:${serenityCoreVersion}"

    implementation "org.junit.jupiter:junit-jupiter-api:${junitVersion}"
    implementation "org.junit.jupiter:junit-jupiter-engine:${junitVersion}"
    implementation "org.assertj:assertj-core:${assertJVersion}"
    implementation "ch.qos.logback:logback-classic:${logbackVersion}"

    compileOnly "org.projectlombok:lombok:${lombokVersion}"
    annotationProcessor "org.projectlombok:lombok:${lombokVersion}"
    testCompileOnly "org.projectlombok:lombok:${lombokVersion}"
    testAnnotationProcessor "org.projectlombok:lombok:${lombokVersion}"
    implementation group: 'com.github.javafaker', name: 'javafaker', version: '1.0.2'

}
test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
    systemProperties System.getProperties()
}

serenity {
    testRoot = "co.com.saucelabs.web.runners"
    requirementsBaseDir = "src/test/resources/features"
    reports = ["single-page-html"]
}

gradle.startParameter.continueOnFailure = true
test.finalizedBy(aggregate)
```

Crear las carpetas necesarias para el proyecto

```bash
    src/test/java/resources/features
    src/test/java/.../runners, stepdefinitions/hook
    src/main/java/.../ui, tasks, questions, hook... etc
```

Dentro de la carpeta resources crear el archivo serenity.conf con la siguiente estructura

```bash
webdriver {
  driver = "edge"
  #driver = "${driver}" #gradle clean test -Ddriver=firefox
}

//headless.mode = true

webdriver {
  capabilities {
    browserName = "MicrosoftEdge"
    "ms:edgeOptions" {
      args = ["start-maximized","test-type", "ignore-certificate-errors",
        "incognito", "disable-infobars", "disable-gpu", "disable-default-apps", "disable-popup-blocking", "remote-allow-origins=*"]
    }
  }
}

firefox {
    webdriver {
      capabilities {
        browserName = "firefox"
        pageLoadStrategy = "normal"
        acceptInsecureCerts = true
        unhandledPromptBehavior = "dismiss"
        strictFileInteractability = true

        "moz:firefoxOptions" {
          args = ["-headless"],
          prefs {
            "javascript.options.showInConsole": false
            "browser.tabs.documentchannel.ppdc": false
            "browser.tab.animated": false
            "browser.panorama.animate_zoom": false
            "network.dns.disablePrefetch": true
            "network.prefetch-next": false
            "network.http.speculative-parallel-limit": 0
            "webgl.disabled": true
            "network.http.pipelining": true
            "network.http.proxy.pipelining": true
            "network.http.pipelining.maxrequests": 8
            "network.http.max-connections": 96
            "network.http.max-connections-per-server": 32
            "network.dns.disableIPv6": false
            "plugin.expose_full_path": true
            "nglayout.initialpaint.delay": 0
          },
          log {"level": "info"},
        }
      }
    }
  }

#
# This section defines environment-specific configuration for different environments.
# You can define nowermal Serenity properties, such as webdriver.base.url, or custom ones
# You can find more details about this feature at https://johnfergusonsmart.com/environment-specific-configuration-in-serenity-bdd/
#

environments {
  default {
    webdriver.base.url = "https://www.saucedemo.com/v1/index.html"
  }
}

serenity {
  encoding = "UTF-8"
  compress.filenames = true
  take.screenshots = FOR_EACH_ACTION
}
```

Dentro de la carpeta resources crear el archivo logback-test.xml con la siguiente estructura

```bash
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>
                %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
            </pattern>
        </encoder>
    </appender>

    <logger name="root" level="WARN"/>
    <logger name="net.serenitybdd" level="INFO"/>
    <logger name="net.thucydides" level="INFO"/>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>

</configuration>
```
Dentro de la carpeta runners se crea una clase java con la siguiente estructura

```bash
Ej:
package co.com.saucelabs.web.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/login.feature",
        glue = "co.com.saucelabs.web.stepdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        tags = "@successful"
)
public class RunnerLogin {
}
```

Dentro de la carpeta features se crea el archivo .feature con la siguiente estructura

```bash
Ej:
Feature: Validate login the app Sauce Labs

  Background:
    Given The "user" open the web site

  @successful
  Scenario Outline: Validate login with valid credentials
    And The "user" send his credentials user "<userName>" and password "<password>"
    When He clicks on the login button
    Then He should see the title "Products" with login is successful

    Examples:
      | userName      | password     |
      | standard_user | secret_sauce |
```

Dentro de la carpeta stepdefinitions/hook crear la clase java Hook con la siguiente estructura

```bash
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

```

Dentro de la carpeta main/java/.../hook crear la clase java OpenWeb con la siguiente estructura

```bash
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
```

Dentro de la carpeta stepdefinitions se crea la clase java de los pasos con la siguiente estructura

```bash
Ej:
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
```

Dentro de la carpeta ui crear la clase java para localizar los elementos con la siguiente estructura

```bash
Ej:
package co.com.saucelabs.web.ui;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {

    public static final Target ERROR_TEXT = Target.the(
            "Text").locatedBy("//*[@id='login_button_container']/div/form/h3");

    public static final Target LOGIN_BUTTON = Target.the(
            "Login").locatedBy("[id='login-button']");

    public static final Target PASSWORD_TEXT = Target.the(
            "Password").locatedBy("[id='password']");

    public static final Target USERNAME_TEXT = Target.the(
            "Username").locatedBy("[id='user-name']");
}
```

Dentro de la carpeta tasks crear la clase java con las tareas con la siguiente estructura

```bash
Ej:
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
```

Dentro de la carpeta questions crear la clase java para las validaciones con la siguiente estructura

```bash
Ej:
package co.com.saucelabs.web.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class TextEqual implements Question<String> {

    private final Target target;

    public TextEqual(Target target) {
        this.target = target;
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(target).answeredBy(actor);
    }

    public static TextEqual textEqual(Target target) {
        return new TextEqual(target);
    }
}
```

Crear los archivos necesarios segun el proyecto

```bash
    utils, models, etc
```

Pasos de como realice el proyecto

```bash
    Abrir IntelliJ y crear un proyecto Java y Gradle
```


## Ejecutar Tests

Por comando gradle

```bash
  gradle clean test
```

Desde la clase java del runners

```bash
  IntelliJ: clic derecho - Run 'nombre de la clase java'
```

## Authors

- Jorge Franco

