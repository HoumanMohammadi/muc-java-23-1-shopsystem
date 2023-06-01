package de.neuefische.shopsystem.controller;

import de.neuefische.shopsystem.model.Product;
import de.neuefische.shopsystem.repository.OrderRepository;
import de.neuefische.shopsystem.repository.ProductRepository;
import de.neuefische.shopsystem.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void allProducts_WhenNoProducts_thenExpectStatusOkAndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/products")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json("""

        []
"""));
    }

    @Test
    void getSingleProduct_WhenAProduct_ThenExpectStatusOKAndOneProduct() throws Exception {
        Product product = new Product("01", "McBook");
        productRepository.addProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/products/{id}", product.getId())).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                        {
                                        "id": "01",
                                        "name": "McBook"
                                        }
                                        """
                ));
    }

    @Test
    void allOrders_WhenNoOrders_thenExpectStatusOkAndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/orders")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().json("""

        []
"""));
    }

    @Test
    void allOrders_WhenAnOrder_ThenExpectStatusOKAndListWithOneOrder() throws Exception {
        Product product = new Product("01", "McBook");
        List<Product> productList = new ArrayList<Product>();
        productList.add(product);
        Order order = new Order("01",productList);
        orderRepository.addOrder(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/orders")).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                        [{
                                        "id": "01",
                                        "products": [{
                                        "id":"01", 
                                        "name":"McBook"}]
                                        }]
                                        """
                ));
    }

    @Test
    void getSingleOrder_WhenAnOrder_ThenStatusOkAndOneOrder() throws Exception {

        List<Product> productList = new ArrayList<Product>();

        Order order = new Order("01",productList);
        orderRepository.addOrder(order);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/orders/{id}","01")).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                              {
                                              "id": "01",
                                              "products": []
                                              }
                                        """
                ));
    }

    @Test
    void addOrder() {
    }
}