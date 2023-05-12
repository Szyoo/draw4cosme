package com.szyoo.draw4cosme.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.szyoo.draw4cosme.exception.ElementNotFoundException;

public class BasePage {
    protected WebDriver driver;

    // 构造函数
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // 通过Css查找元素，成功返回WebElement对象，失败抛出ElementNotFoundException
    protected WebElement findElement(String cssSelector, String elementDescription) throws ElementNotFoundException {
        WebElement element;
        try {
            element = driver.findElement(By.cssSelector(cssSelector));
        } catch (Exception e) {
            throw new ElementNotFoundException("未找到元素: " + elementDescription, e);
        }
        return element;
    }

    // 通过Css数组查找元素，成功返回WebElement对象，失败抛出ElementNotFoundException
    protected WebElement findElement(String[] cssSelectors, String elementDescription) throws ElementNotFoundException {
        for (String cssSelector : cssSelectors) {
            try {
                return driver.findElement(By.cssSelector(cssSelector));
            } catch (Exception ignored) {
            }
        }
        throw new ElementNotFoundException("未找到元素: " + elementDescription);
    }
}
