package co.com.saucelabs.web.ui;

import net.serenitybdd.screenplay.targets.Target;

public class ProductsPage {

    public static final Target TEXT_ELEMENT = Target.the(
            "Text").locatedBy("//*[@id='inventory_filter_container']/div");
}
