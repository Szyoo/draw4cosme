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

@Service
public class PresentService {

    private final WebDriver driver;
    private final DriverService driverService;
    private final PresentListNormalPage presentListNormalPage;
    private final PresentListBrandFanClubPage presentListBrandFanClubPage;
    private final PresentRepository presentRepository;

    @Autowired
    public PresentService(DriverService driverService, 
                          PresentListNormalPage presentListNormalPage,
                          PresentListBrandFanClubPage presentListBrandFanClubPage, 
                          PresentRepository presentRepository) {
        this.driver = driverService.getDriver();
        this.driverService = driverService;
        this.presentListNormalPage = presentListNormalPage;
        this.presentListBrandFanClubPage = presentListBrandFanClubPage;
        this.presentRepository = presentRepository;
    }

    public long getTotalPresents() {
        return presentRepository.count();
    }

    public long getTotalDrawn() {
        return presentRepository.countByIsDrawn(true);
    }

    public static void addElement(List<WebElement> elements, List<Present> presents) {
        for (WebElement element : elements) {
            String link = element.getAttribute("href");
            String presentName = element.getText();
            Present present = new Present(link, presentName);
            presents.add(present);
        }
    }

    public List<Present> searchPresentToList() {
        System.out.println("开始识别奖品...\n请稍等...");

        List<Present> presents = new ArrayList<>();
        try {
            // 尝试从普通奖品列表获取奖品
            presents.addAll(getPresentsFromPage(presentListNormalPage));
            // 尝试从品牌粉丝俱乐部奖品列表获取奖品
            presents.addAll(getPresentsFromPage(presentListBrandFanClubPage));
        } catch (ElementNotFoundException e) {
            System.out.println("在获取奖品时发生错误: " + e.getMessage());
        }

        System.out.println("识别完成！共识别到奖品：" + presents.size() + " 个");

        return presents;
    }

    // 从指定的页面中获取奖品列表
    private List<Present> getPresentsFromPage(PresentListPage page) throws ElementNotFoundException {
        driver.get(page.getUrl());
        driverService.closeOtherWindow();
        return page.getPresents();
    }

}
