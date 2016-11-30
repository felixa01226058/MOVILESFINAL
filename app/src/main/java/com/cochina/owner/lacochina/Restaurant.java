package com.cochina.owner.lacochina;

/**
 * Created by Asus gl552 on 23/10/2016.
 */

public class Restaurant {

    public String restaurantName,
                    restaurantAddress,
                    restaurantType,
                    restaurantTelephone;


    public double latitude,longitude,restaurantReputation;

    public Restaurant(String restaurantName, String restaurantAddress, String restaurantType, String restaurantTelephone, Double restaurantReputation,
                      double longitude,double latitude) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantType = restaurantType;
        this.restaurantTelephone = restaurantTelephone;
        this.restaurantReputation = restaurantReputation;
        this.latitude= latitude;
        this.longitude = longitude;
    }

    public Restaurant(){

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public Double getRestaurantReputation() {
        return restaurantReputation;
    }

    public String getRestaurantTelephone() {
        return restaurantTelephone;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public Double getLatitude(){return latitude;}

    public Double getLongitude(){return longitude;}

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setRestaurantReputation(Double restaurantReputation) {
        this.restaurantReputation = restaurantReputation;
    }

    public void setRestaurantTelephone(String restaurantTelephone) {
        this.restaurantTelephone = restaurantTelephone;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public void setLatitude(Double latitude) {this.latitude=latitude;}

    public void setLongitude(Double longitude) {this.longitude=longitude;}


}
