/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Main DB interaction class
 * @author mitola
 */

public class MainDBClass {
    Connection conn;

    /**
     * Init
     * @throws Exception
     */
    public MainDBClass() throws Exception {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:linkdatabase.db");
        createTables();
    }
    
    //delete from collected links and add to processed links

    /**
     * Moves entry from collected_links table to processed_links table
     * @param url
     * @param title
     * @throws Exception
     */
        public void moveEntryAfterParsing(String url,String title) throws Exception{
        Statement stat = conn.createStatement();
        
        //deleting from collected_links since it doesn't need to reparse it
        stat.executeUpdate("DELETE FROM collected_links WHERE url='"+ url +"';");
        
        stat.executeUpdate("INSERT OR IGNORE INTO processed_links VALUES('"+ url +"','"+ title +"');");
    }
    
    /**
     * Retrieves next url in collected_links table
     * @return
     * @throws Exception
     */
    public String[] getNextEntryForProcessing() throws Exception{
        Statement stat = conn.createStatement();
        
        //deleting from collected_links since it doesn't need to reparse it
        ResultSet rs = stat.executeQuery("SELECT url, title FROM collected_links LIMIT 1;");
        String[] resultStrings = new String[2];
        while (rs.next()) {
            System.out.println("url = " + rs.getString("url"));
            System.out.println("title = " + rs.getString("title"));
            resultStrings[0]=rs.getString("url"); 
            resultStrings[1]=rs.getString("title"); 
        }

        rs.close();
        
        return resultStrings;
    }
    
    /**
     * Prints all the entries in both tables
     * @throws Exception
     */
    public void getAllEntriesCollected() throws Exception{
        Statement stat = conn.createStatement();
        
        //deleting from collected_links since it doesn't need to reparse it
        ResultSet rs = stat.executeQuery("SELECT url, title FROM collected_links;");
        
        //Printing all entries from collected_links
        while (rs.next()) {
            System.out.println("url = " + rs.getString("url"));
        }
        
        rs = stat.executeQuery("SELECT url, title FROM processed_links;");
        
         System.out.println("TABLE PROCESSED LINKS");
        
        //Printing all entrie from processed_links
        while (rs.next()) {
            System.out.println("url = " + rs.getString("url"));
        }
        
        rs.close();
    }
    
    /**
     * Get number of entries in both tables of the DB
     * @throws Exception
     */
    public void getAllEntriesCollectedCount() throws Exception{
        Statement stat = conn.createStatement();
        
        //Print number of collected_links and number of processed_links
        ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM collected_links;");
        rs.next();
        System.out.println("Number of collected_links = " + rs.getInt(1));
        
        rs = stat.executeQuery("SELECT COUNT(*) FROM processed_links;");
        rs.next();
        System.out.println("Number of processed_links = " + rs.getInt(1));
        
        rs.close();
    }
    
    /**
     * Insert new entry with url and title
     * @param url
     * @param title
     * @throws Exception
     */
    public void insertNewEntry(String url, String title) throws Exception{
        Statement stat = conn.createStatement();
        stat.executeUpdate("INSERT OR IGNORE INTO collected_links VALUES ('"+url+"', '"+title+"');");
    }
    
    /**
     * Creates tables if they do not exist yet
     * @throws Exception
     */
    public void createTables() throws Exception{
        Statement stat = conn.createStatement();

        stat.executeUpdate("CREATE TABLE IF NOT EXISTS collected_links (url TEXT UNIQUE, title);");
        stat.executeUpdate("CREATE TABLE IF NOT EXISTS processed_links (url TEXT UNIQUE, title);");
        stat.executeUpdate("INSERT OR IGNORE INTO collected_links VALUES('http://bttstudio.com', 'Dummy link');");
    }
    
    /**
     * This function truncates both tables of DB.
     * First it drops them and after that recreates them
     * @throws Exception
     */
    public void truncateTables() throws Exception{
        Statement stat = conn.createStatement();
        
        //Building collected_links table
        stat.executeUpdate("DROP TABLE IF EXISTS collected_links;");
        stat.executeUpdate("DROP TABLE IF EXISTS processed_links;");
        stat.executeUpdate("CREATE table processed_links (url TEXT UNIQUE, title);");
        stat.executeUpdate("CREATE table collected_links (url TEXT UNIQUE, title);");
    }
  }