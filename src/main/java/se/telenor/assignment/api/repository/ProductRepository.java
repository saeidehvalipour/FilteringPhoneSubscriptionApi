package se.telenor.assignment.api.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import se.telenor.assignment.api.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {

}
