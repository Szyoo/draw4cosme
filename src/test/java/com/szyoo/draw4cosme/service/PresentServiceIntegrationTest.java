package com.szyoo.draw4cosme.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.szyoo.draw4cosme.entity.Present;

@SpringBootTest
public class PresentServiceIntegrationTest {

    @Autowired
    private PresentService presentService;

    @Test
    public void testSearchPresentToList() {
        List<Present> presents = presentService.searchPresentToList();

        for (Present present : presents) {
            System.out.println(present);
        }
    }
}
