package com.szyoo.draw4cosme.service;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyoo.draw4cosme.entity.Present;
import com.szyoo.draw4cosme.exception.ElementNotFoundException;
import com.szyoo.draw4cosme.pages.PresentListPage;
import com.szyoo.draw4cosme.pages.PresentListBrandFanClubPage;
import com.szyoo.draw4cosme.pages.PresentListNormalPage;
import com.szyoo.draw4cosme.repository.PresentRepository;

@Service  // 声明这个类是一个 Spring Service
public class PresentService {

    // 用于网页爬取的 WebDriver 实例
    private final WebDriver driver;
    // 管理 WebDriver 实例的服务
    private final DriverService driverService;
    // 要从中爬取礼物的页面
    private final PresentListNormalPage presentListNormalPage;
    private final PresentListBrandFanClubPage presentListBrandFanClubPage;
    // 用于访问数据库的仓库
    private final PresentRepository presentRepository;

    @Autowired
    public PresentService(DriverService driverService, 
                          PresentListNormalPage presentListNormalPage,
                          PresentListBrandFanClubPage presentListBrandFanClubPage, 
                          PresentRepository presentRepository) {
        // 初始化变量
        this.driver = driverService.getDriver();
        this.driverService = driverService;
        this.presentListNormalPage = presentListNormalPage;
        this.presentListBrandFanClubPage = presentListBrandFanClubPage;
        this.presentRepository = presentRepository;
    }

    // 获取所有奖品总数
    public long getTotalPresents() {
        return presentRepository.count();
    }

    // 获取已抽取的奖品总数
    public long getTotalDrawn() {
        return presentRepository.countByIsDrawn(true);
    }

    // 添加元素到奖品列表
    public static void addElement(List<WebElement> elements, List<Present> presents) {
        for (WebElement element : elements) {
            String link = element.getAttribute("href");
            String presentName = element.getText();
            Present present = new Present(link, presentName);
            presents.add(present);
        }
    }

    // 搜索奖品并添加到列表
    public List<Present> searchPresentToList() {
        System.out.println("开始识别奖品...\n请稍等...");

        List<Present> presents = new ArrayList<>();
        try {
            // 试图从普通奖品列表获取奖品
            presents.addAll(getPresentsFromPage(presentListNormalPage));
            // 试图从品牌粉丝俱乐部奖品列表获取奖品
            presents.addAll(getPresentsFromPage(presentListBrandFanClubPage));
        } catch (ElementNotFoundException e) {
            System.out.println("在获取奖品时发生错误: " + e.getMessage());
        }

        System.out.println("识别完成！共识别到奖品：" + presents.size() + " 个");

        return presents;
    }

    // 从指定的页面中获取奖品列表
    private List<Present> getPresentsFromPage(PresentListPage page) throws ElementNotFoundException {
        // 访问指定的网页
        driver.get(page.getUrl());
        // 关闭其他窗口
        driverService.closeOtherWindow();
        // 获取该页面的奖品
        return page.getPresents();
    }
}
