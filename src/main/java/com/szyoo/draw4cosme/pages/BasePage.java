package com.szyoo.draw4cosme.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.szyoo.draw4cosme.exception.ElementNotFoundException;

import java.util.List;

public class BasePage {
    protected WebDriver driver; // WebDriver 实例，用于浏览器操作

    // 构造函数
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // 通过 Css 查找元素，成功返回 WebElement 对象，失败抛出 ElementNotFoundException
    protected WebElement findElement(String cssSelector, String elementDescription) throws ElementNotFoundException {
        WebElement element;
        try {
            element = driver.findElement(By.cssSelector(cssSelector));
        } catch (Exception e) {
            throw new ElementNotFoundException("未找到元素: " + elementDescription, e);
        }
        return element;
    }

    // 通过 Css 数组查找元素，成功返回 WebElement 对象，失败抛出 ElementNotFoundException
    protected WebElement findElement(String[] cssSelectors, String elementDescription) throws ElementNotFoundException {
        for (String cssSelector : cssSelectors) {
            try {
                return driver.findElement(By.cssSelector(cssSelector));
            } catch (Exception ignored) {
            }
        }
        throw new ElementNotFoundException("未找到元素: " + elementDescription);
    }

    // 通过 Css 查找元素，返回包含所有匹配元素的 List
    // 如果没有找到任何元素，则抛出 ElementNotFoundException
    protected List<WebElement> findElements(String cssSelector, String elementDescription)
            throws ElementNotFoundException {
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        if (elements.isEmpty()) {
            throw new ElementNotFoundException("未找到元素: " + elementDescription);
        }
        return elements;
    }

    protected List<WebElement> findElements(String[] cssSelectors, String elementDescription)
            throws ElementNotFoundException {
        for (String cssSelector : cssSelectors) {
            List<WebElement> elements;
            try {
                elements = driver.findElements(By.cssSelector(cssSelector));
            } catch (Exception ignored) {
                continue;
            }
            if (!elements.isEmpty()) {
                return elements;
            }
        }
        throw new ElementNotFoundException("未找到元素: " + elementDescription);
    }

    // 在给定的元素上通过 Css 查找子元素，成功返回 WebElement 对象，失败抛出 ElementNotFoundException
    protected WebElement findElement(WebElement parentElement, String cssSelector, String elementDescription)
            throws ElementNotFoundException {
        WebElement element;
        try {
            element = parentElement.findElement(By.cssSelector(cssSelector));
        } catch (Exception e) {
            throw new ElementNotFoundException("未找到元素: " + elementDescription, e);
        }
        return element;
    }

}
