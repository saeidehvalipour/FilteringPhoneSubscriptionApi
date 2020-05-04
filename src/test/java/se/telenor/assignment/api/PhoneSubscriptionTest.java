package se.telenor.assignment.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.telenor.assignment.api.model.Product;
import se.telenor.assignment.api.model.ProductModel;
import se.telenor.assignment.api.service.DataLoadService;
import se.telenor.assignment.api.service.ProductService;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneSubscriptionTest {

    private static List<Product> productList;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataLoadService dataLoadService;

    @Autowired
    private ProductService productService;
    @Autowired
    private WebApplicationContext webApplicationContext;

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
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(products.length,  productList.size());
    }

    @Test
    public void testGetAllProductsFilteredByType() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product?type=phone", HttpMethod.GET, request, String.class);

        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        System.out.println("Product string----- :: "+products[0].toString());
        Assert.assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(products.length,  productList.stream().filter(p -> p.getType().equals("phone")).count());
    }

    @Test
    public void testGetAllPhonesFilteredByGbMaximumLimit() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product?gb_limit_max=20", HttpMethod.GET, request, String.class);

        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        System.out.println("Product string----- :: "+products[0].toString());

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(products.length,  2);
    }

    @Test
    public void testGetAllProductsFilteredByCity() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product?city=Malmö", HttpMethod.GET, request, String.class);

        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println("list of " + productList);
        Assert.assertEquals(products.length,  productList.stream().filter(p -> p.getCity().contains("Malmö")).count());
    }

    @Test
    public void testGetAllProductsFilteredByColor() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product?color=guld", HttpMethod.GET, request, String.class);

        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Assert.assertEquals(products.length,  productList.stream().filter(p -> p.getProperties().contains("guld")).count());
    }

    @Test
    public void testGetAllProductsFilteredByMinPriceAndMaxPrice() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product?min_price=100.0&max_price=600.0", HttpMethod.GET, request, String.class);

        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        long price = productList.stream().filter(p -> p.getPrice()>=100.0 && p.getPrice()<=600.0).count();
        Assert.assertEquals(products.length,  price);
    }

    @Test
    public void testGetAllProductsFilteredByColorAndType() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrlWithPort() + "/product?color=guld&type=phone", HttpMethod.GET, request, String.class);

        String newValue = response.getBody().substring(8);
        newValue = newValue.substring(0, newValue.length() - 1);
        System.out.println("newValue : "+newValue);

        Product[] products = new Gson().fromJson(newValue, Product[].class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        long color = productList.stream().filter(p -> p.getProperties().contains("guld") ).count();
        long typesAndColor = productList.stream().filter(p -> p.getType().equals("phone")
                && p.getProperties().contains("guld")).count();

        Assert.assertEquals(products.length, color );
        Assert.assertEquals(products.length, typesAndColor );
    }

    public void addProducts(){
        productList = new ArrayList<>();
        productList.add(new Product(1L, "phone", "grön", null, 277.0, "Blake gränden", "Karlskrona"));
        productList.add(new Product(2L, "phone", "guld", null, 45.0, "Gustafsson gärdet", "Malmö"));
        productList.add(new Product(null, "phone", "brun", null, 952.0, "Olsson allén", "Malmö"));
        productList.add(new Product(null, "subscription", null, 10.0, 334.0, "Candido gränden", "Malmö"));
        productList.add(new Product(null, "subscription", null, 10.0, 650.0, "Persson gärdet", "Karlskrona"));
        productList.add(new Product(null, "subscription", null, 50.0, 74.0, "Nilsson gatan", "Stockholm"));
        dataLoadService.saveProducts(productList);
    }

}
