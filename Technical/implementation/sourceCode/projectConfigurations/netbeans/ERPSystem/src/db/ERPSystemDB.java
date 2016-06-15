/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.RawMaterial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author memam
 */
public class ERPSystemDB {

    private static ERPSystemDB instance;
    private final Connection conn;
    private final Statement stmt;
    private PreparedStatement addNewMaterialStmt = null;
    private final String addNewMaterialStr = "INSERT INTO RawMaterial (Name, Description, Quantity, Price)"
            + "VALUES (?, ?, ?, ?);";

    private ERPSystemDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ERPSystem?useSSL=false",
                "erpSystemAdmin", "erp_System_Admin1#");
        stmt = conn.createStatement();
        addNewMaterialStmt = conn.prepareStatement(addNewMaterialStr, Statement.RETURN_GENERATED_KEYS);
    }

    public static synchronized ERPSystemDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ERPSystemDB();
        }

        return instance;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        stmt.close();
        conn.close();
        System.out.println("Closed");
    }

    public Map<Integer, RawMaterial> getMaterials() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM RawMaterial;");

        Map<Integer, RawMaterial> materials = new LinkedHashMap<>();

        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("Name");
            String desc = rs.getString("Description");
            int qty = rs.getInt("Quantity");
            int price = rs.getInt("Price");

            RawMaterial material = new RawMaterial(id, name, desc, qty, price);
            materials.put(id, material);
        }

        return materials;
    }

    public boolean updateMaterialQty(RawMaterial material) throws SQLException {
        boolean updateStatus = false;
        if (stmt.executeUpdate("UPDATE RawMaterial SET Quantity=" + material.getQty() + " WHERE ID=" + material.getId() + ";") == 1) {
            updateStatus = true;
        }

        return updateStatus;
    }

    public int addNewMaterial(RawMaterial material) throws SQLException {
        addNewMaterialStmt.setString(1, material.getName());
        addNewMaterialStmt.setString(2, material.getDescription());
        addNewMaterialStmt.setInt(3, material.getQty());
        addNewMaterialStmt.setInt(4, (int) material.getPrice());
        
        addNewMaterialStmt.executeUpdate();
        
        ResultSet autoGeneratedKeys = addNewMaterialStmt.getGeneratedKeys();
        autoGeneratedKeys.next();
        return autoGeneratedKeys.getInt(1);
    }
    
    
    public static void main(String[] args) throws SQLException {
        new ERPSystemDB().getMaterials();
    }
}
