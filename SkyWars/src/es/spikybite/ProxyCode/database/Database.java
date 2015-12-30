package es.spikybite.ProxyCode.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import es.spikybite.ProxyCode.Skywars;

public class Database {

	private String a;
	private String b;
	private String c;
	private String d;
	private String e;
    private Connection connection;
	public Database(String a, String b, String c, String d, String e){
	this.a = a;
	this.b = b;
	this.c = c;
	this.d = d;
	this.e  = e;
	}
	public Connection openConnection()
	  {
	    try
	    {
	      Class.forName("com.mysql.jdbc.Driver");
	      this.connection = DriverManager.getConnection("jdbc:mysql://" + this.a + ":" + this.b + "/" + this.c, this.d, this.e);
	    }
	    catch (SQLException e)
	    {
	      Skywars.pl.getLogger().log(Level.SEVERE, "Error when try connect to mysql because: " + e.getMessage());
	    }
	    catch (ClassNotFoundException e)
	    {
	      Skywars.pl.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
	    }
	    return this.connection;
	  }
	  
	  public boolean checkConnection()
	  {
	    return this.connection != null;
	  }
	  
	  public Connection getConnection()
	  {
	    return this.connection;
	  }
	  
	  
	  
	  public void closeConnection()
	  {
	    if (this.connection != null) {
	      try
	      {
	        this.connection.close();
	      }
	      catch (SQLException e)
	      {
	        Skywars.pl.getLogger().log(Level.SEVERE, "Error when closing the MySQL!");
	        e.printStackTrace();
	      }
	    }
	  }
	  
	
}
