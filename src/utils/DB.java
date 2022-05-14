package utils;

import java.sql.Connection;             // for connection
import java.sql.DriverManager;          // for connection
import java.sql.PreparedStatement;      // for queries execution
import java.sql.ResultSet;              //for return type
import java.sql.SQLException;           // for catching exception
import java.sql.Statement;    

import utils.Constants;

public class DB implements  AutoCloseable {
	public Connection connection = null;
    PreparedStatement ps = null;

    //default class for connection creation
    public DB() throws SQLException, ClassNotFoundException {
        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //connect to sql server 
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String My_connection = "jdbc:mysql://localhost:3306/searchengine";
            connection = DriverManager.getConnection(My_connection,Constants.DB_USERNAME,Constants.DB_PASSWORD);  // setup connection
            System.out.println("Connected To Database!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failed" + e);
        }
    }
    //this is for select statements (Read Only)
    public ResultSet selectDb(String sql) throws SQLException 
    {   
            PreparedStatement query = connection.prepareStatement(sql);
            ResultSet rs = query.executeQuery();
            return rs;
    }
    // This is for updating the database (create,update,destroy)
    public int modifyDb(String sql) throws SQLException 
    {
        try {
            Statement ps = connection.createStatement();
            ps.executeUpdate(sql);
            return 1;
        } catch (SQLException e) {
            //System.out.println(e);
            return 0;
        }
    }
    
//    @SuppressWarnings("deprecation")
//	@Override
//    protected void finalize() throws Throwable {
//        try {
//            if (connection != null || !connection.isClosed()) {
//                connection.close();
//            }
//        } finally {
//            super.finalize();
//        }
//    }
    public void close() throws Exception {
    	connection.close();
    	System.out.println("Connection with DB closed !");
    }
}













