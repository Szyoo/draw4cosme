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

    private final WebDriver driver;
    private final DriverService driverService;
    private final PresentListNormalPage presentListNormalPage;
    private final PresentListBrandFanClubPage presentListBrandFanClubPage;
    private final PresentRepository presentRepository;

    /**
     * 构造函数，使用依赖注入初始化变量
     *
     * @param driverService            提供WebDriver的服务类实例
     * @param presentListNormalPage    普通奖品列表页面实例
     * @param presentListBrandFanClubPage 品牌粉丝俱乐部奖品列表页面实例
     * @param presentRepository        操作数据库的仓库实例
     */
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

    /**
     * 获取数据库中所有奖品的数量
     *
     * @return 奖品总数
     */
    public long getTotalPresents() {
        return presentRepository.count();
    }

    /**
     * 获取数据库中所有已经被抽取的奖品的数量
     *
     * @return 已抽取的奖品总数
     */
    public long getTotalDrawn() {
        return presentRepository.countByIsDrawn(true);
    }

    /**
     * 将WebElement对象转化为Present对象，并加入到奖品列表中
     *
     * @param elements 待转化的WebElement对象列表
     * @param presents 存储Present对象的奖品列表
     */
    public static void addElement(List<WebElement> elements, List<Present> presents) {
        for (WebElement element : elements) {
            String link = element.getAttribute("href");
            String presentName = element.getText();
            Present present = new Present(link, presentName);
            presents.add(present);
        }
    }

    /**
     * 扫描两个奖品列表网页，并将获取的奖品加入到列表中
     *
     * @return 扫描到的奖品列表
     */
    public List<Present> searchPresentToList() {
        System.out.println("开始识别奖品...\n请稍等...");

        List<Present> presents = new ArrayList<>();
        try {
            presents.addAll(getPresentsFromPage(presentListNormalPage));
            presents.addAll(getPresentsFromPage(presentListBrandFanClubPage));
        } catch (ElementNotFoundException e) {
            System.out.println("在获取奖品时发生错误: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("在获取奖品时发生未知错误: " + e.getMessage());
        }

        System.out.println("识别完成！共识别到奖品：" + presents.size() + " 个");

        return presents;
    }

    /**
     * 从指定的页面中获取奖品列表
     *
     * @param page 要获取奖品的页面实例
     * @return 页面中的奖品列表
     * @throws ElementNotFoundException 如果在获取元素时发生任何异常，抛出此异常
     */
    private List<Present> getPresentsFromPage(PresentListPage page) throws ElementNotFoundException {
        try {
            driver.get(page.getUrl());
            driverService.closeOtherWindow();
            return page.getPresents();
        } catch (Exception e) {
            throw new ElementNotFoundException("在获取页面 " + page.getUrl() + " 的元素时发生错误", e);
        }
    }
}

