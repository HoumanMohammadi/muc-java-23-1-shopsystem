package de.neuefische.shopsystem.repository;

import de.neuefische.shopsystem.exception.OrderNotFoundException;
import de.neuefische.shopsystem.model.Order;
import de.neuefische.shopsystem.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private final List<Order> orders;

    public OrderRepository() {
        orders = new ArrayList<>();

        // Zur besseren Testbarkeit mit dem Controller, habe ich hier initiale Bestellungen eingefügt
     //   addInitialOrders();
    }

    public OrderRepository(List<Order> orders) {
        this.orders = orders;

        // Zur besseren Testbarkeit mit dem Controller, habe ich hier initiale Bestellungen eingefügt
       // addInitialOrders();
    }

    public List<Order> list() {
        return orders;
    }

    public Order getOrderById(String idOfRequestedOrder) {
        // for-each Schleife
        // Wir "schauen" uns die orders Liste Stück für Stück an
        // singleOrderFromList -> Das Objekt aus der Liste das wir uns anschauen
        for (Order singleOrderFromList : orders) {
            if (singleOrderFromList.getId().equals(idOfRequestedOrder)) {
                return singleOrderFromList;
            }
        }

        // Wenn for-Schleife durchgelaufen ist, aber nichts gefunden hat ...
        // Soll eine Exception geworfen werden
        throw new OrderNotFoundException(idOfRequestedOrder);
    }

    public void addOrder(Order orderToAdd) {
        orders.add(orderToAdd);
    }

    private void addInitialOrders() {
        this.addOrder(new Order("Order-1", List.of(new Product("Product-1", "Georgischer Rotwein"), new Product("Product-2", "Franz. Chardonnay"))));
        this.addOrder(new Order("Order-2", List.of(new Product("Product-3", "Adiletten"), new Product("Product-4", "Bademantel"))));
        this.addOrder(new Order("Order-3", List.of(new Product("Product-1", "Georgischer Rotwein"), new Product("Product-5", "Apple Keyboard"))));
    }
}
