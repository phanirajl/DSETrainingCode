/**
 *
 */
package com.datastax.enablement.model.core.beans;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author matwater
 *
 */
public class ProductInventoryBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean onsale;
    private double price;
    private String product_id;
    private int qty_avail;
    private String sku;
    private String store_id;
    private String store_name;

    public double getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public int getQty_avail() {
        return qty_avail;
    }

    public String getSku() {
        return sku;
    }

    public String getStore_id() {
        return store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public boolean isOnsale() {
        return onsale;
    }

    public void setOnsale(boolean onsale) {
        this.onsale = onsale;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setQty_avail(int qty_avail) {
        this.qty_avail = qty_avail;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
}
