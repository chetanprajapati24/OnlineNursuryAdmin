package com.chirag.admin.adminPlantPanel;

public class PlantDetails {
    public String Dishes,Quantity,Price,Description,ImageURL,RandomUID,Chefid;
    // Alt+insert

    public PlantDetails(String dishes, String quantity, String price, String description, String imageURL, String randomUID, String chefid) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID = randomUID;
        Chefid = chefid;
    }
}