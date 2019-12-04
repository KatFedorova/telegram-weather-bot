package io.github.ITMO.weather_bot.bot;

import sun.plugin2.message.Message;

import javax.management.Query;
import java.sql.*;
import java.util.Scanner;

public class DBConnection {

    Connection connection;

    public void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:D:\\SQLite\\subscribers.db");
            System.out.println("Соединение с БД установлено");
        } catch (Exception e) {
            System.out.println("Ошибка подключения к БД");
        }
    }

    public void insert(String chatId, double latitude, double longitude) { //запись в бд
     //  Scanner scanner = new Scanner(System.in);
     //  System.out.println("Enter chatId: ");
     //  String chatId = scanner.nextLine();
     //  System.out.println("Enter lastLocation: ");
     //  String lastLocation = scanner.nextLine();
        String lat = Double.toString(latitude);
        String lon = Double.toString(longitude);

        String query = "INSERT INTO subscribers (chatId, latitude, longitude)\n" +
                "VALUES ('" + chatId + "', '" + lat + "', '" + lon + "');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка записи в БД");
        }
    }

    public void select() { //вывод из бд
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT chatId, latitude, longitude FROM subscribers;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int chatId = resultSet.getInt("chatId");
                String lastLocation = resultSet.getString("lastLocation");
                System.out.println(chatId +"\t|"+ lastLocation);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
