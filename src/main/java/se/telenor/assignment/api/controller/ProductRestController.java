package se.telenor.assignment.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<List<Product>> getAllProducts(@ModelAttribute("productRequestModel") Optional<ProductModel> productRequestModel) {
        System.out.println("productRequestModel: " + productRequestModel.get());
        List<Product> productList = new ArrayList<Product>();
        if (productRequestModel.isPresent()) {
            ProductModel productModel = productRequestModel.get();
            productList = productService.getAllProducts(productModel);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}

