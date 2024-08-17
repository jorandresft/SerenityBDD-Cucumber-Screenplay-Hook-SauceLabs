package co.com.saucelabs.web.questions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.com.saucelabs.web.ui.ProductsPage.TEXT_ELEMENT;

@AllArgsConstructor
@Slf4j
public class ValidateElementText implements Question<Boolean> {

    private String validateText;

    @Override
    public Boolean answeredBy(Actor actor) {
        boolean result;

        String textElement = TEXT_ELEMENT.resolveFor(actor).getText();

        if (textElement != null && textElement.equals(validateText)) {
            log.info(validateText);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public static ValidateElementText validateElementText(String text) {
        return new ValidateElementText(text);
    }
}
