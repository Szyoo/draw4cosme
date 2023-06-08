package com.szyoo.draw4cosme.service;

import org.springframework.stereotype.Service;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DriverService {

    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            driver = setDriver();
        }
        return driver;
    }

    /**
     * 创建并返回一个 WebDriver 实例。
     * <p>
     * 该方法尝试使用 ChromeDriver 创建一个 WebDriver 实例，同时应用一些通用选项，如无头模式。
     * 如果在创建过程中发生任何异常，将输出相应的错误信息。
     *
     * @return WebDriver 实例，如果发生异常则返回 null
     */
    private WebDriver setDriver() {
        WebDriver driver = null;

        String driverPath = getDriverPath();

        // 设置 ChromeDriver 的路径
        System.setProperty("webdriver.chrome.driver", driverPath);

        // 配置 ChromeOptions
        ChromeOptions options = new ChromeOptions();
        // 无头模式，浏览器在后台运行，不显示界面
        // options.addArguments("--headless");
        // 禁用沙箱模式，可能需要在某些环境（如 Docker 或某些 Linux 环境）中使用
        options.addArguments("--no-sandbox");
        // 禁止使用 /dev/shm，可以避免在某些系统中 /dev/shm 大小不足导致的问题
        options.addArguments("--disable-dev-shm-usage");

        try {
            // 尝试创建一个新的 ChromeDriver 实例
            driver = new ChromeDriver(options);
        } catch (IllegalStateException e) {
            System.out.println("Chrome Driver 的路径可能不正确。当前路径：" + driverPath);
        } catch (SessionNotCreatedException e) {
            System.out.println("无法创建 WebDriver 会话，可能是浏览器版本与驱动不兼容。");
        } catch (WebDriverException e) {
            System.out.println("无法启动新的Chrome实例。请确保没有其他Chrome实例正在运行，然后重试。");
        }

        return driver;
    }

    /**
     * 获取 WebDriver 驱动文件的路径。
     * <p>
     * 此方法首先尝试从类路径中获取驱动文件的路径。如果找到，则返回该路径。
     * 如果在类路径中找不到驱动文件（例如在打包为 JAR 文件后运行时），则返回 JAR 文件所在目录的路径。
     *
     * @return 驱动文件的路径。如果在类路径中找不到驱动文件，并且无法获取 JAR 文件所在目录的路径，则返回 null。
     */
    private String getDriverPath() {
        URL res = getClass().getClassLoader().getResource("driver/chromedriver.exe");
        String driverPath = null;
        if (res != null) {
            // 在 IDE 环境中，可以直接从类路径中获取文件路径
            driverPath = res.getPath();
        } else {
            // 在 JAR 环境中，需要获取 JAR 文件所在目录的路径
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            try {
                // 解码路径（路径可能包含 URL 编码的字符）
                String decodedPath = URLDecoder.decode(path, "UTF-8");
                // 获取 JAR 文件所在目录的路径
                driverPath = new File(decodedPath).getParentFile().getPath();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return driverPath;
    }

    /**
     * 关闭 WebDriver 实例中所有非当前窗口。
     * <p>
     * 此方法将切换到每一个非当前窗口，并关闭它。在此方法执行后，WebDriver 实例将只有一个窗口打开，
     * 并且此窗口将成为当前窗口。
     */
    public void closeOtherWindow() {
        String currentWindow = driver.getWindowHandle(); // 获取当前窗口的句柄
        Set<String> handles = driver.getWindowHandles(); // 获取所有窗口的句柄

        for (String handle : handles) {
            if (!handle.equals(currentWindow)) {
                driver.switchTo().window(handle); // 切换到非当前窗口
                driver.close(); // 关闭该窗口
            }
        }

        driver.switchTo().window(currentWindow); // 切回原来的窗口
    }

    /**
     * 切换到最新打开的窗口（最右侧的窗口）。
     */
    public void switchToLatestWindow() {
        // 获取所有窗口句柄
        Set<String> windowHandles = driver.getWindowHandles();

        // 转换为 List，因为 Set 结构不保证元素的顺序，而我们想获取最后一个元素
        List<String> windowHandlesList = new ArrayList<>(windowHandles);

        // 切换到最后一个窗口句柄（最新打开的窗口）
        driver.switchTo().window(windowHandlesList.get(windowHandlesList.size() - 1));
    }

}
