package se.telenor.assignment.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import se.telenor.assignment.api.Application;
import se.telenor.assignment.api.model.Product;
import se.telenor.assignment.api.model.ProductModel;
import se.telenor.assignment.api.service.DataLoadService;
import se.telenor.assignment.api.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneSubscriptionTest {

    private static List<Product> productList = new ArrayList<>();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataLoadService dataLoadService;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Before
    public void cleanUpDb(){
        addSomeProducts();
    }

    @Test
    public void testGetAllProducts() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        ProductModel productModel = new ProductModel();
        HttpEntity<ProductModel> request = new HttpEntity<>(productModel, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/product", HttpMethod.GET, request, String.class);
        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Gson g = new Gson();
        Product[] p = g.fromJson(newValue, Product[].class);
        System.out.println("Product length----- :: "+p.length);
        System.out.println("Product string----- :: "+p[0].toString());

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(p.length,  productList.size());
    }

    @Test
    public void testGetAllProductsFilteredByType() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/product?type=phone", HttpMethod.GET, request, String.class);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
//        Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getType().equals("phone")).count());
    }


    public void addSomeProducts(){
        productList = new ArrayList<>();
        productList.add(new Product(null, "phone", "green", null, 250.50, "Kungsgatan", "Stockholm"));
        productList.add(new Product(null, "phone", "blue", null, 300.00, "Kungsgatan", "Stockholm"));
        productList.add(new Product(null, "phone", "white", null, 300.00, "Kungsgatan", "Stockholm"));
        productList.add(new Product(null, "subscription", null, 10.0, 100.50, "Gamlastan", "Gothenburg"));
        productList.add(new Product(null, "subscription", null, 20.0, 50.50, "Gamlastan", "Linköping"));
        productList.add(new Product(null, "subscription", null, 100.0, 100.50, "Gamlastan", "Stockholm"));
        dataLoadService.saveProducts(productList);
    }

}
