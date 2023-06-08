package com.szyoo.draw4cosme.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.szyoo.draw4cosme.entity.Present;
import com.szyoo.draw4cosme.pages.PresentListBrandFanClubPage;
import com.szyoo.draw4cosme.pages.PresentListNormalPage;
import com.szyoo.draw4cosme.repository.PresentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PresentServiceTest {
    @Autowired
    private DriverService driverService;
    @Autowired
    private PresentListNormalPage presentListNormalPage;
    @Autowired
    private PresentListBrandFanClubPage presentListBrandFanClubPage;
    @Autowired
    private PresentRepository presentRepository;

    @Test
    void searchPresentToList() {
        // 初始化DriverService并获取WebDriver实例
        this.driverService = new DriverService();
        this.presentListNormalPage = new PresentListNormalPage(driverService);
        this.presentListBrandFanClubPage = new PresentListBrandFanClubPage(driverService);

        // 请在此处初始化PresentRepository
        // 由于这是一个真实的测试，你可能需要连接到真实的数据库

        PresentService presentService = new PresentService(driverService, presentListNormalPage, presentListBrandFanClubPage, presentRepository);

        // 在真实环境下运行searchPresentToList方法
        List<Present> presents = presentService.searchPresentToList();

        // 打印出爬取到的奖品列表
        for (Present present : presents) {
            System.out.println(present);
        }

        // 断言爬取到的奖品列表不为空
        assertFalse(presents.isEmpty());
        driverService.getDriver().quit();
    }
}
