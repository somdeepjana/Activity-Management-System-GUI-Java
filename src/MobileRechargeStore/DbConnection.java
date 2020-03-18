package MobileRechargeStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance = new DbConnection();

    private final String databaseType= "jdbc:mysql://";
    private String serverLocation= "localhost";
    private String serverPort= "3306";
    private final String databaseName= "/mobilerechargestore";
    private String DbUserId= "root";
    private String DbPassword= "toor";

    private Connection dbSeason= null;

    private DbConnection(){}

    public static DbConnection getInstance(){
        return instance;
    }

    public Connection getConnection(){
        try{
            if(dbSeason!=null){
                dbSeason.close();
            }
            dbSeason= DriverManager.getConnection(databaseType+serverLocation+":"+serverPort+databaseName, DbUserId, DbPassword);
            System.out.println("Database is Connected");
        }catch(SQLException e){
            System.out.println("Cannot Connect to Database: "+e.getMessage());
        }
        return dbSeason;
    }

    public void setServerLocation(String serverLocation) {
        this.serverLocation = serverLocation;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setDbUserId(String dbUserId) {
        DbUserId = dbUserId;
    }

    public void setDbPassword(String dbPassword) {
        DbPassword = dbPassword;
    }
}
