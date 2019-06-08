package models;

import controllers.Application;
import play.db.DB;
import play.db.jpa.Model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by abhij on 03/23/2017.
 */
public class recursionAddBook extends Model {

    public recursionAddBook(String bookNames, int price, String data, String order, int total, int x) throws SQLException {
        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        String select = "select Book_Name from BooksList";
        StringBuilder arr = new StringBuilder();
        String bookName;
        ResultSet resultSet = stmt.executeQuery(select);
        while (resultSet.next()) {
            bookName = resultSet.getString("Book_Name");
            arr.append(bookName + "|");
        }
        data = arr.toString();

        Application.renderAnotherBook(bookNames, price, data, order, total);


    }
}