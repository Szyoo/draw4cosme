package com.szyoo.draw4cosme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Draw4cosmeApplication {

	public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:file:./db/dbfile", "sa", "");
            // 这里没有做任何操作，只是打开和关闭连接
            connection.close();
        } catch (SQLException e) {
            System.out.println("无法连接到数据库");
            e.printStackTrace();
        }
		// SpringApplication.run(Draw4cosmeApplication.class, args);
	}

}
