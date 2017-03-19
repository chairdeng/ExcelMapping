package com.jd.jr.data.excel.mapping.example;

import com.jd.jr.data.excel.mapping.SheetMapping;
import com.jd.jr.data.excel.mapping.SheetMappingHandler;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Created by dengc on 2017/3/18.
 */
public class ResultSetTest {
    public static void main(String[] args){
        try {
            System.out.println(ResultSetTest.class.getResource("/resultset-config.xml").getFile());
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url="jdbc:mysql://192.168.99.100:3306/test?user=root&password=root";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");
            SheetMapping sheetMapping = SheetMappingHandler.newInstance(new File("D:\\WorkDirectory\\java\\workspace\\ExcelMapping\\src\\test\\resources\\resultset-config.xml"));
            sheetMapping.write(resultSet,new File("D:\\excel.xlsx"));
            resultSet.close();
            statement.close();
            connection.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
