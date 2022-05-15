package utils;

import java.sql.Connection;             // for connection
import java.sql.DriverManager;          // for connection
import java.sql.PreparedStatement;      // for queries execution
import java.sql.ResultSet;              //for return type
import java.sql.SQLException;           // for catching exception
import java.sql.Statement;    


public class DB implements  AutoCloseable {
	public Connection connection = null;
    PreparedStatement ps = null;

    //default class for connection creation
    public DB() throws SQLException, ClassNotFoundException {
        try {
        	//connect to sql server 
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String My_connection = Constants.DB_CONNECTION_STRING;
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
    
    public void clearDb() throws SQLException {
    	System.out.println("Cleared Db!");
    	this.modifyDb("Delete from pages;");
    }
    
    
    public boolean isUrlInDb(String url) throws SQLException {
    	String sql = "Select * from pages where url='"+url+"' ;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        return rs.next();
    }
    
    public int getPagesCount() throws SQLException {
    	String sql = "Select count(*) from pages;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    
    
    public int insertPage(String fileName ,String url) throws SQLException {
    	return this.modifyDb("Insert into pages (fileName,url) values ('"+fileName+"','"+url+"');");
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
    
    // free the allocated resources
    public void close() throws Exception {
    	connection.close();
    	System.out.println("Connection with DB closed !");
    }
}













