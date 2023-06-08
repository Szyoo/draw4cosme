package com.szyoo.draw4cosme.service;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;

public class DriverServiceTest {

    @Test
    public void testGetDriver() {
        // Given
        DriverService driverService = new DriverService();

        // When
        WebDriver driver = driverService.getDriver();

        // Then
        assertNotNull(driver);
        
        driverService.getDriver().quit();
    }
}
