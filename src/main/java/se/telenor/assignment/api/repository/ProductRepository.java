package se.telenor.assignment.api.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import se.telenor.assignment.api.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {

}
