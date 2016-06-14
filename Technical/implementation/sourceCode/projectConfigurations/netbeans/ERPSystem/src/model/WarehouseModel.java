/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import db.ERPSystemDB;
import entities.RawMaterial;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author memam
 */
public class WarehouseModel {

    private Map<Integer, RawMaterial> materials;
    private ERPSystemDB dbInstance = null;

    public WarehouseModel() throws SQLException {
        dbInstance = ERPSystemDB.getInstance();
        materials = dbInstance.getMaterials();
    }

    public Collection<RawMaterial> getMaterials() {
        return materials.values();
    }

    public Set<Integer> getMaterialsCode() {
        return materials.keySet();
    }

    public int addNewMaterial(String name, String desc, int qty, int price) {
        return 1;
    }

    public RawMaterial addExtraQty(int id, int qty) throws SQLException {
        RawMaterial material = materials.get(id);
        material.addQty(qty);

        if (dbInstance.updateMaterialQty(material)) {
            return material;
        } else {
            return null;
        }
    }

    public RawMaterial withdrawSomeMaterial(int id, int qty) throws SQLException {
        RawMaterial material = materials.get(id);

        if (qty <= material.getQty()) {
            material.addQty(qty * -1);
            if (dbInstance.updateMaterialQty(material)) {
                return material;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
