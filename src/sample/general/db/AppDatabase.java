package sample.general.db;


import java.sql.Connection;
import java.sql.DriverManager;

public class AppDatabase {

    private static Connection instance = null;

    public static Connection getInstance(){

        if(instance==null){
            String dbName = "crunchy_dinner";
            String userName = "root";
            String password = "";
            String url = "jdbc:mysql://localhost/"+dbName;


            try{
                Class.forName("com.mysql.cj.jdbc.Driver");

                instance = DriverManager.getConnection(url,userName,password);


            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else
            return instance;


        return instance;

    }

}
