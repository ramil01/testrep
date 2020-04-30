package endava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSql {

        private static String dbhost = "jdbc:sqlserver://localhost;databaseName=DB";
        private static String username = "sa";
        private static String password = "qwerty";
        private static Connection conn;


        public static Connection connectionDb() {
            try  {
                conn = DriverManager.getConnection(
                        dbhost, username, password);
            } catch (SQLException e) {
                System.out.println("Cannot create database connection");
                e.printStackTrace();
            } finally {
                return conn;
            }
        }
    }


