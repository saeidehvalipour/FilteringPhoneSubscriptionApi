package se.telenor.assignment.api.model;

import lombok.Data;

@Data
public class ProductModel {
    private String type;
    private Double min_price;
    private Double max_price;
    private String city;
    private String store_address;
    private String color;
    private Double gb_limit_min;
    private Double gb_limit_max;
}
