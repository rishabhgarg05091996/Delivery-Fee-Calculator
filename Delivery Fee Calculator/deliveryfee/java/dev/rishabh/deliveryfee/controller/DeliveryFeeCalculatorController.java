/**
 * This is a Controller class to consume Request(JSON) for an Order, 
 * calculates Delivery Fees on the basis of rules and send back Response(JSON).
 * 
 * @author Rishabh Garg
 */

package dev.rishabh.deliveryfee.controller;

import dev.rishabh.deliveryfee.model.ShoppingCart;
import dev.rishabh.deliveryfee.rules.DeliveryFeeRules;
import dev.rishabh.deliveryfee.exception.ResourceNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/shoppingcart")
public class DeliveryFeeCalculatorController {
    
    // HTTP Response Key - delivery_fee
    private static final String DELIVERY_FEE = "delivery_fee";

    /**
     * Function to calculate delivery fee for an Order.
     * 
     * @param shoppingCart - Instance of ShoppingCart model class(JSON)
     * @return - Response Entity with body and Status(JSON).
     * @throws Exception
     */
    @GetMapping(path = "/getDeliveryFee", 
                produces = "application/json", 
                consumes = "application/json")
    public ResponseEntity<Object> getOrderDeliveryFee(@RequestBody ShoppingCart shoppingCart) throws Exception {
        
        // Getting values for Request (Input).
        double cartValue = shoppingCart.getCart_value();
        int deliveryDistance = shoppingCart.getDelivery_distance();
        int amountOfItems = shoppingCart.getAmount_of_items();
        String time = shoppingCart.getTime();

        // Check for Empty Required values.
        if(cartValue == 0.00|| deliveryDistance == 0 || 
            amountOfItems == 0 || time.isEmpty() || time == null) {
            throw new ResourceNotFoundException("Required Data missing, Please look into it!!");
        }

        // Check for negative values.
        if(cartValue < 0.00 || deliveryDistance < 0 || amountOfItems < 0) {
            throw new ResourceNotFoundException("Invalid Data, Please look into it!!");
        }

        // Function to calculate delivery fee on the basis of certain rules.
        double deliveryFee = DeliveryFeeRules.getDeliveryFees(cartValue, 
                                                                deliveryDistance, 
                                                                amountOfItems, 
                                                                time);
        // Setting delivery fee in ShoppingCart model class.
        shoppingCart.setDelivery_fee(deliveryFee);

        // Creating Response Body using HashMap.
        HashMap<String, Double> map = new HashMap<>();
        map.put(DELIVERY_FEE, shoppingCart.getDelivery_fee());

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}