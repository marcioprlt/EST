/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erp_system;

import java.util.Date;

/**
 *
 * @author Marcio
 */
public class Stock {
    
    private Date date;
    private int quantity;

    public Stock(Date date, int quantity) {
        this.date = date;
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }
    
}
