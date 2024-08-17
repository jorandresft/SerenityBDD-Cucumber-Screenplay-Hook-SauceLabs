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
