package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    long existingID;
    long nonEsxistingID;

    @BeforeEach
    void setUp() throws Exception {
        existingID = 1L;
        nonEsxistingID = 1000L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        var product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);
        Assertions.assertNotNull(product.getId());

    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {


        repository.deleteById(existingID);

        Optional<Product> result = repository.findById(existingID);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void deleteShouldThrowEmptyResultDataAcessExceptionWhenIdDoesNotExist() {


        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonEsxistingID);
        });
    }

}
