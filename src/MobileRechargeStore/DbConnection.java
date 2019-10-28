package MobileRechargeStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance = new DbConnection();

    private final String mySqlServer= "jdbc:mysql://localhost:3306/";
    private final String databaseName= "mobilerechargestore";
    private final String DbUserId= "root";
    private final String DbPassword= "Conan@1887Doyle";

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
            dbSeason= DriverManager.getConnection(mySqlServer+databaseName, DbUserId, DbPassword);
            System.out.println("Database is Connected");
        }catch(SQLException e){
            System.out.println("Cannot Connect to Database: "+e.getMessage());
        }
        return dbSeason;
    }
}