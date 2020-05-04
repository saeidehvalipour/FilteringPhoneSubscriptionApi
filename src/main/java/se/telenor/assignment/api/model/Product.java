package se.telenor.assignment.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @JsonIgnore
    private String color;
    private Double gbLimit;
    private Double price;
    private String address;
    private String city;

    public Product(){}

    public Product(Long id, String type, String color, Double gbLimit, Double price, String address, String city) {
        this.id = id;
        this.type = type;

        this.color = color;
        this.gbLimit = gbLimit;
        this.price = price;
        this.address = address;
        this.city = city;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColor(String color) {
          this.color = color;
    }

    public void setGbLimit(Double gbLimit) {
        this.gbLimit = gbLimit;
    }

    @JsonProperty(value = "properties")
    public void setProperties(Double gbLimit) {
        this.gbLimit = gbLimit;
    }

    @JsonProperty(value = "properties")
    public String getProperties() {
        if (StringUtils.isEmpty(this.color))
            return "gb_limit:" + this.gbLimit;
        else if ("phone".equals(this.type)){
            return "color:" + this.color;
        }
            return "";
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty(value = "store_address")
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    @JsonIgnore
    public Double getGbLimit() {
        return gbLimit;
    }

    public Double getPrice() {
        return price;
    }

    @JsonProperty(value = "store_address")
    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

}
