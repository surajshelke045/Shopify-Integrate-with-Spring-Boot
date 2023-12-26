package com.shopify.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLevel {
	
    private long inventory_item_id;
    private long location_id;
    private int available_adjustment;
    private int available;
    private String url;
    
    

}
