
package com.mycompany.sequence_comparator2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gabriel BRUNET & Clement SAURY
 */
public class ConnectionDataBase {

    private Connection con;

    private Statement stmt;
    
    public ConnectionDataBase() throws ClassNotFoundException, SQLException{
        // Load the driver class & create the connection object then create the statement of the object
        try{
            Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://remotemysql.com:3306/jeho8etZte", "jeho8etZte", "GxTGVa1AvL");
        if (con==null){
            System.out.println("nope");
        }
        else{
            System.out.println("co ok");
            stmt = con.createStatement();
        }
        
        }
        catch(SQLException e){ 
        }
    }
    
    public void shutdown() throws SQLException {

    }
    
    public void shutdown(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }
    

    public Connection getCon() {
        return con;
    }
    
    public Statement getStatement(){
            return stmt;
    }

}
