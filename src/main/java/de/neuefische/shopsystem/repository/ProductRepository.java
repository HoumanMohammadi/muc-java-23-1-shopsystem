package de.neuefische.shopsystem.repository;

import de.neuefische.shopsystem.exception.OrderNotFoundException;
import de.neuefische.shopsystem.exception.ProductNotFoundException;
import de.neuefische.shopsystem.model.Order;
import de.neuefische.shopsystem.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


// Repository - Klasse die auf gespeicherte Daten zugreift
// Product Repository kümmert sich um ... Zugriff auf "Product" Daten

@Repository
public class ProductRepository {

    private final List<Product> products;

    public ProductRepository() {
        products = new ArrayList<>();
        // Zur besseren Testbarkeit mit dem Controller, habe ich hier initiale Produkte eingefügt
       // addInitialProducts();
    }

    public ProductRepository(List<Product> products) {
        this.products = products;
        // Zur besseren Testbarkeit mit dem Controller, habe ich hier initiale Produkte eingefügt
       // addInitialProducts();
    }

    public List<Product> list() {
        return products;
    }

    public Product getProductById(String idOfRequestedProduct) {
        // Indices:  0              1                      2
        //          AppleKeyboard   Vorwerk Staubsauger    Les Paul Gitarre

        // for-each Schleife
        // Wir "schauen" uns die products Liste STück für Stück an
        // singleProductFromList -> Das Objekt aus der Liste das wir uns anschauen
        for (Product singleProductFromList : products) {
            if (singleProductFromList.getId().equals(idOfRequestedProduct)) {
                return singleProductFromList;
            }
        }

        // Wenn for-Schleife durchgelaufen ist, aber nichts gefunden hat ...
        // Soll eine Exception geworfen werden
        throw new ProductNotFoundException(idOfRequestedProduct);
    }

    private void addInitialProducts() {
        this.products.addAll(List.of(new Product("Product-1", "Georgischer Rotwein"), new Product("Product-2", "Franz. Chardonnay"), new Product("Product-3", "Adiletten"), new Product("Product-4", "Bademantel"), new Product("Product-5", "Apple Keyboard")));
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
