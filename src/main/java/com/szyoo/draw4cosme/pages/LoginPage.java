package com.szyoo.draw4cosme.pages;

import com.szyoo.draw4cosme.exception.ElementNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    
    private static final String[] URL_LOGIN_FRAME = {
            "div.inr>div.TB_main .btn-cmn a", // 点击produce member时弹出的登录框按钮
            "div.usr-auth>ul>li:nth-child(2)>a",// 主界面下的登陆按钮
    };
    private static final String URL_MAIL = "form>ul input#LoginUserLoginId";
    private static final String URL_PASSWORD = "form>ul input#LoginUserPassword";
    private static final String URL_LOGIN_SUBMIT = "form>input.button-submit";

    // 构造函数
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getLoginFrame() throws ElementNotFoundException {
        return findElement(URL_LOGIN_FRAME, "登录弹出框");
    }

    public WebElement getMailInput() throws ElementNotFoundException {
        return findElement(URL_MAIL, "登录页面的邮箱输入框");
    }

    public WebElement getPasswordInput() throws ElementNotFoundException {
        return findElement(URL_PASSWORD, "登录页面的密码输入框");
    }

    public WebElement getSubmitButton() throws ElementNotFoundException {
        return findElement(URL_LOGIN_SUBMIT, "登录页面的登录按钮");
    }

}
