package de.neuefische.shopsystem.service;

import de.neuefische.shopsystem.exception.ProductNotFoundException;
import de.neuefische.shopsystem.model.Order;
import de.neuefische.shopsystem.model.Product;
import org.junit.jupiter.api.Test;
import de.neuefische.shopsystem.repository.OrderRepository;
import de.neuefische.shopsystem.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    // mock = Dummy = Gaukelt vor es gäbe ein Objekt von der Klasse
    // Wir nutzen das, um NUR die Zielklasse zu testen (also den ShopService)
    // - nicht das drumherum
    ProductRepository productRepository = mock(ProductRepository.class);
    OrderRepository orderRepository = mock(OrderRepository.class);

    ShopService shopService = new ShopService(productRepository, orderRepository);

    @Test
    void getProductById_whenExistingId_thenReturnProduct() {
        //GIVEN
        String expectedProductId = "1";
        Product expectedProduct = new Product(expectedProductId, "Rotwein");

        // Wie soll sich das "gemockte" Repository verhalten?
        // Der Dummy soll das expectedProduct liefern, wenn ein Aufruf von getProductById
        // mit der ID expectedProductId kommt
        when(productRepository.getProductById(expectedProductId)).thenReturn(expectedProduct);

        //WHEN
        Product actualProduct = shopService.getProductById(expectedProductId);

        //THEN
        assertEquals(expectedProduct, actualProduct);
        // Sicherstellen, dass getProductById auch WIRKLICH aufgerufen wurde
        verify(productRepository).getProductById(any());
    }

    @Test
    void listProducts_whenAtLeastOneProductExists_thenReturnProductList() {
        //GIVEN
        String expectedProductId = "1";
        Product product = new Product(expectedProductId, "Rotwein");
        List<Product> expectedProductList= new ArrayList<Product>();
        expectedProductList.add(product);
        when(productRepository.list()).thenReturn(expectedProductList);

        //WHEN
        List<Product> actualProductList=shopService.listProducts();

        //THEN
        assertEquals(expectedProductList,actualProductList);
        verify(productRepository).list();
    }

    @Test
    void listOrders_whenNoOrdersExist_thenReturnEmptyList() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void getOrderById_whenValidOrderId_thenReturnOrder() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void addOrder_whenOrderWasPlacedSuccessfully_thenOrdersListLengthShouldBeIncremented() {
        //GIVEN

        //WHEN

        //THEN
    }

    // BONUS
    @Test
    void getProductById_whenNonExistingId_thenThrowProductNotFoundException() {
        //GIVEN

        //WHEN + THEN
    }

    // BONUS
    @Test
    void getOrderById_whenNonExistingId_thenThrowOrderNotFoundException() {
        //GIVEN

        //WHEN + THEN
    }

}