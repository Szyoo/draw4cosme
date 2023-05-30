package com.szyoo.draw4cosme.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szyoo.draw4cosme.service.DriverService;

@Component
public class PresentListBrandFanClubPage extends PresentListPage {

    private static final String URL = "https://www.cosme.net/present";

    // CSS选择器数组
    private static final String[] CSS_SELECTORS = {
            "div[class=psnt]>ul[class=clearfix]>li>a[href*='cosme.net/brand/brand_id']" };

    @Autowired
    public PresentListBrandFanClubPage(DriverService driverService) {
        super(driverService.getDriver());
    }

    @Override
    protected String[] getCssSelectors() {
        return CSS_SELECTORS;
    }

    @Override
    protected String getElementDescription() {
        return "品牌粉丝俱乐部抽奖介绍界面的开始抽奖按钮";
    }

    @Override
    public String getUrl() {
        return URL;
    }
}
