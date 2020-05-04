package se.telenor.assignment.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.telenor.assignment.api.model.Product;
import se.telenor.assignment.api.model.ProductModel;
import se.telenor.assignment.api.repository.ProductRepository;
import se.telenor.assignment.api.repository.impl.ProductRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProductService {

    @Autowired
    private ProductRepositoryImpl productRepositoryImpl;

    @Autowired
    private ProductRepository productRepository;

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public List<Product> getAllProducts(ProductModel productModel) {

        List<Product> productList;
        if (Objects.nonNull(productModel)) {
            productList = findByCriteria(productModel);
            productList.stream().forEach(product -> System.out.println(product.toString()));
        } else {
            productList = productRepositoryImpl.getAllProducts();
        }
        return productList;
    }

    public List<Product> findByCriteria(ProductModel productModel) {
        List<Product> products = productRepositoryImpl.findByCriteria(productModel);
        return modifyProductList(products);
    }

    public List<Product> modifyProductList(List<Product> products){
        return products.stream()
                .map(product -> {
                    String address = product.getAddress().concat(", ").concat(product.getCity());
                    product.setAddress(address);
                    product.setCity(null);
                    Double gbLimit = (Objects.isNull(product.getGbLimit()) || product.getGbLimit()==0) ? 0.0 : product.getGbLimit();
                    if (Objects.nonNull(gbLimit))
                        product.setGbLimit(gbLimit);
                    else
                        product.setGbLimit(null);
                    String color = (Objects.isNull(product.getColor()) || product.getColor().length() == 0)? null: product.getColor();
                    product.setColor(color);
                    return product;
                })
                .collect(Collectors.toList());
    }
}

