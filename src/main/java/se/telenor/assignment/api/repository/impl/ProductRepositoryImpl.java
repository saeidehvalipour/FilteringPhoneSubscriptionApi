package se.telenor.assignment.api.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import se.telenor.assignment.api.model.Product;
import se.telenor.assignment.api.model.ProductModel;
import se.telenor.assignment.api.repository.ProductRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static se.telenor.assignment.api.utility.PhoneSubscriptionConstant.*;


@Service
public class ProductRepositoryImpl{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findByCriteria(ProductModel productModel){

        return productRepository.findAll((Specification<Product>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(productModel.getType()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(TYPE), productModel.getType())));
            }
            if(productModel.getColor()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(COLOR), productModel.getColor())));
            }
            if(productModel.getCity()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CITY), productModel.getCity())));
            }
            if(productModel.getStore_address()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(ADDRESS), productModel.getStore_address())));
            }
            if(productModel.getMin_price()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.ge(root.get(PRICE), productModel.getMin_price())));
            }
            if(productModel.getMax_price()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.le(root.get(PRICE), productModel.getMax_price())));
            }
            if(productModel.getGb_limit_min()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.ge(root.get(GB_LIMIT), productModel.getGb_limit_min())));
            }
            if(productModel.getGb_limit_max()!=null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.le(root.get(GB_LIMIT), productModel.getGb_limit_max())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

}

