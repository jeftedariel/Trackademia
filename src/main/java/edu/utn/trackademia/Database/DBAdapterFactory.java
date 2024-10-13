/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.Database;

/**
 *
 * @author jefte
 */

import edu.utn.trackademia.ConfigHandler;
import java.util.Properties;

public class DBAdapterFactory {
    private static final String DB_TYPE = "dbadaptertype";
    
    
    
    public static IDBAdapter getAdapter(){
        ConfigHandler ch = new ConfigHandler();
        
        try{
            return (IDBAdapter)Class.forName(ch.getDatabaseConfig().dbtype()).newInstance();
        } catch (Exception e){
            e.printStackTrace();
            return null;
            
        }
    }
    
    
}
