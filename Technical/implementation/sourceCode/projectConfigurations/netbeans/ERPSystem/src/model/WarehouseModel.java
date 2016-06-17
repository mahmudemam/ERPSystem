/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import db.ERPSystemDB;
import entities.RawMaterial;
import entities.WarehouseException;
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

    public WarehouseModel() throws WarehouseException {
        try {
            dbInstance = ERPSystemDB.getInstance();
            materials = dbInstance.getMaterials();
        } catch (SQLException ex) {
            throw new WarehouseException("ErrorCode=" + ex.getErrorCode() + "; ErrorMessage: " + ex.getMessage(), ex.getCause());
        }
    }

    public Collection<RawMaterial> getMaterials() {
        return materials.values();
    }

    public Set<Integer> getMaterialsCode() {
        return materials.keySet();
    }

    public int addNewMaterial(String name, String desc, int qty, int price) throws WarehouseException {
        int materialID = 0;

        try {
            materialID = dbInstance.addNewMaterial(new RawMaterial(0, name, desc, qty, price));
            if (materialID > 0) {
                materials.put(materialID, new RawMaterial(materialID, name, desc, qty, price));
            }
        } catch (SQLException ex) {
            throw new WarehouseException("ErrorCode=" + ex.getErrorCode() + "; ErrorMessage: " + ex.getMessage(), ex.getCause());
        }

        return materialID;
    }

    public RawMaterial addExtraQty(int id, int qty) throws WarehouseException {
        RawMaterial material = materials.get(id);
        material.addQty(qty);

        try {
            if (dbInstance.updateMaterialQty(material)) {
                return material;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new WarehouseException("ErrorCode=" + ex.getErrorCode() + "; ErrorMessage: " + ex.getMessage(), ex.getCause());
        }
    }

    public RawMaterial withdrawSomeMaterial(int id, int qty) throws WarehouseException {
        RawMaterial material = materials.get(id);

        if (qty <= material.getQty()) {
            material.addQty(qty * -1);
            try {
                if (dbInstance.updateMaterialQty(material)) {
                    return material;
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                throw new WarehouseException("ErrorCode=" + ex.getErrorCode() + "; ErrorMessage: " + ex.getMessage(), ex.getCause());
            }
        } else {
            return null;
        }
    }
}
