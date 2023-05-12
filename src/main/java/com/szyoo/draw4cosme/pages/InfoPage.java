package com.szyoo.draw4cosme.pages;

import com.szyoo.draw4cosme.exception.ElementNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InfoPage extends BasePage {

    private static final String[] URL_INFO_PAGE_A = {
            "div.block-info>div.inner>div.box-present>div.col-wrap div.col>div.txtset div.btn a.gatrack",
            "div.block-info + div.box-image>a.gatrack",
            "div.bg-area div.present li.btn-cosme>a.gatrack",
            "#main>#section>.content>.apply>input.btn-green",
            "#presentInfo>div.detail>p.apply>a",
            "#brd-psnt>div.brd-psnt-frm>div.brd-psnt-frm-inr>a"
    };

    private static final String[] URL_INFO_PAGE_IMG = {
            "div.block-info>div.inner>div.box-present>div.col-wrap div.col>p img[src]",
            "div.box-image>div.clearfix>div.fl-none>img",
            "div.bg-area div.present>img",
            "#main>#section>.photo>img",
            "#presentInfo>p.img>img",
            "#brd-psnt>div.brd-psnt-frm>div.brd-psnt-frm-inr>a img"
    };

    // produce member応募済み灰色按钮
    private static String URL_PRODUCE_MEMBER_APPLIED = "#main>#section>.content>p.apply-after";

    public InfoPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getStartButton() throws ElementNotFoundException {
            return findElement(URL_INFO_PAGE_A, "抽奖介绍界面的开始抽奖按钮");
    }

    public WebElement getImage() throws ElementNotFoundException {
            return findElement(URL_INFO_PAGE_IMG, "抽奖介绍界面的图片");
    }

    public WebElement getProduceMemberApplied() throws ElementNotFoundException {
            return findElement(URL_PRODUCE_MEMBER_APPLIED, "ProduceMember応募済み灰色按钮");
    }
}
