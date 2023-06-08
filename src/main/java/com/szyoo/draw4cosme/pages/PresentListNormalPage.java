package com.szyoo.draw4cosme.pages;

import org.springframework.stereotype.Component;

import com.szyoo.draw4cosme.service.DriverService;

@Component
public class PresentListNormalPage extends PresentListPage {

    private static final String URL = "https://www.cosme.net/present";

    private static final String[] CSS_SELECTORS = {
            "a[href*='present/detail/present_id']",
            "a[href*='/as.iy.impact-ad.jp/ct?id=']"
    };

    public PresentListNormalPage(DriverService driverService) {
        super(driverService.getDriver());
    }

    @Override
    protected String[] getCssSelectors() {
        return CSS_SELECTORS;
    }

    @Override
    protected String getElementDescription() {
        return "抽奖介绍界面的开始抽奖按钮";
    }

    @Override
    public String getUrl() {
        return URL;
    }
}
