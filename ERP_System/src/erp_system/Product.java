/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erp_system;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Marcio
 */
public class Product {
    
    private int id;
    private String name;
    private double price;
    private Queue<Stock> stockList;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        
        stockList = new LinkedList<Stock>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Queue<Stock> getStockList() {
        return stockList;
    }    
    
}
