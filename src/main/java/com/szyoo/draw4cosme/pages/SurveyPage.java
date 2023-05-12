package com.szyoo.draw4cosme.pages;

import com.szyoo.draw4cosme.exception.ElementNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SurveyPage extends BasePage {
    
    private static final String URL_SEND_BTN = "form>input[alt='送信']";

    public SurveyPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getSendButton() throws ElementNotFoundException {
        return findElement(URL_SEND_BTN, "问卷填写界面的送信按钮");
    }
}
