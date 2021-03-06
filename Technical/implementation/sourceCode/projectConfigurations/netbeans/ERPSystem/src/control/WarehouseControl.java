/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entities.RawMaterial;
import entities.WarehouseException;
import model.WarehouseModel;
import view.WarehouseView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author memam
 */
public class WarehouseControl {

    private final WarehouseView theView;
    private final WarehouseModel theModel;

    public WarehouseControl(WarehouseView theView, WarehouseModel theModel) {
        this.theView = theView;
        this.theModel = theModel;

        this.theView.addNewMaterialListener(new NewMaterialListener());
        this.theView.addExtraQtyListener(new ExtraQtyListener());
        this.theView.addWithdrawSomeMaterialListener(new WithdrawSomeMaterialListener());

        this.theModel.getMaterials().stream().forEach((material) -> {
            this.theView.addNewMaterial(material.getId(), material.getName(), material.getDescription(), material.getQty(), (int) material.getPrice());
        });
    }

    private class NewMaterialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = theView.getNewMaterialName();
            String desc = theView.getNewMaterialDesc();
            int qty = theView.getNewMaterialQty();
            int price = theView.getNewMaterialPrice();
                        
            if ((name != null && !name.equals("")) && desc != null) {
                if (qty >= 0 && price >= 0) {
                    try {
                        int id = theModel.addNewMaterial(name, desc, qty, price);
                        theView.addNewMaterial(id, name, desc, qty, price);
                    } catch (WarehouseException ex) {
                        Logger.getLogger(WarehouseControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    theView.warnUser("الكمية أو السعر يجب أن يكونوا موجبين");
                }
            } else {
                theView.warnUser("الأسم أو الوصف غير ممتلئين");
            }
        }
    }

    private class ExtraQtyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = theView.getExtraQtyId();
            int qty = theView.getExtraQtyQty();
            if (qty < 0) {
                theView.warnUser("القيمة المضافة يجب أن تكون موجبة");
            } else {
                try {
                    RawMaterial material = theModel.addExtraQty(id, qty);
                    if (material != null) {
                        theView.updateMaterialQty(material.getId(), material.getQty());
                    }
                } catch (WarehouseException ex) {
                    Logger.getLogger(WarehouseControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class WithdrawSomeMaterialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = theView.getWithdrawQtyId();
            int qty = theView.getWithdrawQtyQty();
            try {
                RawMaterial material = theModel.withdrawSomeMaterial(id, qty);
                if (material != null) {
                    theView.updateMaterialQty(material.getId(), material.getQty());
                } else {
                    theView.warnUser("الكمية المراد صرفها غير متوفرة!");
                }
            } catch (WarehouseException ex) {
                Logger.getLogger(WarehouseControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
