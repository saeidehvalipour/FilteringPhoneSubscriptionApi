package se.telenor.assignment.api;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import se.telenor.assignment.api.model.Product;
import se.telenor.assignment.api.model.ProductModel;
import se.telenor.assignment.api.service.DataLoadService;
import se.telenor.assignment.api.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneSubscriptionTest {

    private static List<Product> productList = new ArrayList<>();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataLoadService dataLoadService;

    @Autowired
    private ProductService productService;

    @LocalServerPort
    private int port;

    private String getUrlWithPort() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Before
    public void cleanAndAddDb(){
        productService.deleteAllProducts();
        addProducts();
    }

    @Test
    public void testGetAllProducts() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        ProductModel productModel = new ProductModel();
        HttpEntity<ProductModel> request = new HttpEntity<>(productModel, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product", HttpMethod.GET, request, String.class);
        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        System.out.println("Product string----- :: "+products[0].toString());

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(products.length,  productList.size());
    }

    @Test
    public void testGetAllProductsFilteredByType() {

//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<?> request = new HttpEntity<>(null, headers);
//
//        ResponseEntity<String> response = restTemplate.getForEntity(getUrlWithPort() + "/product?type=phone", request, String.class);
//        Assert.assertNotNull(response.getBody());
//        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
//        Assert.assertEquals(response.getBody().size(),  productList.stream().filter(p -> p.getType().equals("phone")).count());
    }


    public void addProducts(){
        productList = new ArrayList<>();
        productList.add(new Product(null, "phone", "grön", null, 277.0, "Blake gränden", "Karlskrona"));
        productList.add(new Product(null, "phone", "guld", null, 45.0, "Gustafsson gärdet", "Malmö"));
        productList.add(new Product(null, "phone", "brun", null, 952.0, "Olsson allén", "Malmö"));
        productList.add(new Product(null, "subscription", null, 10.0, 334.0, "Candido gränden", "Malmö"));
        productList.add(new Product(null, "subscription", null, 10.0, 650.0, "Persson gärdet", "Karlskrona"));
        productList.add(new Product(null, "subscription", null, 50.0, 774.0, "Nilsson gatan", "Stockholm"));
        dataLoadService.saveProducts(productList);
    }

}
