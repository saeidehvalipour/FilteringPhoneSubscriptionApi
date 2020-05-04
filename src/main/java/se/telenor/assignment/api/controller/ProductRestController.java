package se.telenor.assignment.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.telenor.assignment.api.model.Product;
import se.telenor.assignment.api.model.ProductModel;
import se.telenor.assignment.api.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(@ModelAttribute("productRequestModel") Optional<ProductModel> productModelRequest) {

        List<Product> productList = new ArrayList<Product>();
        if (productModelRequest.isPresent()) {
            ProductModel productModel = productModelRequest.get();
            productList = productService.getAllProducts(productModel);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAllProducts() {
        productService.deleteAllProducts();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}

