package utils;

import java.sql.Connection;             // for connection
import java.sql.DriverManager;          // for connection
import java.sql.PreparedStatement;      // for queries execution
import java.sql.ResultSet;              //for return type
import java.sql.SQLException;           // for catching exception
import java.sql.Statement;
import java.util.List;    


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
    
    public ResultSet getIdsAndFilenames () throws SQLException {
    	String sql = "Select id, fileName from pages;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        return rs;
    }

    public ResultSet getIndex() throws SQLException {
    	String sql = "Select * from indexer;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        return rs;
    }

    public ResultSet getUnindexed() throws SQLException {
    	String sql = "Select * from pages where indexed=0;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        return rs;
    }

    public int getPagesCount() throws SQLException {
    	String sql = "Select count(*) from pages;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getIndexCount() throws SQLException {
    	String sql = "Select count(*) from indexer;";
    	PreparedStatement query = connection.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    
    public int insertPage(String fileName ,String url) throws SQLException {
    	return this.modifyDb("Insert into pages (fileName,url,indexed) values ('"+fileName+"','"+url+"', "+false+");");
    }

    public int setIndexed(int id) throws SQLException {
    	return this.modifyDb("Update pages set indexed=1 where id="+id+";");
    }

    public int insertIndexEntry(Integer docId, String word, List<Integer> indexInfo) throws SQLException {
    	Integer total = indexInfo.get(Constants.TOTAL);
        Integer title = indexInfo.get(Constants.TITLE);
        Integer h1 = indexInfo.get(Constants.H1);
        Integer h2 = indexInfo.get(Constants.H2);
        Integer h3 = indexInfo.get(Constants.H3);
        Integer h4 = indexInfo.get(Constants.H4);
        Integer h5 = indexInfo.get(Constants.H5);
        Integer normal = indexInfo.get(Constants.NORMAL);
        Integer wordCount = indexInfo.get(Constants.WORD_COUNT);
        String sql = "Insert into indexer (docId,word,total,title,h1,h2,h3,h4,h5,normal,doc_word_count) values ("+docId+",'"+word+"',"+total+","+title+","+h1+","+h2+","+h3+","+h4+","+h5+","+normal+", "+wordCount+");";
    	return this.modifyDb(sql);
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













