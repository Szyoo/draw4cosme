package com.szyoo.draw4cosme.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

class DriverUpdateServiceTest {
    DriverUpdateService service = new DriverUpdateService();
    String expectedLatestVersion = "113.0.5672.63";
    String fileUrl = "src/main/resources/driver/chromedriver.exe";

    @Test
    void testGetCurrentVersion() {
        try {
            String currentVersion = service.getCurrentVersion();
            assertEquals("0", currentVersion);
        } catch (IOException e) {
            fail("An IOException was thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetLatestVersion() {
        String latestVersion = service.getLatestVersion();
        assertEquals(expectedLatestVersion, latestVersion);
    }

    @Test
    void testDownloadLatestVersion() {
        service.downloadLatestVersion();
        File driverFile = new File(fileUrl);
        assertTrue(driverFile.exists(), "The driver file should exist after downloading");

        try {
            String currentVersion = service.getCurrentVersion();
            assertEquals(expectedLatestVersion, currentVersion,
                    "The current version should be updated after downloading");
        } catch (IOException e) {
            fail("An IOException was thrown: " + e.getMessage());
        }
    }
}
