package com.example.owner.lacochina;

/**
 * Created by Asus gl552 on 23/10/2016.
 */

public class Restaurant {

    public String restaurantName,
                    restaurantAddress,
                    restaurantType,
                    restaurantTelephone,
                    restaurantReputation,
                    delivery;

    public Restaurant(String restaurantName, String restaurantAddress, String restaurantType, String restaurantTelephone, String restaurantReputation, String delivery) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantType = restaurantType;
        this.restaurantTelephone = restaurantTelephone;
        this.restaurantReputation = restaurantReputation;
        this.delivery = delivery;
    }

    public Restaurant(){


    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public String getRestaurantReputation() {
        return restaurantReputation;
    }

    public String getRestaurantTelephone() {
        return restaurantTelephone;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setRestaurantReputation(String restaurantReputation) {
        this.restaurantReputation = restaurantReputation;
    }

    public void setRestaurantTelephone(String restaurantTelephone) {
        this.restaurantTelephone = restaurantTelephone;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
