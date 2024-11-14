package store.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.domain.Product;
import store.product.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        ProductRepository productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetProductByName() {
        Optional<Product> productOpt = productService.getProductByName("콜라");
        assertTrue(productOpt.isPresent());
        Product product = productOpt.get();
        assertEquals("콜라", product.getName());
    }

    @Test
    public void testIsProductAvailable() {
        boolean available = productService.isProductAvailable("콜라", 5);
        assertTrue(available);
    }

    @Test
    public void testReduceProductQuantityInsufficientStock() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.reduceProductQuantity("콜라", 100);
        });
    }
}