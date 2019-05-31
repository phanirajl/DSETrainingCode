package com.datastax.enablement.model.core.beans;

import java.io.Serializable;
import java.util.UUID;

public class ProductSku implements Serializable {

    private static final long serialVersionUID = 1L;
    UUID product_id;
    String sku;
    String pkg_count;
    String sku_attributes;
    String sku_description;
    String sku_name;

    public String getPkg_count() {
        return pkg_count;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public String getSku() {
        return sku;
    }

    public String getSku_attributes() {
        return sku_attributes;
    }

    public String getSku_description() {
        return sku_description;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setPkg_count(String pkg_count) {
        this.pkg_count = pkg_count;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setSku_attributes(String sku_attributes) {
        this.sku_attributes = sku_attributes;
    }

    public void setSku_description(String sku_description) {
        this.sku_description = sku_description;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }
}
