package com.szyoo.draw4cosme.pages;

import com.szyoo.draw4cosme.exception.ElementNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InfoConfirmPage extends BasePage {

    private static final String URL_NEXT_BUTTON = "#main form>p.enquete-apl-btn>input.btn-green";

    public InfoConfirmPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getNextButton() throws ElementNotFoundException {
        return findElement(URL_NEXT_BUTTON, "个人信息确认页面的下一步按钮");
    }
}
