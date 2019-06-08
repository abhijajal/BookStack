package controllers;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.h2.command.dml.Select;
import play.*;
import play.db.DB;
import play.db.jpa.Model;
import play.mvc.*;

import java.sql.*;
import java.util.*;

import models.*;

import javax.sql.DataSource;

public class Application extends Controller {
public  static String data="";
public static String order="";
public static int total=0;
    public static void index() {
        render();
    }

    public static void placeOrder() throws java.sql.SQLException {
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
        forwardBookName fD = new forwardBookName(data);


    }

    public static void decision(String bookNames, String hidden) throws java.sql.SQLException {


        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        int price;
        String selectPrice = "select Book_Price from BookSList where Book_Name='" + bookNames + "'";
        ResultSet resultSet = stmt.executeQuery(selectPrice);
        resultSet.next();
        price = resultSet.getInt("Book_Price");

        if (hidden.equals("ConfirmOrder")) {
            checkOut(bookNames,price);

        } else if (hidden.equals("AddAnotherBooks")) {
            addAnotherBook(bookNames,price);
        }
    }
    public static void checkOut(String bookNames,int price) throws  java.sql.SQLException{
        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        int x=0;
        if(order=="") {
            int quantity;
            x=1;
            String select = "select Book_Quantity from BooksList where Book_Name='" + bookNames + "'";
            ResultSet resultSet = stmt.executeQuery(select);
            resultSet.next();
            quantity = resultSet.getInt("Book_Quantity");
            String selectPrice = "select Book_Price from BookSList where Book_Name='" + bookNames + "'";
            resultSet = stmt.executeQuery(selectPrice);
            resultSet.next();
            price = resultSet.getInt("Book_Price");
            if (quantity > 1) {
                quantity = quantity - 1;

                String update = "UPDATE BooksList SET Book_Quantity=" + quantity + " WHERE Book_Name='" + bookNames + "'";
                stmt.executeUpdate(update);
            } else if (quantity == 1) {
                stmt.execute("DELETE FROM BooksList WHERE Book_Name='" + bookNames + "'");
            }
            order=order+bookNames+"|"+price+"|";
            forwardOrder fo=new forwardOrder(order,price,x);
        }
        else
        {
            x=2;
            forwardOrder fo=new forwardOrder(order,total,x);
        }
    }

    public static void purchase(String order,int price,int x)
    {
        render(order,price,x);
    }

    public static void addAnotherBook(String bookNames,int price) throws SQLException {
        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();


        String select = "select Book_Quantity from BooksList where Book_Name='" + bookNames + "'";
        ResultSet resultSet = stmt.executeQuery(select);
        resultSet.next();
        int quantity = resultSet.getInt("Book_Quantity");

        if (quantity > 1) {
            quantity = quantity - 1;

            String update = "UPDATE BooksList SET Book_Quantity=" + quantity + " WHERE Book_Name='" + bookNames + "'";
            stmt.executeUpdate(update);
        } else if (quantity == 1) {
            stmt.execute("DELETE FROM BooksList WHERE Book_Name='" + bookNames + "'");
        }
        order=order+bookNames+"|"+price+"|";

        total=total+price;

        recursionAddBook m=new recursionAddBook(bookNames,price,data,order,total,2);


    }
    public  static void renderAnotherBook(String bookNames,int price,String data,String order,int total)
    {

        render(bookNames,price,data,order,total);

    }
    public static void orderPlaced(String data)
    {
        order="";
        total=0;
        render(data);

    }

    public static void checkInventory()
    {
        render();
    }

    public static void addBooks()
    {
        render();
    }

    public static void searchBook(){
        render();
    }
    public static void searchPublisher(){
        render();
    }
    public static void searchQuantity(){
        render();
    }

