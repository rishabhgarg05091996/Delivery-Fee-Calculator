/**
 * This is a Rule Engine Class used to calculate Delivery fee 
 * on the basis of certain rules.
 * 
 * @author Rishabh Garg
 */
package dev.rishabh.deliveryfee.rules;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class DeliveryFeeRules {
    
    // These are some Constants. Using these values to check conditions.
    private static final int MAX_CARTVALUECHECK = 100;
    private static final double MAX_DELIVERYFEE = 15;

    // This constant used for Cart Value Rule
    private static final int CARTVALUECHECK = 10;
    // These constants used for Delivery Distance Rule
    private static final int DELIVERYDISTANCECHECK = 1000;
    private static final int FIVEHUNDREDMETERS = 500;
    private static final int ONEEUROS = 1;
    private static final int TWOEUROS = 2;
    // These constants used for Amount of Items Rule
    private static final double FIFTYCENTS = 0.5;
    private static final int AMOUNTOFITEMSCHECK = 5;
    // These constants used for Rush Week Rule
    private static final String RUSH_DAYOFWEEK = "FRIDAY";
    private static final String RUSH_TIMESTART = "15:00";
    private static final String RUSH_TIMEEND = "19:00";
    private static final double RUSH_FEE = 1.1;

    /**
     * Function to calculate delivery fee on the basis of certain rules.
     * 
     * @param cartValue - Cart Value amount : double
     * @param deliveryDistance - Delivery Distance (in meters) : integer
     * @param amountOfItems - Amount of Items : integer
     * @param time - Order Time in UTC : String
     * @return - Surcharge(Delivery Fee) : Double
     */
    public static double getDeliveryFees(double cartValue, 
                                        int deliveryDistance, 
                                        int amountOfItems, 
                                        String time) {
        
        double surcharge = 0.00;
        
        // If Cart value exceeds Maximum amount then there is no Delivery Charge.
        if(cartValue >= MAX_CARTVALUECHECK) {    
            return 0.0;
        }

        // If Cart Value is less than 10 Euros :-
        if(cartValue < CARTVALUECHECK) {
            surcharge += CARTVALUECHECK - cartValue;
        } // [END OF CART VALUE LOGIC]

        // If Delivery Distance is under 1 Km
        surcharge += TWOEUROS;

        // If Delivery Distance is more than 1 Km
        if(deliveryDistance > DELIVERYDISTANCECHECK) {
            int distanceFee = (deliveryDistance - DELIVERYDISTANCECHECK - 1) / FIVEHUNDREDMETERS;
            surcharge += (distanceFee + 1) * ONEEUROS;
        } // [END OF DELIVERY DISTANCE LOGIC]

        // If Delivery Fee reaches maximum Fees then :-
        if(surcharge > MAX_DELIVERYFEE) {
            return MAX_DELIVERYFEE;
        }

        // If Amount of Items exceeds 5 items
        if(amountOfItems >= AMOUNTOFITEMSCHECK) {
            int amountOfItemsFee = amountOfItems - AMOUNTOFITEMSCHECK;
            surcharge += (amountOfItemsFee + 1) * FIFTYCENTS;
        } // [END OF AMOUNT OF ITEMS LOGIC]

        // If Delivery Fee reaches maximum Fees then :-
        if(surcharge > MAX_DELIVERYFEE) {
            return MAX_DELIVERYFEE;
        }

        // Time is in UTC.
        Instant orderDateTime = Instant.parse(time);

        // Getting Local Day of Week according to system defaults.
        DayOfWeek dow = orderDateTime.atZone(ZoneId.systemDefault()).getDayOfWeek();
        // Getting Local Date Time according to system defaults.
        LocalDateTime orderdTime = LocalDateTime.ofInstant(orderDateTime, ZoneId.systemDefault());
        // Type Cast Date Time into LocalTime
        LocalTime orderTime = orderdTime.toLocalTime();

        // Check for Rush Day of Week - Friday
        if(String.valueOf(dow).equalsIgnoreCase(RUSH_DAYOFWEEK)) {
            if(orderTime.isAfter(LocalTime.parse(RUSH_TIMESTART)) && 
                orderTime.isBefore(LocalTime.parse(RUSH_TIMEEND))) {
                    surcharge *= RUSH_FEE;
            }
        }

        // If Delivery Fee reaches maximum Fees then :-
        if(surcharge > MAX_DELIVERYFEE) {
            return MAX_DELIVERYFEE;
        }

        return surcharge;
    }
}
