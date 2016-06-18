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
public class ERPException extends java.lang.Exception {

    public ERPException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ERPException(String message) {
        super(message);
    }

    public ERPException(Throwable cause) {
        super(cause);
    }

    public ERPException() {
    }
}