    public static void ByQuantity(String sel,int Qty) throws SQLException {
        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        String select;
        if(sel.equals("Greater Than")) {
            select = "select * from BooksList where Book_Quantity >"+Qty;
        }
        else {
            select = "select * from BooksList where Book_Quantity <"+Qty;
        }
        ResultSet resultSet = stmt.executeQuery(select);
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
        int noOfCols=resultSetMetaData.getColumnCount();
        StringBuilder arr = new StringBuilder();
        String bookName, bookPublisher;
        int bookQuantity,bookPrice;
        while (resultSet.next()) {
            bookName = resultSet.getString("Book_Name");
            bookPublisher = resultSet.getString("Book_Publisher");
            bookQuantity = resultSet.getInt("Book_Quantity");
            bookPrice=resultSet.getInt("Book_Price");
            arr.append(bookName + "|");
            arr.append(bookPublisher + "|");
            arr.append(bookQuantity + "|");
            arr.append(bookPrice+"|");
        }

        String data=arr.toString();
        forwardQauntity fQ=new forwardQauntity(data,noOfCols);

    }
    public static void dispByQuantity(String data,int noOfCols){
        render(data,noOfCols);
    }

    public static void ByPublisher(String publisher) throws SQLException {
        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        String select= "select * from BooksList where Book_Publisher ='"+publisher+"'";
        ResultSet resultSet = stmt.executeQuery(select);
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
        int noOfCols=resultSetMetaData.getColumnCount();
        StringBuilder arr = new StringBuilder();
        String bookName, bookPublisher;
        int bookQuantity,bookPrice;
        while (resultSet.next()) {
            bookName = resultSet.getString("Book_Name");
            bookPublisher = resultSet.getString("Book_Publisher");
            bookQuantity = resultSet.getInt("Book_Quantity");
            bookPrice=resultSet.getInt("Book_Price");
            arr.append(bookName + "|");
            arr.append(bookPublisher + "|");
            arr.append(bookQuantity + "|");
            arr.append(bookPrice+"|");
        }

        String data=arr.toString();
        forwardPublisher fQ=new forwardPublisher(data,noOfCols);

    }
    public static void dispByPublisher(String data,int noOfCols){
        render(data,noOfCols);
    }

    public static void viewBook() throws java.sql.SQLException {
        DataSource dataSource = (DataSource) DB.getDataSource();
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        String select = "select * from BooksList order by Book_Name";
        ResultSet resultSet = stmt.executeQuery(select);
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
        int noOfCols=resultSetMetaData.getColumnCount();
        StringBuilder arr = new StringBuilder();
        String bookName, bookPublisher;
        int bookQuantity,bookPrice;
        while (resultSet.next()) {
            bookName = resultSet.getString("Book_Name");
            bookPublisher = resultSet.getString("Book_Publisher");
            bookQuantity = resultSet.getInt("Book_Quantity");
            bookPrice=resultSet.getInt("Book_Price");
            arr.append(bookName + "|");
            arr.append(bookPublisher + "|");
            arr.append(bookQuantity + "|");
            arr.append(bookPrice+"|");
       }

       String data=arr.toString();
        forwardData fD=new forwardData(data,noOfCols);
    }
    public static void viewBooks(String data,int noOfCols)
    {
        render(data,noOfCols);
    }
    public static void addBookToDB(String bookName,String bookPublisher,int bookQuantity,int bookPrice) throws java.sql.SQLException
    {
        DataSource data= (DataSource) DB.getDataSource();
        Connection conn=  DB.getConnection();
        Statement stmt=conn.createStatement();
        String status;
        int quantity=0;
        String tableCreate="create table if not exists BooksList(Book_Name varchar(255) NOT NULL, Book_Publisher varchar(255), Book_Quantity int NOT NULL, Book_Price int NOT NULL, PRIMARY KEY (Book_Name));";
        stmt.execute(tableCreate);
        String insertionQuery="INSERT INTO BooksList values ('"+bookName+"','"+bookPublisher+"',"+bookQuantity+","+bookPrice+");";
        try {
            stmt.execute(insertionQuery);
        }
        catch (Exception e){
            String select="select Book_Quantity from BookSList where Book_Name='"+bookName+"'";
            ResultSet resultSet=stmt.executeQuery(select);
            resultSet.next();
            quantity= resultSet.getInt("Book_Quantity");
            quantity=quantity+bookQuantity;

            String update="UPDATE BooksList SET Book_Quantity="+quantity+" WHERE Book_Name='"+bookName+"'";
            stmt.executeUpdate(update);
            String updatePrice="UPDATE BooksList SET Book_Price="+bookPrice+" WHERE Book_Name='"+bookName+"'";
            stmt.executeUpdate(updatePrice);

        }
        render(bookName,bookPublisher,bookQuantity,quantity);
    }

}