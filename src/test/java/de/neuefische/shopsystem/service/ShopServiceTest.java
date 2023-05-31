package de.neuefische.shopsystem.service;

import de.neuefische.shopsystem.exception.OrderNotFoundException;
import de.neuefische.shopsystem.exception.ProductNotFoundException;
import de.neuefische.shopsystem.model.Order;
import de.neuefische.shopsystem.model.Product;
import org.junit.jupiter.api.Test;
import de.neuefische.shopsystem.repository.OrderRepository;
import de.neuefische.shopsystem.repository.ProductRepository;
import org.mockito.internal.matchers.Or;

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
        verify(productRepository).list();
        assertEquals(expectedProductList,actualProductList);
    }

    @Test
    void listOrders_whenNoOrdersExist_thenReturnEmptyList() {
        //GIVEN
        List<Order> expectedOrderList= new ArrayList<Order>();
        when(orderRepository.list()).thenReturn(expectedOrderList);

        //WHEN
        List<Order> actualOrderList= shopService.listOrders();
        //THEN
        verify(orderRepository).list();
        assertEquals(expectedOrderList,actualOrderList);
    }

    @Test
    void getOrderById_whenValidOrderId_thenReturnOrder() {
        //GIVEN
        String orderId="01";
        Order expectedOrder= new Order(orderId, new ArrayList<Product>());
        when(orderRepository.getOrderById(orderId)).thenReturn(expectedOrder);

        //WHEN
        Order actualOrder= orderRepository.getOrderById(orderId);

        //THEN
        verify(orderRepository).getOrderById(any());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void addOrder_whenOrderWasPlacedSuccessfully_thenOrdersListLengthShouldBeIncremented() {
        //GIVEN
        String orderId="02";
        Product expectedProduct= new Product(orderId, "Rotwein");
        List<Product> expectedProducts= new ArrayList<>();
        expectedProducts.add(expectedProduct);
        Order expectedOrder= new Order(orderId, expectedProducts);
        //WHEN
        shopService.addOrder(expectedOrder);
        //THEN
        verify(orderRepository).addOrder(expectedOrder);
    }

    // BONUS
    @Test
    void getProductById_whenNonExistingId_thenThrowProductNotFoundException() {
        //GIVEN
        when(productRepository.getProductById("1")).thenThrow(new ProductNotFoundException(""));

        assertThrows(
                ProductNotFoundException.class,()->{
                    shopService.getProductById("1");
                }
        );
        verify(productRepository).getProductById(any());

        //WHEN + THEN
    }

    // BONUS
    @Test
    void getOrderById_whenNonExistingId_thenThrowOrderNotFoundException() {
        //GIVEN
        when(orderRepository.getOrderById("1")).thenThrow(new OrderNotFoundException(""));

        //WHEN + THEN
        assertThrows(
                OrderNotFoundException.class,() ->{
                    shopService.getOrderById("1");
                }
        );
        verify(orderRepository).getOrderById(any());
    }

}