package com.szyoo.draw4cosme.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class DriverUpdateService {

    private static final String DRIVER_DIR = "src/main/resources/driver";
    private static final String DRIVER_FILE = DRIVER_DIR + "/chromedriver";
    private static final String VERSION_FILE = DRIVER_DIR + "/version.txt";

    // 获取当前版本
    public String getCurrentVersion() throws IOException {
        // 创建 File 对象，表示版本文件
        File versionFile = new File(VERSION_FILE);

        // 如果版本文件不存在
        if (!versionFile.exists()) {
            // 创建新的版本文件
            boolean fileCreated = versionFile.createNewFile();

            // 如果文件创建成功
            if (fileCreated) {
                // 使用 try-with-resources 结构自动关闭流
                try (FileWriter fileWriter = new FileWriter(versionFile);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                    // 写入默认的版本号 "0"
                    bufferedWriter.write("0");
                } catch (IOException e) {
                    // 处理写入错误
                    e.printStackTrace();
                }
            }
        }

        // 从版本文件中读取当前版本号
        String currentVersion = null;
        // 使用 try-with-resources 结构自动关闭流
        try (FileReader fileReader = new FileReader(versionFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            // 读取文件中的一行（即版本号）
            currentVersion = bufferedReader.readLine();
        } catch (IOException e) {
            // 处理读取错误
            e.printStackTrace();
        }

        // 返回当前版本号
        return currentVersion;
    }

    // 获取最新的ChromeDriver版本
    public String getLatestVersion() {
        // 最新的ChromeDriver版本号
        String latestVersion = null;

        try {
            // 使用Jsoup连接到ChromeDriver版本信息页面
            Document doc = Jsoup.connect("https://chromedriver.storage.googleapis.com/LATEST_RELEASE").get();

            // 从页面正文获取最新版本号
            latestVersion = doc.body().text();
        } catch (IOException e) {
            // 处理连接或解析错误
            e.printStackTrace();
        }

        // 返回最新版本号
        return latestVersion;
    }

    // 下载最新版本的ChromeDriver
    public void downloadLatestVersion() {
        int maxRetry = 3; // 最大重试次数
        int retry = 0;
        boolean success = false;

        while (!success && retry < maxRetry) {
            try {
                // 获取最新版本号
                String latestVersion = getLatestVersion();

                // 创建指向最新版本ChromeDriver的URL
                URL url = new URL(
                        "https://chromedriver.storage.googleapis.com/" + latestVersion + "/chromedriver_win32.zip");

                // 打开网络连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // 获取输入流
                InputStream in = connection.getInputStream();
                ZipInputStream zipIn = new ZipInputStream(in);

                // 解压缩文件
                ZipEntry entry;
                while ((entry = zipIn.getNextEntry()) != null) {
                    // Only extract chromedriver.exe
                    if (entry.getName().equals("chromedriver.exe")) {
                        FileOutputStream out = new FileOutputStream(DRIVER_FILE + ".exe");
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zipIn.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        out.close();
                    }
                }

                // 关闭连接和流
                zipIn.closeEntry();
                zipIn.close();
                in.close();
                connection.disconnect();

                // 更新版本文件
                Files.write(Paths.get(VERSION_FILE), latestVersion.getBytes());

                // 如果到达这里，说明没有发生错误，下载成功
                success = true;
            } catch (IOException e) {
                // 如果发生错误，打印错误信息并增加重试次数
                System.out.println("Failed to download the latest ChromeDriver, retrying...");
                retry++;
                if (retry == maxRetry) {
                    // 如果已经达到最大重试次数，记录错误信息
                    System.out.println("Failed to download the latest ChromeDriver after " + maxRetry + " attempts.");
                    e.printStackTrace();
                }
            }
        }
    }

    // 检查并更新ChromeDriver
    public void checkAndUpdateDriver() {
        try {
            // 获取当前版本号
            String currentVersion = getCurrentVersion();

            // 获取最新版本号
            String latestVersion = getLatestVersion();

            // 比较当前版本号和最新版本号
            // 如果版本号不同，说明有新版本可用
            if (!currentVersion.equals(latestVersion)) {
                // TODO: 在这里添加UI提示代码
                // 这是一个占位符，你需要在这里添加前端UI的代码
                // 这部分代码应该提示用户有新版本可用，并询问用户是否下载新版本
                // 如果用户同意下载新版本，那么就执行下面的downloadLatestVersion()方法

                // 下载并安装最新版本
                downloadLatestVersion();
            }
        } catch (IOException e) {
            // 如果在获取版本号或下载新版本的过程中发生错误，打印错误堆栈
            // 你可以根据需要添加更多的错误处理代码，比如记录错误信息，发送错误报告等
            e.printStackTrace();
        }
    }

}
