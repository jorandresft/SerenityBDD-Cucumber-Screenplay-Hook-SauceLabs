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
