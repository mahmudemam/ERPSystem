/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author memam
 */
public class WarehouseException extends ERPException {

    public WarehouseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarehouseException(String message) {
        super(message);
    }

    public WarehouseException(Throwable cause) {
        super(cause);
    }

    public WarehouseException() {
    }
    
}
