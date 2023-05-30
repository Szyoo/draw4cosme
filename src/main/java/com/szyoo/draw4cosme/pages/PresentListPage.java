package com.szyoo.draw4cosme.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.szyoo.draw4cosme.exception.ElementNotFoundException;
import com.szyoo.draw4cosme.entity.Present;

import java.util.ArrayList;
import java.util.List;

public abstract class PresentListPage extends BasePage {

    public PresentListPage(WebDriver driver) {
        super(driver);
    }

    // 抽象方法，由子类实现，提供页面链接
    public abstract String getUrl();

    // 抽象方法，由子类实现，提供 CSS 选择器
    protected abstract String[] getCssSelectors();

    // 抽象方法，由子类实现，提供元素描述
    protected abstract String getElementDescription();

    public List<Present> getPresents() throws ElementNotFoundException {
        List<WebElement> elements = findElements(getCssSelectors(), getElementDescription());
        List<Present> presents = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.getText().trim();
            if (text.isEmpty()) {
                WebElement titleElement;
                try {
                    titleElement = findElement(element, ".psnt-ttl", "奖品名称");
                } catch (ElementNotFoundException e) {
                    continue;
                }
                text = titleElement.getText().trim();
            }
            if (!text.isEmpty()) {
                presents.add(new Present(element.getAttribute("href"), text));
            }
        }
        return presents;
    }
}
