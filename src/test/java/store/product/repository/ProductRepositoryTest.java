package store.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.domain.Product;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    public void testInitializeProducts() {
        Map<String, Product> allProducts = productRepository.findAll();
        assertFalse(allProducts.isEmpty());
        assertTrue(allProducts.containsKey("콜라"));
    }

    @Test
    public void testFindByName() {
        Product product = productRepository.findByName("콜라").orElse(null);
        assertNotNull(product);
        assertEquals(1000, product.getPrice());
    }
}