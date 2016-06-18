/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;

/**
 *
 * @author memam
 */
public class ERPSystemDB {

    protected Connection conn;
    protected static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/ERPSystem?useSSL=false&useUnicode=yes&characterEncoding=UTF-8";

    protected ERPSystemDB() {
        
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        conn.close();
    }
}
