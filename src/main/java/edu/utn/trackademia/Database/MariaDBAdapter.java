/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.Database;

import edu.utn.trackademia.ConfigHandler;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jefte
 */
public class MariaDBAdapter implements IDBAdapter {

    Connection connection = null;
    ConfigHandler ch = new ConfigHandler();
    
    static {
        try {
            new org.mariadb.jdbc.Driver();
        } catch (Exception e) {

        }
    }

    @Override
    public Connection getConnection() {
        try {
            this.connection = DriverManager.getConnection(this.getConnectionString(), ch.getDatabaseConfig().username(), ch.getDatabaseConfig().password());
            System.out.println("Connection class =>" + this.connection.getClass().getCanonicalName());

        } catch(Exception e){
            e.printStackTrace();
           
        } finally{
            return this.connection;
        }
    }
    
    @Override
    public void disconnect(){
        if(connection != null){
            try{
                connection.close();
                System.out.println("Connection Closed");
            } catch(Exception e){
                
                e.printStackTrace();
            }
        }
    }

    private String getConnectionString() {
        return "jdbc:mariadb://"+ch.getDatabaseConfig().host()+":"+ch.getDatabaseConfig().port()+"/"+ch.getDatabaseConfig().database();
    }

}
